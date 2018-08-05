package io.jmdg.xtrength.entites

import java.util.*

/**
 * Created by Joshua de Guzman on 05/08/2018.
 */

data class Xtrength(
        // Xtrength Configuration
        internal var complexitySet: Array<String> = arrayOf("Very Weak", "Weak", "Not Bad", "Strong", "Very Strong"),
        internal var hint: String = "Enter desired password",
        internal var isCommonWordsDisabled: Boolean = false,

        // View Configuration
        internal var padding: Float = 20f,
        internal var paddingLeft: Float = 20f,
        internal var paddingTop: Float = 20f,
        internal var paddingRight: Float = 20f,
        internal var paddingBottom: Float = 20f
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