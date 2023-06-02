package com.everybodv.habibulquran.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everybodv.habibulquran.data.remote.response.GeneralResponse
import com.everybodv.habibulquran.data.remote.response.LoginResponse
import com.everybodv.habibulquran.data.remote.response.User
import com.everybodv.habibulquran.data.remote.retrofit.AuthApiService
import com.everybodv.habibulquran.data.remote.retrofit.AuthConfig
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.Event
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    private val login = MutableLiveData<LoginResponse>()

    private val _registerData = MutableLiveData<GeneralResponse>()
    val registerData: LiveData<GeneralResponse> = _registerData

    private val _loginData = MutableLiveData<User>()
    val loginData: LiveData<User> = _loginData

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
                                _loginData.value = User(it.id, it.name, it.email, it.password, it.jenisKelamin, it.birthdate, it.image, it.isVerified, it.token)
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

        AuthConfig.getAuthService().register(name, email, password, image, gender, birthYear, birthMonth, birthDay)
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
        AuthConfig.getAuthService().editProfile(id, id, name, email, gender, birthYear, birthMonth, birthDay)
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
}