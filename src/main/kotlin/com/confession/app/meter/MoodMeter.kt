package com.confession.app.meter

import com.confession.app.model.Mood
import com.confession.app.model.MoodQuadrant
import kotlin.math.sqrt

private fun determineOffset(
    xSign: Int,
    ySign: Int,
    size: Int
): Pair<Int, Int> {
    return when {
        (xSign == -1 && ySign == 1) -> Pair(xSign * size, ySign * size)
        (xSign == 1 && ySign == 1) -> Pair(xSign, ySign * size)
        (xSign == -1 && ySign == -1) -> Pair(xSign * size, ySign)
        (xSign == 1 && ySign == -1) -> Pair(xSign, ySign)
        else -> throw Exception("Incorrect values for xSign: ${xSign} ySign: ${ySign} must be either 1 or -1")
    }
}

private fun createQuadrant(
    moods: Array<Mood>,
    quadrant: MoodQuadrant,
    energy: Int,
    pleasantness: Int
): List<MoodMeterElement> {
    val items = moods.filter { mood: Mood -> mood.quadrant == quadrant }

    //each quadrant is n x n
    val sublistSize = sqrt(items.size.toDouble()).toInt()

    var offsets = determineOffset(pleasantness, energy, sublistSize)

    var initialPleasantnessCount = offsets.first
    var initialEnergyCount = offsets.second

    var pleasantnessCount = initialPleasantnessCount
    var energyCount = initialEnergyCount

    return items.mapIndexed { index, mood ->
        if(index % (sublistSize) == 0 && index != 0) {
            println("CHECK ${index} ${sublistSize}")
            pleasantnessCount = initialPleasantnessCount
            //may vary
            energyCount -= 1

        } else {
            pleasantnessCount += 1
        }

        println("COUNT ${pleasantnessCount} ${energyCount}")
        val element = MoodMeterElement(
            mood = mood,
            energy = energyCount,
            pleasantness = pleasantnessCount
        )

        element
    }
}

private fun createMeter(): MutableList<MoodMeterElement> {
    val moods = Mood.values()
    val moodMeterElements: MutableList<MoodMeterElement> = mutableListOf()

    //positive energy, negative pleasantness
    val redQuadrant = createQuadrant(
        moods = moods,
        quadrant = MoodQuadrant.RED,
        energy = 1,
        pleasantness = -1,
    )
    moodMeterElements.addAll(redQuadrant)

    //positive energy, positive pleasantness
    val yellowQuadrant = createQuadrant(
        moods = moods,
        quadrant = MoodQuadrant.YELLOW,
        energy = 1,
        pleasantness = 1,
    )
    moodMeterElements.addAll(yellowQuadrant)

    //negative energy, negative pleasantness
    val blueQuadrant = createQuadrant(
        moods = moods,
        quadrant = MoodQuadrant.BLUE,
        energy = -1,
        pleasantness = -1,
    )
    moodMeterElements.addAll(blueQuadrant)

    //negative energy, positive pleasantness
    val greenQuadrant = createQuadrant(
        moods = moods,
        quadrant = MoodQuadrant.BLUE,
        energy = -1,
        pleasantness = 1,
    )
    moodMeterElements.addAll(greenQuadrant)

    return moodMeterElements
}

fun main() {
    createMeter()
}

class MoodMeter {
}