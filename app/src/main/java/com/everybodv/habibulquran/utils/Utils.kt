package com.everybodv.habibulquran.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.CountDownTimer
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.everybodv.habibulquran.utils.Const.Companion.FILENAME_FORMAT
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())
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

fun hideContent(view: View) {
    view.visibility = View.GONE
}

fun showContent(view: View) {
    view.visibility = View.VISIBLE
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

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)

    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

    return file
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