package com.confession.app.export.pdf

import com.confession.app.ResString
import com.confession.app.meter.MoodMeterElement
import com.confession.app.meter.MoodMeterGraphics
import com.confession.app.model.ConfessionResponse
import com.confession.app.util.*
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDDocumentInformation
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.util.Matrix
import java.awt.geom.Point2D
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.properties.Delegates
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ConfessionPdf {
    lateinit var document: PDDocument
    lateinit var pdd: PDDocumentInformation
    lateinit var currentPage: PDPage
    lateinit var currentContentStream: PDPageContentStream

    var currentFontSize by Delegates.notNull<Float>()
    var currentFont: PDFont = PDType1Font.COURIER

    private var pageY = 750f
    private var pageX = 60f


    //Font Sizes
    private val TITLE_FONT_SIZE = 18f
    private val CONTENT_FONT_SIZE = 14f

    //Document
    private val DOCUMENT_HEIGHT = 750f
    private val HORIZONTAL_BREAK = 450F


    private fun isLandscape(page: PDPage): Boolean {
        val rotation = page.rotation
        val isLandscape: Boolean
        isLandscape = if (rotation == 90 || rotation == 270) {
            true
        } else if (rotation == 0 || rotation == 360 || rotation == 180) {
            false
        } else {
            println(
                "Can only handle pages that are rotated in 90 degree steps. This page is rotated ${rotation} degrees. Will treat the page as in portrait format",
            )
            false
        }
        return isLandscape
    }

    @Throws(IOException::class)
    private fun addCenteredText(
        text: String?,
        font: PDFont,
        fontSize: Int,
        content: PDPageContentStream,
        page: PDPage,
        offset: Point2D.Float
    ) {
        content.setFont(font, fontSize.toFloat())
        content.beginText()

        // Rotate the text according to the page orientation
        val pageIsLandscape: Boolean = isLandscape(page)
        val pageCenter: Point2D.Float = getCenter(page)

        // We use the text's width to place it at the center of the page
        val stringWidth: Float = getStringWidth(text, font, fontSize)
        if (pageIsLandscape) {
            val textX = pageCenter.x - stringWidth / 2f + offset.x
            val textY = pageCenter.y - offset.y
            // Swap X and Y due to the rotation
            content.setTextMatrix(Matrix.getRotateInstance(Math.PI / 2, textY, textX))
        } else {
            val textX = pageCenter.x - stringWidth / 2f + offset.x
            val textY = pageCenter.y + offset.y
            content.setTextMatrix(Matrix.getTranslateInstance(textX, textY))
        }
        content.showText(text)
        content.endText()
    }

    private fun getCenter(page: PDPage): Point2D.Float {
        val pageSize = page.mediaBox
        val rotated: Boolean = isLandscape(page)
        val pageWidth = if (rotated) pageSize.height else pageSize.width
        val pageHeight = if (rotated) pageSize.width else pageSize.height
        return Point2D.Float(pageWidth / 2f, pageHeight / 2f)
    }

    @Throws(IOException::class)
    private fun getStringWidth(text: String?, font: PDFont, fontSize: Int): Float {
        return font.getStringWidth(text) * fontSize / 1000f
    }

    @Throws(IOException::class)
    private fun getStringHeight(text: String?, font: PDFont, fontSize: Int): Float {
        return (font.fontDescriptor.capHeight) / 1000 * fontSize
    }

    private fun centerTextHorizontally(text: String): Float {
        val font = currentFont
        val fontSize = currentFontSize.toInt()
        val page = currentPage
        val stringWidth: Float = getStringWidth(text, font, fontSize)
        val pageCenter: Point2D.Float = getCenter(page)

        val textX = pageCenter.x - stringWidth / 2f

        return textX
    }

    private fun newPage(){
        pageY = DOCUMENT_HEIGHT
        if(this::currentContentStream.isInitialized) { currentContentStream.close() }
        val page = PDPage()
        page.mediaBox = PDRectangle.A4;
        currentPage = page
        document.addPage(currentPage)
        currentContentStream = PDPageContentStream(document, currentPage)
    }

    private fun verticallyPad(units: Float) {
        pageY -= units
    }

    private fun padText() {
        val size = currentFontSize.times(1/2f)
        verticallyPad(size)
    }

    private fun moodText(element: MoodMeterElement) {
        addText("- ${element.mood}")
        addText("- ${ResString.pleasantness}: ${element.pleasantness}")
        addText("- ${ResString.energy}: ${element.energy}")
    }

    private fun addText(text: String) {
        val height = getStringHeight(text, currentFont, currentFontSize.toInt())
        val width = getStringWidth(text, currentFont, currentFontSize.toInt())
        wrapPage(height)

        if(shouldWrapText(width)) {
            val singleCharWidth = width/text.length
            val textLength = text.length.toDouble()

            val limit = floor(HORIZONTAL_BREAK/singleCharWidth).toDouble()
            val linesCount = ceil(textLength.div(limit)).toInt()
            val amountToRemove = (textLength/linesCount).toInt()

            val final = wordWrapPDF(text, amountToRemove)

            pageX = centerTextHorizontally(final[0])

            final.forEach {
                currentContentStream.beginText()
                currentContentStream.newLineAtOffset(pageX ,pageY)
                currentContentStream.showText(it)
                pageY -= height
                currentContentStream.endText()
                padText()
            }

        } else {
            pageX = centerTextHorizontally(text)
            currentContentStream.beginText()
            currentContentStream.newLineAtOffset(pageX ,pageY)
            currentContentStream.showText(text)
            currentContentStream.newLine()
            currentContentStream.endText()
            pageY -= height
            padText()
        }
    }

    private fun wrapPage(size: Float) {
        if((pageY-size) <= 10F) {
            newPage()
        }
    }
    private fun shouldWrapText(size: Float): Boolean {
        return size >= HORIZONTAL_BREAK
    }

    private fun setFont(pdFont: PDFont, fontSize: Float) {
        currentFontSize = fontSize
        currentFont = pdFont
        currentContentStream.setFont(currentFont, currentFontSize)
    }

    private fun moodImage(element: MoodMeterElement?, document: PDDocument): PDImageXObject {
        val buffer = MoodMeterGraphics.getMoodElementAsBufferedImage(
            element,
            width = 150,
            height = 150
        )

        return LosslessFactory.createFromImage(document, buffer)
    }

    private fun drawMoodImage(
        image: PDImageXObject,
        yPosition: Float,
        contentStream: PDPageContentStream,
        page: PDPage
    ) {
        val w = image.width
        val x_pos = page.cropBox.width
        val x_adjusted: Float = (x_pos - w) / 2 + page.cropBox.lowerLeftX
        contentStream.drawImage(image, x_adjusted, yPosition)
    }

    fun create(confessionResponse: ConfessionResponse) {
        document = PDDocument()

        //info

        pdd = document.documentInformation

        pdd.author = ResString.appName
        pdd.creator = ResString.appName

        val now = LocalDateTime.now()
        val fileDisplayPattern = DateTimeFormatter.ofPattern(ResString.fileTimeFormat)
        val fileTitle = "${ResString.appName}_${fileDisplayPattern.format(now)}"
        pdd.title = fileTitle

        newPage()
        setFont(PDType1Font.COURIER, TITLE_FONT_SIZE)
        val displayPattern = DateTimeFormatter.ofPattern(ResString.timeFormat)
        val dateTitle = displayPattern.format(now)

        currentContentStream.setLeading(14.5);

        addText(ResString.appName)

        addText(dateTitle)

        setFont(PDType1Font.COURIER, CONTENT_FONT_SIZE)

        val presentElement = confessionResponse.presentMood
        if(presentElement != null) {
            val presentMoodTitle = ResString.presentMood
            addText(presentMoodTitle)
            moodText(presentElement)

        } else {
            addText(ResString.noPresentMood)
        }

        //Image size
        wrapPage(150F)
        verticallyPad(150F)

        val presentMoodImage = moodImage(presentElement, document)
        drawMoodImage(
            presentMoodImage,
            pageY,
            currentContentStream,
            currentPage,
        )

        wrapPage(20F)
        verticallyPad(20F)

        //reflect

        val understandingAnswer = confessionResponse.understandingAnswer

        if(understandingAnswer.isNotBlank()) {
            val understanding = understandingFormattedText(presentElement)
            setFont(PDType1Font.COURIER_BOLD, currentFontSize)
            addText(understanding)

            setFont(PDType1Font.COURIER, currentFontSize)
            addText(understandingAnswer)
        }

        val expressingAnswer = confessionResponse.expressingAnswer

        if(expressingAnswer.isNotBlank()) {
            setFont(PDType1Font.COURIER_BOLD, currentFontSize)
            addText(ResString.questionExpressing)

            setFont(PDType1Font.COURIER, currentFontSize)
            addText(expressingAnswer)
        }

        val regulatingAnswer = confessionResponse.regulatingAnswer

        if(expressingAnswer.isNotBlank()) {
            setFont(PDType1Font.COURIER_BOLD, currentFontSize)
            addText(ResString.questionRegulating)

            setFont(PDType1Font.COURIER, currentFontSize)
            addText(regulatingAnswer)
        }

        setFont(PDType1Font.COURIER, currentFontSize)
        val desiredElement = confessionResponse.desiredMood
        if(desiredElement != null) {
            val desiredMoodTitle = ResString.desiredMood
            addText(desiredMoodTitle)
            moodText(desiredElement)
        } else {
            addText(ResString.noDesiredMood)
        }



        wrapPage(150F)
        verticallyPad(150F)
        val desiredMoodImage = moodImage(desiredElement, document)
        drawMoodImage(
            desiredMoodImage,
            pageY,
            currentContentStream,
            currentPage,
        )

        wrapPage(20F)
        verticallyPad(20F)

        //remark

        val howCanYouFeelAnswer = confessionResponse.howCanYouFeelAnswer

        if(howCanYouFeelAnswer.isNotBlank()) {
            val howCanYouFeel = howCanYouFeelFormattedText(desiredElement)
            setFont(PDType1Font.COURIER_BOLD, currentFontSize)
            addText(howCanYouFeel)

            setFont(PDType1Font.COURIER, currentFontSize)
            addText(howCanYouFeelAnswer)
        }

        val doneWellAnswer = confessionResponse.doneWellAnswer

        if(doneWellAnswer.isNotBlank()) {
            setFont(PDType1Font.COURIER_BOLD, currentFontSize)
            addText(ResString.questionOneThingDoneWell)

            setFont(PDType1Font.COURIER, currentFontSize)
            addText(doneWellAnswer)
        }

        val toImproveAnswer = confessionResponse.toImproveAnswer

        if(toImproveAnswer.isNotBlank()) {
            setFont(PDType1Font.COURIER_BOLD, currentFontSize)
            addText(ResString.questionOneThingToImproveOn)

            setFont(PDType1Font.COURIER, currentFontSize)
            addText(toImproveAnswer)
        }

        val tasks = confessionResponse.tasks

        val timeInHours = totalTaskTimeInHours(tasks)
        val formatted = tasksAsString(tasksSize = tasks.size, timeInHours)

        setFont(PDType1Font.COURIER_BOLD, currentFontSize)
        addText(ResString.tasks)
        setFont(PDType1Font.COURIER, currentFontSize)
        if(tasks.isNotEmpty()) {
            addText(formatted)

            tasks.forEach {
                addText("${it.name} - ${it.duration.inWholeMinutes} min.")
            }
        }


        currentContentStream.close()
        document.save("/tmp/fooz.pdf")

        document.close()
    }

    companion object {

    }
}