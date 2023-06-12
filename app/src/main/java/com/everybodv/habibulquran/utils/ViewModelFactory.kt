package com.everybodv.habibulquran.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.ui.auth.AuthViewModel
import com.everybodv.habibulquran.ui.makhraj.MakhrajViewModel
import com.everybodv.habibulquran.ui.quran.QuranViewModel
import com.everybodv.habibulquran.ui.tadarus.TadarusViewModel

class ViewModelFactory (private val quranRepository: QuranRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuranViewModel::class.java)) {
            return QuranViewModel(quranRepository) as T
        }
        if (modelClass.isAssignableFrom(MakhrajViewModel::class.java)) {
            return MakhrajViewModel(quranRepository) as T
        }
        if (modelClass.isAssignableFrom(TadarusViewModel::class.java)) {
            return TadarusViewModel(quranRepository) as T
        }
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(quranRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}