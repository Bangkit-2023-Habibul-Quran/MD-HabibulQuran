package com.everybodv.habibulquran.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everybodv.habibulquran.data.remote.response.*
import com.everybodv.habibulquran.data.remote.retrofit.AuthConfig
import com.everybodv.habibulquran.data.remote.retrofit.QuranConfig
import com.everybodv.habibulquran.data.remote.retrofit.QuranPredictConfig
import com.everybodv.habibulquran.utils.AppExecutors
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.Event
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuranRepository private constructor(
    private val appExecutors: AppExecutors
) {

//    private val _listSurah = MutableLiveData<List<Data>>()
//    val listSurah: LiveData<List<Data>> = _listSurah

    private val _listSurahTest = MutableLiveData<List<Data>>()
    val listSurahTest: LiveData<List<Data>> = _listSurahTest

    private val _listHijaiyah = MutableLiveData<List<DataItem>>()
    val listHijaiyah: LiveData<List<DataItem>> = _listHijaiyah

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listAyat = MutableLiveData<List<VersesItem>>()
    val listAyat: LiveData<List<VersesItem>> = _listAyat

    fun getAyatBySurahId(surahNumber: Int): LiveData<List<VersesItem>> {
        _isLoading.value = true

        QuranConfig.getQuranService().getAyatBySurahId(surahNumber)
            .enqueue(object : Callback<SurahByIdResponse> {
                override fun onResponse(call: Call<SurahByIdResponse>, response: Response<SurahByIdResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val verseList = ArrayList<VersesItem>()
                        val verseRespose = response.body()?.data
                        appExecutors.diskIO.execute {
                            verseRespose?.verses?.forEach {
                                val verse = VersesItem(it.number, it.translation, it.text, it.audio)
                                verseList.add(verse)
                            }
                            _listAyat.postValue(verseList)
                        }
                    } else {
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SurahByIdResponse>, t: Throwable) {
                    Log.e(Const.TAG_QURAN_REPO, "onFailure: ${t.message}")
                }

            })

        return _listAyat
    }

    private val _surahList = MutableLiveData<List<Data>>()
    val surahList: LiveData<List<Data>> = _surahList

    fun getListSurah(): LiveData<List<Data>> {
        _isLoading.value = true
        if (_surahList.value != null) {
            _isLoading.value = false
        } else {
            QuranConfig.getQuranService().getListSurah()
                .enqueue(object : Callback<SurahResponse> {
                    override fun onResponse(
                        call: Call<SurahResponse>,
                        response: Response<SurahResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            val listSurahResponse = ArrayList<Data>()
                            val listSurah = response.body()?.data
                            appExecutors.diskIO.execute {
                                listSurah?.forEach {
                                    val surahData = Data(
                                        it.number,
                                        it.numberOfVerses,
                                        it.name,
                                        it.preBismillah
                                    )
                                    listSurahResponse.add(surahData)
                                }
                                _surahList.postValue(listSurahResponse)
                            }
                        } else {
                            Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${t.message}")
                    }

                })
        }
        return _surahList
    }

    fun getTadarusTest(): LiveData<List<Data>> {
        _isLoading.value = true
        if (_listSurahTest.value != null) {
            _isLoading.value = false
        } else {
            QuranConfig.getQuranService().getTadarusTest()
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
                                        it.number,
                                        it.numberOfVerses,
                                        it.name,
                                        it.preBismillah,
                                        it.verses
                                    )
                                    surahResponse.add(surahData)
                                }
                                _listSurahTest.postValue(surahResponse)
                            }
                        } else {
                            Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.message()}")
                        }

                    }

                    override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${t.message}")
                    }
                })
        }
        return _listSurahTest
    }

    fun getAllHijaiyah(): LiveData<List<DataItem>> {
        _isLoading.value = true

        if (_listHijaiyah.value != null) {
            _isLoading.value = false
        } else {
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
                                    val hijaiyahData =
                                        DataItem(it.arabic, it.pronounciation, it.audio)
                                    hijaiyahResponse.add(hijaiyahData)
                                }
                                _listHijaiyah.postValue(hijaiyahResponse)
                            }
                        } else {
                            Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<HijaiyahResponse>, t: Throwable) {
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${t.message}")
                    }

                })
        }
        return _listHijaiyah
    }

    private val _tadarusPredictData = MutableLiveData<QuranPredictResponse>()
    val tadarusPredictData: LiveData<QuranPredictResponse> = _tadarusPredictData

    fun getTadarusPredict(audioFile: MultipartBody.Part, originalText: RequestBody): LiveData<QuranPredictResponse> {
        _isLoading.value = true
        QuranPredictConfig.getQuranPredictService().predictTadarus(audioFile, originalText)
            .enqueue(object : Callback<QuranPredictResponse> {
                override fun onResponse(
                    call: Call<QuranPredictResponse>,
                    response: Response<QuranPredictResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _tadarusPredictData.postValue(response.body())
                    } else {
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<QuranPredictResponse>, t: Throwable) {
                    Log.e(Const.TAG_QURAN_REPO, "onFailure: ${t.message}")
                }

            })
        return _tadarusPredictData
    }

    private val login = MutableLiveData<LoginResponse>()

    private val _registerData = MutableLiveData<GeneralResponse>()
    val registerData: LiveData<GeneralResponse> = _registerData

    private val _loginData = MutableLiveData<User>()
    val loginData: LiveData<User> = _loginData

    private val _isEnabled = MutableLiveData<Boolean>(true)
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _regMsg = MutableLiveData<Event<String>>()
    val regMsg: LiveData<Event<String>>
        get() = _regMsg

    private val _logMsg = MutableLiveData<Event<String>>()
    val logMsg: LiveData<Event<String>>
        get() = _logMsg

    fun login(email: String, password: String): LiveData<LoginResponse> {
        _isEnabled.value = false
        _isLoading.value = true

        AuthConfig.getAuthService().login(email, password)
            .enqueue(object : Callback<LoginResponse> {

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isEnabled.value = true
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        response.body().let { login ->
                            login?.user?.let {
                                _loginData.value = User(
                                    it.id,
                                    it.name,
                                    it.email,
                                    it.password,
                                    it.jenisKelamin,
                                    it.birthdate,
                                    it.image,
                                    it.isVerified,
                                    it.token
                                )
                            }
                        }
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.message()}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            }
            )
        return login
    }

    fun register(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        image: MultipartBody.Part,
        gender: RequestBody,
        birthYear: Int,
        birthMonth: Int,
        birthDay: Int
    ): LiveData<GeneralResponse> {
        _isEnabled.value = false
        _isLoading.value = true

        AuthConfig.getAuthService()
            .register(name, email, password, image, gender, birthYear, birthMonth, birthDay)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    if (response.isSuccessful) {
                        _registerData.postValue(response.body())
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.message()}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            })
        return _registerData
    }

    fun forgotResetPass(email: String) {
        _isEnabled.value = false
        _isLoading.value = true

        AuthConfig.getAuthService().forgotReset(email)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isEnabled.value = true
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _logMsg.value = Event("")
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.message()}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            })
    }

    fun verifyEmail(email: String) {
        _isEnabled.value = false
        _isLoading.value = true

        AuthConfig.getAuthService().verifyAccount(email)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isEnabled.value = true
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _logMsg.value = Event("")
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.message()}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            })
    }

    fun editProfile(
        id: String,
        name: String,
        email: String,
        gender: String,
        birthYear: Int,
        birthMonth: Int,
        birthDay: Int
    ) {
        AuthConfig.getAuthService()
            .editProfile(id, id, name, email, gender, birthYear, birthMonth, birthDay)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    if (response.isSuccessful) {
                        _logMsg.value = Event("")
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.message()}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            })
    }

    companion object {
        @Volatile
        private var instance: QuranRepository? = null
        fun getInstance(
            appExecutors: AppExecutors
        ): QuranRepository =
            instance ?: synchronized(this) {
                instance ?: QuranRepository(appExecutors)
            }.also { instance = it }
    }
}