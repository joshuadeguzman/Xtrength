package io.jmdg.xtrength.entites

import java.util.*

/**
 * Created by Joshua de Guzman on 05/08/2018.
 */

data class Xtrength(
        // Xtrength Configuration
        internal var complexitySet: Array<String> = arrayOf("Very Weak", "Weak", "Not Bad", "Strong", "Very Strong"),
        internal var hint: String = "Enter desired password",
        internal var isCommonWordsDisabled: Boolean = false

        // View Configuration

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Xtrength

        if (!Arrays.equals(complexitySet, other.complexitySet)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(complexitySet)
    }
}