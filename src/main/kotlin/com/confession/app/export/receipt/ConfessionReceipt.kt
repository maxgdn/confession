package com.confession.app.export.receipt

import com.confession.app.ResString
import com.confession.app.export.receipt.EscPosCommands.Companion.BEEP_BEEP
import com.confession.app.export.receipt.EscPosCommands.Companion.HEADER
import com.confession.app.export.receipt.EscPosCommands.Companion.JUSTIFY_CENTER
import com.confession.app.export.receipt.EscPosCommands.Companion.JUSTIFY_LEFT
import com.confession.app.export.receipt.EscPosCommands.Companion.LINE_FEED
import com.confession.app.export.receipt.EscPosCommands.Companion.RESET
import com.confession.app.meter.MoodMeterElement
import com.confession.app.meter.MoodMeterGraphics.Companion.getMoodElementAsBufferedImage
import com.confession.app.model.ConfessionResponse
import com.confession.app.model.Task
import com.confession.app.usb.ConfessionUsb
import com.confession.app.usb.UsbJavaStream
import com.confession.app.util.*
import com.github.anastaciocintra.escpos.EscPos
import com.github.anastaciocintra.escpos.EscPosConst
import com.github.anastaciocintra.escpos.Style
import com.github.anastaciocintra.escpos.image.*
import java.awt.image.BufferedImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class EscPosCommands {
    companion object {

        //0 - 127 characters in hex.

        // Null
        private const val NUL: Byte = 0x00
        // Start of Heading
        private const val SOH: Byte = 0x01
        // Start of Text
        private const val STX: Byte = 0x02
        // End of Text
        private const val ETX: Byte = 0x03
        // End of Transmit
        private const val EOT: Byte = 0x04
        // Enquiry
        private const val ENQ: Byte = 0x05
        // Acknowledge
        private const val ACK: Byte = 0x06
        // Bell
        private const val BEL: Byte = 0x07
        // Back Space
        private const val BS: Byte = 0x08
        // Horizontal Tab
        private const val TAB: Byte = 0x09
        // Line Feed
        private const val LF: Byte = 0x0A
        // Vertical Tab
        private const val VT: Byte = 0x0B
        // Form Feed
        private const val FF: Byte = 0x0C
        // Carriage Return
        private const val CR: Byte = 0x0D
        // Shift Out
        private const val SO: Byte = 0x0E
        // Shift In
        private const val SI: Byte = 0x0F
        // Data Line Escape
        private const val DLE: Byte = 0x10
        // Device Control 1
        private const val DC1: Byte = 0x11
        // Device Control 2
        private const val DC2: Byte = 0x12
        // Device Control 3
        private const val DC3: Byte = 0x13
        // Device Control 4
        private const val DC4: Byte = 0x14
        // Negative Acknowledge
        private const val NAK: Byte = 0x15
        // Synchronous Idle
        private const val SYN: Byte = 0x16
        // End of Transmit Block
        private const val ETB: Byte = 0x17
        // Cancel
        private const val CAN: Byte = 0x18
        // End of Medium
        private const val EM: Byte = 0x19
        // Substitute
        private const val SUB: Byte = 0x1A
        // Escape
        private const val ESC: Byte = 0x1B
        // File Separator
        private const val FS: Byte = 0x1C
        // Group Separator
        private const val GS: Byte = 0x1D
        // Record Separator
        private const val RS: Byte = 0x1E
        // Unit Separator
        private const val US: Byte = 0x1F
        // Space
        private const val SPACE: Byte = 0x20
        // Exclamation Point
        private const val EXCLAMATION_POINT: Byte = 0x21
        // Double Quote
        private const val DOUBLE_QUOTE: Byte = 0x22
        // Pound/Number Sign
        private const val POUND_NUMBER_SIGN: Byte = 0x23
        // Dollar Sign
        private const val DOLLAR_SIGN: Byte = 0x24
        // Percent Sign
        private const val PERCENT_SIGN: Byte = 0x25
        // Ampersand
        private const val AMPERSAND: Byte = 0x26
        // Single Quote
        private const val SINGLE_QUOTE: Byte = 0x27
        // Left Parenthesis
        private const val LEFT_PARENTHESIS: Byte = 0x28
        // Right Parenthesis
        private const val RIGHT_PARENTHESIS: Byte = 0x29
        // Asterisk
        private const val ASTERISK: Byte = 0x2A
        // Plus Sign
        private const val PLUS_SIGN: Byte = 0x2B
        // Comma
        private const val COMMA: Byte = 0x2C
        // Hyphen / Minus Sign
        private const val HYPHEN_MINUS_SIGN: Byte = 0x2D
        // Period
        private const val PERIOD: Byte = 0x2E
        // Forward Slash
        private const val FORWARD_SLASH: Byte = 0x2F
        // Zero Digit
        private const val ZERO_DIGIT: Byte = 0x30
        // One Digit
        private const val ONE_DIGIT: Byte = 0x31
        // Two Digit
        private const val TWO_DIGIT: Byte = 0x32
        // Three Digit
        private const val THREE_DIGIT: Byte = 0x33
        // Four Digit
        private const val FOUR_DIGIT: Byte = 0x34
        // Five Digit
        private const val FIVE_DIGIT: Byte = 0x35
        // Six Digit
        private const val SIX_DIGIT: Byte = 0x36
        // Seven Digit
        private const val SEVEN_DIGIT: Byte = 0x37
        // Eight Digit
        private const val EIGHT_DIGIT: Byte = 0x38
        // Nine Digit
        private const val NINE_DIGIT: Byte = 0x39
        // Colon
        private const val COLON: Byte = 0x3A
        // Semicolon
        private const val SEMICOLON: Byte = 0x3B
        // Less-Than Sign
        private const val LESS_THAN_SIGN: Byte = 0x3C
        // Equals Sign
        private const val EQUALS_SIGN: Byte = 0x3D
        // Greater-Than Sign
        private const val GREATER_THAN_SIGN: Byte = 0x3E
        // Question Mark
        private const val QUESTION_MARK: Byte = 0x3F
        // At Sign
        private const val AT_SIGN: Byte = 0x40
        // Capital A
        private const val CAPITAL_A: Byte = 0x41
        // Capital B
        private const val CAPITAL_B: Byte = 0x42
        // Capital C
        private const val CAPITAL_C: Byte = 0x43
        // Capital D
        private const val CAPITAL_D: Byte = 0x44
        // Capital E
        private const val CAPITAL_E: Byte = 0x45
        // Capital F
        private const val CAPITAL_F: Byte = 0x46
        // Capital G
        private const val CAPITAL_G: Byte = 0x47
        // Capital H
        private const val CAPITAL_H: Byte = 0x48
        // Capital I
        private const val CAPITAL_I: Byte = 0x49
        // Capital J
        private const val CAPITAL_J: Byte = 0x4A
        // Capital K
        private const val CAPITAL_K: Byte = 0x4B
        // Capital L
        private const val CAPITAL_L: Byte = 0x4C
        // Capital M
        private const val CAPITAL_M: Byte = 0x4D
        // Capital N
        private const val CAPITAL_N: Byte = 0x4E
        // Capital O
        private const val CAPITAL_O: Byte = 0x4F
        // Capital P
        private const val CAPITAL_P: Byte = 0x50
        // Capital Q
        private const val CAPITAL_Q: Byte = 0x51
        // Capital R
        private const val CAPITAL_R: Byte = 0x52
        // Capital S
        private const val CAPITAL_S: Byte = 0x53
        // Capital T
        private const val CAPITAL_T: Byte = 0x54
        // Capital U
        private const val CAPITAL_U: Byte = 0x55
        // Capital V
        private const val CAPITAL_V: Byte = 0x56
        // Capital W
        private const val CAPITAL_W: Byte = 0x57
        // Capital X
        private const val CAPITAL_X: Byte = 0x58
        // Capital Y
        private const val CAPITAL_Y: Byte = 0x59
        // Capital Z
        private const val CAPITAL_Z: Byte = 0x5A
        // Left Bracket
        private const val LEFT_BRACKET: Byte = 0x5B
        // Backward Slash
        private const val BACKWARD_SLASH: Byte = 0x5C
        // Right Bracket
        private const val RIGHT_BRACKET: Byte = 0x5D
        // Caret
        private const val CARET: Byte = 0x5E
        // Underscore
        private const val UNDERSCORE: Byte = 0x5F
        // Back Quote
        private const val BACK_QUOTE: Byte = 0x60
        // Lower-case A
        private const val LOWER_CASE_A: Byte = 0x61
        // Lower-case B
        private const val LOWER_CASE_B: Byte = 0x62
        // Lower-case C
        private const val LOWER_CASE_C: Byte = 0x63
        // Lower-case D
        private const val LOWER_CASE_D: Byte = 0x64
        // Lower-case E
        private const val LOWER_CASE_E: Byte = 0x65
        // Lower-case F
        private const val LOWER_CASE_F: Byte = 0x66
        // Lower-case G
        private const val LOWER_CASE_G: Byte = 0x67
        // Lower-case H
        private const val LOWER_CASE_H: Byte = 0x68
        // Lower-case I
        private const val LOWER_CASE_I: Byte = 0x69
        // Lower-case J
        private const val LOWER_CASE_J: Byte = 0x6A
        // Lower-case K
        private const val LOWER_CASE_K: Byte = 0x6B
        // Lower-case L
        private const val LOWER_CASE_L: Byte = 0x6C
        // Lower-case M
        private const val LOWER_CASE_M: Byte = 0x6D
        // Lower-case N
        private const val LOWER_CASE_N: Byte = 0x6E
        // Lower-case O
        private const val LOWER_CASE_O: Byte = 0x6F
        // Lower-case P
        private const val LOWER_CASE_P: Byte = 0x70
        // Lower-case Q
        private const val LOWER_CASE_Q: Byte = 0x71
        // Lower-case R
        private const val LOWER_CASE_R: Byte = 0x72
        // Lower-case S
        private const val LOWER_CASE_S: Byte = 0x73
        // Lower-case T
        private const val LOWER_CASE_T: Byte = 0x74
        // Lower-case U
        private const val LOWER_CASE_U: Byte = 0x75
        // Lower-case V
        private const val LOWER_CASE_V: Byte = 0x76
        // Lower-case W
        private const val LOWER_CASE_W: Byte = 0x77
        // Lower-case X
        private const val LOWER_CASE_X: Byte = 0x78
        // Lower-case Y
        private const val LOWER_CASE_Y: Byte = 0x79
        // Lower-case Z
        private const val LOWER_CASE_Z: Byte = 0x7A
        // Left Brace
        private const val LEFT_BRACE: Byte = 0x7B
        // Vertical Bar
        private const val VERTICAL_BAR: Byte = 0x7C
        // Right Brace
        private const val RIGHT_BRACE: Byte = 0x7D
        // Tilde
        private const val TILDE: Byte = 0x7E
        // Delta
        private const val DELTA: Byte = 0x7F

        // Commands

        val RESET = byteArrayOf(ESC,AT_SIGN)

        val HEADER = byteArrayOf(ESC, EXCLAMATION_POINT, LEFT_PARENTHESIS)

        //Justify
        val JUSTIFY_LEFT = byteArrayOf(ESC, LOWER_CASE_A, ZERO_DIGIT)
        val JUSTIFY_CENTER = byteArrayOf(ESC, 0x61, ONE_DIGIT)
        val JUSTIFY_RIGHT = byteArrayOf(ESC, 0x61, TWO_DIGIT)

        val LINE_FEED = byteArrayOf(LF)
        val CARRIAGE_RETURN = byteArrayOf(CR)

        val BEEP_BEEP = byteArrayOf(0x1b, 0x42, 0x02, 0x01)

    }
}

class ConfessionReceipt(private val confessionUsbResource: ConfessionUsb.ConfessionUsbResource) {

    private val MAX_LINE_WIDTH = 48

    //Utility
    private fun EscPos.printString(string: String, commands: ByteArray = byteArrayOf()): EscPos {
        val lines = wordWrapReceipt(string, MAX_LINE_WIDTH)
        for(line in lines) {
            this.write(commands + line.toByteArray(Charsets.US_ASCII))
            this.write(LINE_FEED)
        }

        this.write(RESET)

        return this
    }

    private fun EscPos.write(byteArray: ByteArray): EscPos {
        this.write(byteArray, 0, byteArray.size)
        return this
    }

    //Layout

    private fun EscPos.questionResponse(question: String, answer: String): EscPos {
        this.printString(question, JUSTIFY_CENTER + HEADER)
        if (answer.isNotBlank()) this.printString(answer, JUSTIFY_CENTER)
        else this.write(LINE_FEED)
        return this
    }

    @OptIn(kotlin.time.ExperimentalTime::class)
    private fun EscPos.tasks(tasks: List<Task>): EscPos {
        this.printString(ResString.tasks, JUSTIFY_LEFT + HEADER)
        val timeInHours = totalTaskTimeInHours(tasks)
        val formatted = tasksAsString(tasksSize = tasks.size, timeInHours)
        this.printString(formatted)
        this.write(LINE_FEED)
        tasks.forEach {
            this.printString("- ${it.name} ${it.duration.inWholeMinutes} min.")
        }

        return this
    }

    private fun EscPos.mood(title: String, element: MoodMeterElement?): EscPos {
        this.printString(title, JUSTIFY_CENTER + HEADER)
        element?.let {
            this.printString("- ${element.mood}", JUSTIFY_CENTER)
            this.printString("- ${ResString.pleasantness}: ${element.pleasantness}", JUSTIFY_CENTER)
            this.printString("- ${ResString.energy}: ${element.energy}", JUSTIFY_CENTER)
            this.write(LINE_FEED)
        }

        return this
    }

    private fun EscPos.previewMoodImage(element: MoodMeterElement?): EscPos {
        val algorithm: Bitonal = BitonalThreshold(127)

        val bufferedImage: BufferedImage? = getMoodElementAsBufferedImage(element)

        val escposImage = EscPosImage(CoffeeImageImpl(bufferedImage), algorithm)

        val imageWrapper = BitImageWrapper()
        imageWrapper.setJustification(EscPosConst.Justification.Center);
        imageWrapper.setMode(BitImageWrapper.BitImageMode._24DotDoubleDensity_Default);
        this.write(imageWrapper, escposImage);

        return this
    }

    private fun EscPos.confessionTitle(): EscPos {
        val title = Style()
            .setFontSize(Style.FontSize._3, Style.FontSize._3)
            .setJustification(EscPosConst.Justification.Center)
        this.write(title,ResString.appName)
        this.write(LINE_FEED)
        val now = LocalDateTime.now()
        val displayPattern = DateTimeFormatter.ofPattern(ResString.timeFormat)
        val date = Style()
            .setFontSize(Style.FontSize._2, Style.FontSize._2)
            .setJustification(EscPosConst.Justification.Center)
        this.write(date,displayPattern.format(now))

        return this
    }

    fun create(confessionResponse: ConfessionResponse) {
        val device = confessionUsbResource.device
        device?.let {
            val stream = UsbJavaStream(device)

            val escPos = EscPos(stream)
            escPos
                .confessionTitle()
                .feed(2)
                .mood(ResString.presentMood, confessionResponse.presentMood)
                .previewMoodImage(confessionResponse.presentMood)
                .feed(2)
                .questionResponse(understandingFormattedText(confessionResponse.presentMood), confessionResponse.understandingAnswer)
                .questionResponse(ResString.questionExpressing, confessionResponse.expressingAnswer)
                .questionResponse(ResString.questionRegulating, confessionResponse.regulatingAnswer)
                .feed(2)
                .mood(ResString.desiredMood, confessionResponse.desiredMood)
                .previewMoodImage(confessionResponse.desiredMood)
                .feed(2)
                .questionResponse(howCanYouFeelFormattedText(confessionResponse.desiredMood), confessionResponse.howCanYouFeelAnswer)
                .questionResponse(ResString.questionOneThingToImproveOn, confessionResponse.toImproveAnswer)
                .questionResponse(ResString.questionOneThingDoneWell, confessionResponse.doneWellAnswer)
                .tasks(confessionResponse.tasks)
                .write(BEEP_BEEP)
                .feed(5)
                .cut(EscPos.CutMode.FULL)
            escPos.close()

        }
    }
}