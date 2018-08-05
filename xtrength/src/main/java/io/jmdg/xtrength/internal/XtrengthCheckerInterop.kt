package io.jmdg.xtrength.internal

import io.jmdg.xtrength.internal.constants.Pattern
import io.jmdg.xtrength.internal.constants.Rate

/**
 * Created by Joshua de Guzman on 05/08/2018.
 */

internal class XtrengthCheckerInterop {
    lateinit var string: String
    private var baseScore = 0
    private var characterScore = 0
    private var numberScore = 0
    private var symbolsScore = 0
    private var middleScore = 0
    private var requirements = 0

    fun validate(string: String) {
        this.string = string

        // Reset counts
        resetCounts()

        // Characters
        characterScore = string.length * Rate.CHARACTER
        baseScore += characterScore
        validateRequirement(characterScore)

        // Numbers
        numberScore = getFlatRate(Pattern.DIGITS, Rate.NUMBERS)
        baseScore += numberScore

        // Symbols
        symbolsScore = getFlatRate(Pattern.SYMBOLS, Rate.SYMBOLS)
        baseScore += symbolsScore

        // Middle Numbers or Symbols
        middleScore = getComputedMiddleScore()
        baseScore += middleScore

        // Requirements
        requirements *= Rate.REQUIREMENTS
        baseScore += requirements

    }

    private fun resetCounts() {
        baseScore = 0
        requirements = 0
    }

    private fun validateRequirement(n: Int) {
        if (n > 0) {
            requirements++
        }
    }

    private fun getFlatRate(pattern: String, rate: Int): Int {
        val n = getLength(pattern) * rate
        validateRequirement(n)
        return n
    }

    private fun getLength(pattern: String): Int {
        var count = 0
        string.forEach {
            if (it.toString().matches(pattern.toRegex())) count++
        }
        return count
    }

    private fun getComputedMiddleScore(): Int {
        var n = (getLength(Pattern.DIGITS) + getLength(Pattern.SYMBOLS))
        if (n > 2) {
            n = (n - 2) * Rate.MIDDLE_NUMBERS_SYMBOLS
        }
        validateRequirement(n)
        return n
    }

    fun getBaseScore(): Int {
        return baseScore
    }

    fun getCharacterScore(): Int {
        return characterScore
    }

    fun getNumberScore(): Int {
        return numberScore
    }

    fun getSymbolScore(): Int {
        return symbolsScore
    }

    fun getMiddleScore(): Int {
        return middleScore
    }

    fun getRequirementScore(): Int {
        return requirements
    }
}

