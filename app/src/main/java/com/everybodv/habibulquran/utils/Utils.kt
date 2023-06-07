package com.everybodv.habibulquran.utils

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun showLoading(view: View, isLoading: Boolean) {
    view.visibility = if (isLoading) View.VISIBLE else View.GONE
}

fun FragmentActivity.addOnBackPressedCallbackWithInterval(
    millisToBack: Long,
    firstPressed: () -> Unit
) {
    onBackPressedDispatcher.addCallback(
        this, ActivityUtils.getOnBackPressedCallbackWithInterval(
            this,
            millisToBack,
            firstPressed
        )
    )
}

object ActivityUtils {
    fun getOnBackPressedCallbackWithInterval(
        fragmentActivity: FragmentActivity,
        millisToBack: Long,
        firstPressed: () -> Unit
    ) =
        object : OnBackPressedCallback(true) {
            var pressed = AtomicBoolean(false)
            val counter = object : CountDownTimer(millisToBack, millisToBack) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    if (pressed.get()) {
                        pressed.set(false)
                    }
                }
            }

            override fun handleOnBackPressed() {
                if (pressed.get()) disableAndPressOnBack()
                else {
                    pressed.set(true)
                    firstPressed()
                }
                counter.start()
            }

            private fun disableAndPressOnBack() {
                isEnabled = false
                fragmentActivity.onBackPressed()
                fragmentActivity.finishAffinity()
            }
        }
}