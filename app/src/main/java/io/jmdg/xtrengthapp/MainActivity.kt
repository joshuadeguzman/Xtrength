package io.jmdg.xtrengthapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
    }

    private fun initComponents() {
        // Adding listeners to the input changes
        xiv_password
                .getInputView()
                .addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        // Log what you need
                        Timber.d("Base Score: %d", xiv_password.getBaseScore())
                        Timber.d("Character Score: %d", xiv_password.getCharacterScore())
                        Timber.d("Number Score: %d", xiv_password.getNumberScore())
                        Timber.d("Symbol Score: %d", xiv_password.getSymbolScore())
                        Timber.d("Middle Number & Symbols Score: %d", xiv_password.getMiddleScore())
                        Timber.d("Uppercase Score: %d", xiv_password.getUppercaseScore())
                        Timber.d("Lowercase Score: %d", xiv_password.getLowercaseScore())
                        Timber.d("Requirements Score: %d", xiv_password.getRequirementScore())

                        // Add custom methods here when validating text (eg. render ui changes, callback methods)
                        if (xiv_password.getBaseScore() < 50) {
                            renderSomeCustomPrompt()
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

    private fun renderSomeCustomPrompt() {
        // Do something here
    }

}
