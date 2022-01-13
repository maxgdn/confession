package com.confession.app

object ResString {

    val appName: String
    val loading: String
    val previous: String
    val next: String
    val reset: String
    val energy: String
    val pleasantness: String
    val presentMood: String
    val desiredMood: String
    val tasks: String

    val questionRecognizing: String
    val questionUnderstanding: String
    val questionUnderstandingEmpty: String
    val questionExpressing: String
    val questionRegulating: String
    val questionBecoming: String
    val questionHowCanYouFeel: String
    val questionHowCanYouFeelEmpty: String
    val questionOneThingDoneWell: String
    val questionOneThingToImproveOn: String

    val problemGeneratingConfession: String
    val noPresentMood: String
    val noDesiredMood: String

    val exportPDF: String
    val exportPrint: String
    val exportReceipt: String

    val chooseAReceiptPrinter: String
    val printerNoticeLinux: String
    val printerNoticeWindows: String

    val OK: String
    val Cancel: String

    val timeFormat: String
    val fileTimeFormat: String

    init {
        appName = "Confession"
        loading = "Loading"
        previous = "Previous"
        next = "Next"
        reset = "Reset"
        energy = "Energy"
        pleasantness = "Pleasantness"
        presentMood = "Present Mood: "
        desiredMood = "Desired Mood: "
        tasks = "Tasks: "

        //Ruler
        questionRecognizing = "How are you feeling?"
        questionUnderstanding = "What happened to make you feel %s?"
        questionUnderstandingEmpty = "this way"
        questionExpressing = "How are you showing your feeling?"
        questionRegulating = "What are you doing to feel more, less, or the same of that same feeling?"
        questionBecoming = "How would you like to feel?"

        questionHowCanYouFeel = "How can you feel %s?"
        questionHowCanYouFeelEmpty = "better"

        questionOneThingDoneWell = "What is one thing you are doing well?"
        questionOneThingToImproveOn = "What is one thing you can improve on?"

        exportPDF = "PDF"
        exportPrint = "Print"
        exportReceipt = "Receipt"

        problemGeneratingConfession = "A problem occurred in generating the confession."
        noPresentMood = "No Present Mood"
        noDesiredMood = "No Desired Mood"

        chooseAReceiptPrinter = "Choose a Receipt Printer"
        printerNoticeLinux = "On Linux connection's can fail because user has no write permission on the USB device file."
        printerNoticeWindows = "On Windows connection's can fail because no libusb device driver is installed for the device"

        OK = "Okay"
        Cancel = "Cancel"

        timeFormat = "yyyy-MM-dd hh:mm:ss"
        fileTimeFormat = "yyyy_MM_dd_hh_mm"
    }
}