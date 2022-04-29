package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.repository.Repository
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import com.dicoding.picodiploma.loginwithanimation.view.signup.SignupViewModel

class RepositoryViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
//            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
//                SignupViewModel(pref) as T
//            }
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
//                LoginViewModel(pref) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}