package io.jmdg.xtrength.internal

import io.jmdg.xtrength.internal.constants.Rate
import io.jmdg.xtrength.internal.constants.RegexPattern

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
    private var upperCaseScore = 0
    private var lowerCaseScore = 0
    private var requirements = 0

    fun validate(string: String) {
        this.string = string

        // Reset counts
        resetCounts()

        // Compute for score additions
        computeScoreAdditions()

        // Compute for score deductions
        computeScoreDeductions()
    }

    private fun computeScoreAdditions() {
        // Characters
        characterScore = string.length * Rate.CHARACTER
        baseScore += characterScore
        validateRequirement(characterScore)

        // Numbers
        numberScore = getFlatRate(RegexPattern.DIGITS, Rate.NUMBERS)
        baseScore += numberScore

        // Symbols
        symbolsScore = getFlatRate(RegexPattern.SYMBOLS, Rate.SYMBOLS)
        baseScore += symbolsScore

        // Middle Numbers or Symbols
        middleScore = getComputedMiddleScore()
        baseScore += middleScore

        // UpperCase
        upperCaseScore = getCaseSensitivityRate(RegexPattern.UPPERCASE, Rate.CASE_SENSITIVITY)
        baseScore += upperCaseScore

        // Lowercase
        lowerCaseScore = getCaseSensitivityRate(RegexPattern.LOWERCASE, Rate.CASE_SENSITIVITY)
        baseScore += lowerCaseScore

        // Requirements
        requirements *= Rate.REQUIREMENTS
        baseScore += requirements
    }

    private fun computeScoreDeductions() {
        // Deduct if all inputs are letters only
        if (characterScore / Rate.CHARACTER == string.length) {
            baseScore -= string.length
        }

        // Deduct if all inputs are numbers only
        if (numberScore / Rate.NUMBERS == string.length) {
            baseScore -= string.length
        }
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

    private fun getCaseSensitivityRate(pattern: String, rate: Int): Int {
        val length = getLength(pattern)
        var n = 0
        if (length > 0) {
            n = (string.length - length) * rate
        }
        validateRequirement(n)
        return n
    }

    private fun getLength(pattern: String): Int {
        var n = 0
        string.forEach {
            if (it.toString().matches(pattern.toRegex())) n++
        }
        return n
    }

    private fun getComputedMiddleScore(): Int {
        var n = (getLength(RegexPattern.DIGITS) + getLength(RegexPattern.SYMBOLS))
        if (n > 2) {
            n = (n - 2) * Rate.MIDDLE_NUMBERS_SYMBOLS
        }
        validateRequirement(n)
        return n
    }

    fun getBaseScore(): Int {
        return if (baseScore >= Rate.MAX) {
            Rate.MAX
        } else {
            baseScore
        }
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

    fun getUppercaseScore(): Int {
        return upperCaseScore
    }

    fun getLowercaseScore(): Int {
        return lowerCaseScore
    }
}

