package io.jmdg.xtrength

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.RelativeLayout
import io.jmdg.xtrength.entites.Xtrength
import io.jmdg.xtrength.internal.XtrengthCheckerInterop

/**
 * Created by Joshua de Guzman on 05/08/2018.
 */

class XtrengthInputView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private var attrs: AttributeSet? = attrs
    private var defaultConfig = Xtrength()
    private lateinit var editText: EditText
    private var xtrengthCheckerInterop: XtrengthCheckerInterop = XtrengthCheckerInterop()

    init {
        initXMLAttributes()
    }


    private fun defaultConfig(init: Xtrength.() -> Unit) {
        defaultConfig.init()
    }

    private fun initXMLAttributes() {
        val typedArray: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.XtrengthInputView, 0, 0)
        loadAttributes(typedArray)
        typedArray.recycle()

        renderXMLBuilderConfigurations()
    }

    private fun loadAttributes(typedArray: TypedArray) {
        defaultConfig {
            // Load attributes from XML

            // Render configurations
            renderInputView()
        }
    }

    private fun renderXMLBuilderConfigurations() {

    }

    private fun renderInputView() {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        editText = EditText(context)
        editText.layoutParams = layoutParams
        editText.hint = "Enter desired password"
        addView(editText)

        // Add default input text watcher
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                xtrengthCheckerInterop.validate(p0.toString())
            }
        })
    }

    fun getInputView(): EditText {
        return editText
    }

    fun getBaseScore(): Int {
        return xtrengthCheckerInterop.getBaseScore()
    }

    fun getCharacterScore(): Int {
        return xtrengthCheckerInterop.getCharacterScore()
    }

    fun getNumberScore(): Int {
        return xtrengthCheckerInterop.getNumberScore()
    }

    fun getSymbolScore(): Int {
        return xtrengthCheckerInterop.getSymbolScore()
    }

    fun getMiddleScore(): Int {
        return xtrengthCheckerInterop.getMiddleScore()
    }

    fun getRequirementScore(): Int {
        return xtrengthCheckerInterop.getRequirementScore()
    }
}

