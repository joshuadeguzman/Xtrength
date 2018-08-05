# Xtrength
> A password strength meter for Android

[![Kotlin](https://img.shields.io/badge/Kotlin-1.2.60-green.svg?style=flat-square)](http://kotlinlang.org)
[![Support](https://img.shields.io/badge/Support-27.1.1-6ab344.svg?style=flat-square)](https://github.com/ReactiveX/RxJava/releases/tag/v2.1.10)
[![Build Status](https://img.shields.io/travis/joshuadeguzman/Xtrength.svg?style=flat-square)](https://travis-ci.org/joshuadeguzman/ButtonsKt)
[![GitHub (pre-)release](https://img.shields.io/github/release/joshuadeguzman/Xtrength/all.svg?style=flat-square)
](./../../releases)

Easily integrate real time password strength meter to your application using Xtrength. 

### Installation
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    // Replace version with release version, e.g. 1.0.0-alpha, -SNAPSHOT
    implementation 'io.jmdg:xtrength:[VERSION]'
}
```

![Demo 1](https://i.imgur.com/qYwX6GF.gif)
![Demo 2](https://i.imgur.com/bnARD7g.gif)

### Usage
```xml
<io.jmdg.xtrength.XtrengthInputView
    android:id="@+id/xiv_password"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```
Do something when listening to the validation of scores
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    xiv_password
            .getInputView()
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    // Log what you need (eg. via Timber)
                    Timber.d("Base Score: %d", xiv_password.getBaseScore())

                    // Add custom methods here when validating text (eg. render ui changes, callback methods)
                    someMethod()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //
                }
            })
    
}
```

#### META

Joshua de Guzman | code@jmdg.io

Distributed under the MIT license. 

#### CONTRIBUTING

1. Fork it (<https://github.com/joshuadeguzman/ButtonsKt/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request
