package io.jmdg.xtrength

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import io.jmdg.xtrength.entites.Xtrength
import io.jmdg.xtrength.internal.XtrengthCheckerInterop
import io.jmdg.xtrength.internal.XtrengthSingleton
import io.jmdg.xtrength.internal.helpers.ResolutionUtil


/**
 * Created by Joshua de Guzman on 05/08/2018.
 */

class XtrengthInputView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private var attrs: AttributeSet? = attrs
    private var defaultConfig = Xtrength()
    private lateinit var complexityTextView: TextView
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
        editText.maxLines = 1
        editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        editText.transformationMethod = PasswordTransformationMethod.getInstance()
        editText.hint = "Enter desired password"

        // Initialize font
        if (XtrengthSingleton.typeface == null) {
            XtrengthSingleton.typeface = Typeface.createFromAsset(context.assets, "opensans.ttf")
        }

        // Render font styles
        editText.setTypeface(XtrengthSingleton.typeface, Typeface.NORMAL)

        // Render defaults
        editText.background.setColorFilter(ContextCompat.getColor(context, R.color.midnightBlue), PorterDuff.Mode.SRC_ATOP)

        if (defaultConfig.padding > 0) {
            editText.setPadding(ResolutionUtil.dpToPx(context, defaultConfig.padding),
                    ResolutionUtil.dpToPx(context, defaultConfig.padding),
                    ResolutionUtil.dpToPx(context, defaultConfig.padding),
                    ResolutionUtil.dpToPx(context, defaultConfig.padding))
        } else {
            editText.setPadding(ResolutionUtil.dpToPx(context, defaultConfig.paddingLeft),
                    ResolutionUtil.dpToPx(context, defaultConfig.paddingTop),
                    ResolutionUtil.dpToPx(context, defaultConfig.paddingRight),
                    ResolutionUtil.dpToPx(context, defaultConfig.paddingBottom))
        }

        // Add view to the root view
        addView(editText)

        // Add complexity view to root view
        renderComplexityTextView()

        // Add default input text watcher
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // Additional filter
                p0!!.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                    source.toString().filterNot { it.isWhitespace() }
                })

                // Validate complexity
                if (!p0.toString().isBlank()) {
                    xtrengthCheckerInterop.validate(p0.toString().replace("\\s".toRegex(), ""))
                    renderComplexityChanges()
                } else {
                    complexityTextView.text = ""
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }
        })
    }

    private fun renderComplexityTextView() {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(ResolutionUtil.dpToPx(context, defaultConfig.paddingRight), 0, 30, 0)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)

        complexityTextView = TextView(context)
        complexityTextView.layoutParams = layoutParams
        complexityTextView.setTypeface(XtrengthSingleton.typeface, Typeface.NORMAL)
        addView(complexityTextView)
    }

    private fun renderComplexityChanges() {
        when {
            getBaseScore() in 86..100 -> renderComplexityViewChanges(4)
            getBaseScore() in 66..85 -> renderComplexityViewChanges(3)
            getBaseScore() in 41..65 -> renderComplexityViewChanges(2)
            getBaseScore() in 21..40 -> renderComplexityViewChanges(1)
            getBaseScore() in 0..20 -> renderComplexityViewChanges(0)
        }
    }

    private fun renderComplexityViewChanges(resId: Int) {
        // Render text
        complexityTextView.text = defaultConfig.complexitySet[resId]

        // Apply colors
        val color = context.resources.getIntArray(R.array.complexityColors)[resId]
        complexityTextView.setTextColor(color)
        editText.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
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

    fun getUppercaseScore(): Int {
        return xtrengthCheckerInterop.getUppercaseScore()
    }

    fun getLowercaseScore(): Int {
        return xtrengthCheckerInterop.getLowercaseScore()
    }

    fun getRequirementScore(): Int {
        return xtrengthCheckerInterop.getRequirementScore()
    }
}

