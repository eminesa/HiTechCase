package com.eminesa.hitechcase.extensions

import android.view.View

fun View.isAbility(isEnable: Boolean) {

    if (isEnable) {
        this.alpha = 1f
        this.isEnabled = true
    } else {
        this.alpha = 0.5f
        this.isEnabled = false
    }

}

