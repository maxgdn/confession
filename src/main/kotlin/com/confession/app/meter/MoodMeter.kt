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

    //snake our way down the quadrant while incrementing the correct values
    return items.mapIndexed { index, mood ->
        if(index % (sublistSize) == 0 && index != 0) {
            pleasantnessCount = initialPleasantnessCount
            energyCount -= 1
        } else {
            pleasantnessCount += 1
        }

        val element = MoodMeterElement(
            mood = mood,
            energy = energyCount,
            pleasantness = pleasantnessCount
        )

        element
    }
}

private fun createMeter(): List<MoodMeterElement> {
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

    return moodMeterElements.toList()
}

class MoodMeter {
    val meter: List<MoodMeterElement> = createMeter()

    private fun findItem(x: Int, y: Int): MoodMeterElement? {
        val filtered = meter.filter {
            (x == it.pleasantness && y == it.energy)
        }

        return if(filtered.size == 1) {
            filtered.first()
        } else {
            return null
        }
    }

    /*
    *
    * Containing structure is poor for this operation.
    * However it works ¯\_(ツ)_/¯ and the size is small.
    * Consider, hashmap, or indexed 2D array to replace
    *
    * */
    fun getMoodsNearest(element: MoodMeterElement): List<MoodMeterElement?> {
        val nearest = mutableListOf<MoodMeterElement?>()

        //nearest elements by 1

        val currentX = element.pleasantness
        val currentY = element.energy

        val rightIdx = currentX + 1
        val leftIdx = currentX - 1

        val aboveIdx = currentY + 1
        val belowIdx = currentY - 1

        //top left
        nearest.add(findItem(leftIdx, aboveIdx))
        //top
        nearest.add(findItem(currentX, aboveIdx))
        //top right
        nearest.add(findItem(rightIdx, aboveIdx))

        //left
        nearest.add(findItem(leftIdx, currentY))
        //current
        nearest.add(findItem(currentX, currentY))
        //right
        nearest.add(findItem(rightIdx, currentY))

        //bottom left
        nearest.add(findItem(leftIdx, belowIdx))
        //bottom
        nearest.add(findItem(currentX, belowIdx))
        //bottom right
        nearest.add(findItem(rightIdx, belowIdx))

        return nearest
    }
}