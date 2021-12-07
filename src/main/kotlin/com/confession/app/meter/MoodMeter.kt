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
        }

        val element = MoodMeterElement(
            mood = mood,
            energy = energyCount,
            pleasantness = pleasantnessCount
        )

        pleasantnessCount += 1

        element
    }
}

private fun createMeter(): ArrayList<MoodMeterElement> {
    val moods = Mood.values()
    val moodMeterElements: ArrayList<MoodMeterElement> = arrayListOf()

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
        quadrant = MoodQuadrant.GREEN,
        energy = -1,
        pleasantness = 1,
    )
    moodMeterElements.addAll(greenQuadrant)

    return moodMeterElements as ArrayList<MoodMeterElement>
}

class MoodMeter {
    val meter: ArrayList<MoodMeterElement> = createMeter()

    private fun findItem(x: Int, y: Int): MoodMeterElement? {
        return meter.find {
            (x == it.pleasantness && y == it.energy)
        }
    }

    /*
    *
    *  To deal with the fact that zero doesn't exist
    *
    * */
    enum class ChasmOp {
        ADD,
        SUBTRACT
    }

    private fun jumpTheChasm(a: Int, b: Int, operation: ChasmOp): Int {
        return when(operation) {
            ChasmOp.ADD -> {
                val result = a + b

                val final = if(result == 0) result + 1 else result
                final
            }
            ChasmOp.SUBTRACT -> {
                val result = a - b

                val final = if(result == 0) result - 1 else result
                final
            }
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
        val nearest = arrayListOf<MoodMeterElement?>()

        //nearest elements by 1

        val currentX = element.pleasantness
        val currentY = element.energy

        val rightIdx = jumpTheChasm(currentX, 1, ChasmOp.ADD)
        val leftIdx = jumpTheChasm(currentX, 1, ChasmOp.SUBTRACT)

        val aboveIdx = jumpTheChasm(currentY, 1, ChasmOp.ADD)
        val belowIdx = jumpTheChasm(currentY, 1, ChasmOp.SUBTRACT)

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
