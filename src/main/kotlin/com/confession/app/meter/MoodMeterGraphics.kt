package com.confession.app.meter

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skia.Image
import java.awt.Color
import java.awt.FontMetrics
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt

class MoodMeterGraphics {

    companion object {
        fun getMoodElementAsImageByteArray(
            moodMeterElement: MoodMeterElement?,
            width: Int = 250,
            height: Int = 250
        ): ByteArray {
            val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)

            val g2d = bufferedImage.createGraphics()

            val metrics: FontMetrics = g2d.fontMetrics

            // fill all the image with white
            g2d.color = Color.white
            g2d.fillRect(0, 0, width, height)

            // create a string with yellow
            g2d.color = Color.black

            val size = 12
            val sizeFloat = size.toFloat()

            for (i in 1..size) {
                val factor = i.div(sizeFloat)
                val lineHeight = ((height * factor)).toInt()
                g2d.drawLine(0, lineHeight, width, lineHeight)
            }

            for (i in 1..size) {
                val factor = i.div(sizeFloat)
                val lineHeight = ((width * factor)).toInt()
                g2d.drawLine(lineHeight, 0, lineHeight, height)
            }

            val centerX = width/2
            val centerY = height/2

            var x = centerX
            var y = centerY

            val computeCircleCoordinates = {
                if(moodMeterElement != null) {
                    val pleasantness = moodMeterElement.pleasantness
                    //must invert the Y axis values as the canvas itself increases in value while moving downward
                    val energy = moodMeterElement.energy.times(-1)

                    //calculate position from the center
                    x = centerX + (width.times(pleasantness.div(sizeFloat))).toInt()
                    y = centerY + (height.times(energy.div(sizeFloat))).toInt()
                }
            }

            computeCircleCoordinates()

            val diagonal = sqrt(width.toDouble().pow(2) + height.toDouble().pow(2))
            val radius = (diagonal * 0.05f).toInt()

            fun drawCenteredCircle(x: Int,y: Int,r: Int) {
                val circleX = x-(r/2);
                val circleY = y-(r/2);
                g2d.fillOval(circleX,circleY,r,r);
            }

            drawCenteredCircle(x, y, radius)
            drawCenteredCircle(width/2, height/2, radius)

            g2d.dispose()

            val stream = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, "PNG", stream)

            val bytes = stream.toByteArray()

            return bytes
        }

        fun getMoodElementAsBufferedImage(
            moodMeterElement: MoodMeterElement?,
            width: Int = 250,
            height: Int = 250
        ): BufferedImage? {
            val bytes = getMoodElementAsImageByteArray(moodMeterElement, width, height)
            val stream: InputStream = ByteArrayInputStream(bytes)
            return ImageIO.read(stream)
        }

        fun getMoodElementAsImageBitmap(
            moodMeterElement: MoodMeterElement?,
            width: Int = 250,
            height: Int = 250
        ): ImageBitmap {
            val bytes = getMoodElementAsImageByteArray(moodMeterElement, width, height)
            return Image.makeFromEncoded(bytes).asImageBitmap()
        }
    }
}