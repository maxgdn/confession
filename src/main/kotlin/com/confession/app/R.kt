package com.confession.app

object ResString {

    val appName: String
    val loading: String
    val previous: String
    val next: String
    val reset: String
    val energy: String
    val pleasantness: String
    val questionRecognizing: String
    val questionUnderstanding: String
    val questionUnderstandingEmpty: String
    val questionExpressing: String
    val questionRegulating: String
    val questionBecoming: String

    init {
        appName = "Confession"
        loading = "Loading"
        previous = "Previous"
        next = "Next"
        reset = "Reset"
        energy = "Energy"
        pleasantness = "Pleasantness"

        //Ruler
        questionRecognizing = "How are you feeling?"
        questionUnderstanding = "What happened to make you feel %s?"
        questionUnderstandingEmpty = "this way"
        questionExpressing = "How are you showing your feeling?"
        questionRegulating = "What are you doing to feel more, less, or the same of that same feeling?"
        questionBecoming = "How would you like to feel?"
    }
}