package com.everybodv.habibulquran.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.data.remote.response.HijaiyahResponse
import com.everybodv.habibulquran.data.remote.response.SurahResponse
import com.everybodv.habibulquran.data.remote.retrofit.QuranConfig
import com.everybodv.habibulquran.utils.AppExecutors
import com.everybodv.habibulquran.utils.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuranRepository private constructor(
    private val appExecutors: AppExecutors
){

    private val _listSurah = MutableLiveData<List<Data>>()
    val listSurah: LiveData<List<Data>> = _listSurah

    private val _listHijaiyah = MutableLiveData<List<DataItem>>()
    val listHijaiyah: LiveData<List<DataItem>> = _listHijaiyah

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllSurah(): LiveData<List<Data>> {
        _isLoading.value = true

        QuranConfig.getQuranService().getAllSurah()
            .enqueue(object : Callback<SurahResponse> {
                override fun onResponse(
                    call: Call<SurahResponse>,
                    response: Response<SurahResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val surahResponse = ArrayList<Data>()
                        val surah = response.body()?.data
                        appExecutors.diskIO.execute {
                            surah?.forEach {
                                val surahData = Data(
                                    it.number, it.numberOfVerses, it.name, it.preBismillah, it.verses
                                )
                                surahResponse.add(surahData)
                            }
                            _listSurah.postValue(surahResponse)
                        }
                    } else {
                        Log.e(Const.TAG_QURAN_REPO, "onFailuts: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                    Log.e(Const.TAG_QURAN_REPO, "onFailuts: ${t.message}")
                }

            })
        return _listSurah
    }

    fun getAllHijaiyah(): LiveData<List<DataItem>> {
        _isLoading.value = true

        QuranConfig.getQuranService().getAllHijaiyah()
            .enqueue(object : Callback<HijaiyahResponse> {
                override fun onResponse(
                    call: Call<HijaiyahResponse>,
                    response: Response<HijaiyahResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val hijaiyahResponse = ArrayList<DataItem>()
                        val hijaiyah = response.body()?.data
                        appExecutors.diskIO.execute {
                            hijaiyah?.forEach {
                                val hijaiyahData = DataItem(it.arabic, it.pronounciation, it.audio)
                                hijaiyahResponse.add(hijaiyahData)
                            }
                            _listHijaiyah.postValue(hijaiyahResponse)
                        }
                    } else {
                        Log.e(Const.TAG_QURAN_REPO, "onFailuts: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<HijaiyahResponse>, t: Throwable) {
                    Log.e(Const.TAG_QURAN_REPO, "onFailuts: ${t.message}")
                }

            })
        return _listHijaiyah
    }
}