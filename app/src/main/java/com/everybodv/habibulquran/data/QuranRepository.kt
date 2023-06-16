package com.everybodv.habibulquran.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.everybodv.habibulquran.data.remote.response.*
import com.everybodv.habibulquran.data.remote.retrofit.AuthConfig
import com.everybodv.habibulquran.data.remote.retrofit.HijaiyahPredictConfig
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
                                        DataItem(it.arabic, it.pronounciation, it.audio, it.prediction, it.available)
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

    private val _tadarusPredictData = MutableLiveData<QuranPredictResponse?>()
    val tadarusPredictData: LiveData<QuranPredictResponse?> = _tadarusPredictData

    fun clearTadarusDataPredict(): LiveData<QuranPredictResponse?> {
        if (_tadarusPredictData.value != null) {
            val tadarus = QuranPredictResponse(error = false, rating = null)
            _tadarusPredictData.value = tadarus
        }
        return _tadarusPredictData
    }

    private val _hijaiyahPredictData = MutableLiveData<HijaiyahPredictResponse>()
    val hijaiyahPredictData: LiveData<HijaiyahPredictResponse> = _hijaiyahPredictData

    fun clearMakhrajDataPredict(): LiveData<HijaiyahPredictResponse> {
        if (_hijaiyahPredictData.value != null) {
            val makhraj = HijaiyahPredictResponse(predictedLabel = null)
            _hijaiyahPredictData.postValue(makhraj)
        }
        return _hijaiyahPredictData
    }
    fun getMakhrajPredict(audioFile: MultipartBody.Part): LiveData<HijaiyahPredictResponse> {
        _isLoading.value = true
        HijaiyahPredictConfig.getHijaiyahPredictService().predictHijaiyah(audioFile)
            .enqueue(object : Callback<HijaiyahPredictResponse> {
                override fun onResponse(
                    call: Call<HijaiyahPredictResponse>,
                    response: Response<HijaiyahPredictResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _hijaiyahPredictData.postValue(response.body())
                    } else {
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<HijaiyahPredictResponse>, t: Throwable) {
                    Log.e(Const.TAG_QURAN_REPO, "onFailure: ${t.message}")
                }

            })
        return _hijaiyahPredictData
    }
    fun getTadarusPredict(audioFile: MultipartBody.Part, originalText: RequestBody): LiveData<QuranPredictResponse?> {
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
                        Log.e(Const.TAG_QURAN_REPO, "onFailure: ${response.body()?.message}")
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

//    private val _regMsg = MutableLiveData<Event<String>>()
//    val regMsg: LiveData<Event<String>>
//        get() = _regMsg

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
                    _isEnabled.value = true
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _registerData.postValue(response.body())
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.body()?.message}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            })
        return _registerData
    }

    private val _forgotData = MutableLiveData<GeneralResponse>()
    val forgotData: LiveData<GeneralResponse> = _forgotData

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
                        _forgotData.value = response.body()
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

    private val _verifyResponse = MutableLiveData<GeneralResponse>()
    val verifyResponse: LiveData<GeneralResponse> = _verifyResponse

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
                        _verifyResponse.value = response.body()
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

    private val _editData = MutableLiveData<GeneralResponse>()
    val editData: LiveData<GeneralResponse> = _editData
    fun editProfile(
        id: String,
        name: String,
        email: String,
        gender: String,
        birthYear: Int,
        birthMonth: Int,
        birthDay: Int
    ) {
        _isLoading.value = true
        _isEnabled.value = false
        AuthConfig.getAuthService()
            .editProfile(id, id, name, email, gender, birthYear, birthMonth, birthDay)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isEnabled.value = true
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _editData.value = response.body()

                        _logMsg.value = Event("")
                    } else {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                }

            })
    }

    private val _userData = MediatorLiveData<UserData?>()
    val userData: LiveData<UserData?> = _userData

    fun clearDetailUserData(): LiveData<UserData?> {
        if (_userData.value != null) {
            _userData.value = null
        }
        return _userData
    }
    fun getDetailUser(userId: String): LiveData<UserData?> {
        _isLoading.value = true
        if (_userData.value != null) {
            _isLoading.value = false
        } else {
            AuthConfig.getAuthService().detailUser(userId)
                .enqueue(object : Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            _userData.value = response.body()?.user
                        } else {
                            Log.e(Const.TAG_AUTH_REPO, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        Log.e(Const.TAG_AUTH_REPO, "onFailure: ${t.message}")
                    }

                })
        }
        return _userData
    }

    fun updateUserData(name: String, email: String, birthDay: Int, gender: String, birthYear: Int, birthMonth: Int, image: String): LiveData<UserData?> {
        if (_userData.value != null) {
            val bm = if (birthMonth < 10) "0${birthMonth}" else birthMonth.toString()
            val bd = if (birthDay <10) "0${birthDay}" else birthDay.toString()
            val birthDate = "${birthYear}-${bm}-${bd}T00:00:00.000Z"
            _userData.value = UserData(name = name, email = email, birthdate = birthDate, jenisKelamin = gender, image = image)
        } else Log.e(Const.TAG_QURAN_REPO, "Failed to update")
        return _userData
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