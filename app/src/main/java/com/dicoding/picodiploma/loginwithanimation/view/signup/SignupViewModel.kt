package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.api.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.model.UserModel
import com.dicoding.picodiploma.loginwithanimation.model.UserPreference
import com.dicoding.picodiploma.loginwithanimation.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import kotlin.math.sign

class SignupViewModel(private val repository: Repository): ViewModel() {

    var registerResponse: MutableLiveData<Call<RegisterResponse>> = MutableLiveData()

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.register(name, email, password)
            registerResponse.value = response
        }
    }
}