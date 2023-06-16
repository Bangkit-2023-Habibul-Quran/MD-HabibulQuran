package com.everybodv.habibulquran.utils

import com.everybodv.habibulquran.data.QuranRepository

object Injection {

    fun provideRepository(): QuranRepository {
        val appExecutors = AppExecutors()
        return QuranRepository.getInstance(appExecutors)
    }
}