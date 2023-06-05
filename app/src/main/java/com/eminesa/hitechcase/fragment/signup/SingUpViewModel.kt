package com.eminesa.hitechcase.fragment.signup

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SingUpViewModel : ViewModel() {

    private val _email = MutableLiveData<String?>()
    private val _password = MutableLiveData<String?>()
    private val _webSite = MutableLiveData<String?>()
    private val _firstName = MutableLiveData<String?>()
    private val _uri= MutableLiveData<Uri?>()

    val uri: LiveData<Uri?> get() = _uri
    val email: LiveData<String?> get() = _email
    val password: LiveData<String?> get() = _password
    val webSite: LiveData<String?> get() = _webSite
    val firstName: LiveData<String?> get() = _firstName

    private val _isInputValid = MutableLiveData<Boolean>()
    val isInputValid: LiveData<Boolean> get() = _isInputValid

    private val _isWebsiteValid = MutableLiveData<Boolean>()
    val isWebsiteValid: LiveData<Boolean> get() = _isWebsiteValid

    private val _isFirstNameValid = MutableLiveData<Boolean>()
    val isFirstNameValid: LiveData<Boolean> get() = _isFirstNameValid

    private val _isMailValid = MutableLiveData<Boolean>()
    val isMailValid: LiveData<Boolean> get() = _isMailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    init {
        _isInputValid.value = false
        _isWebsiteValid.value = false
        _isFirstNameValid.value = false
        _isMailValid.value = false
        _isPasswordValid.value = false
     }

    fun setEmail(email: String) {
        _email.value = email
        validateInput()
    }

    fun setPassword(password: String) {
        _password.value = password
        validateInput()
    }

    fun setWebSite(webSite: String) {
        _webSite.value = webSite
        validateInput()
    }

    fun setFirstName(firstName: String) {
        _firstName.value = firstName
        validateInput()
    }

    fun setUri(uri: Uri) {
        _uri.value = uri
        validateInput()
    }

    private fun validateInput() {

        val isEmailValid = _email.value?.let { isValidEmail(it) } ?: false
        _isMailValid.value = isEmailValid

        val isPasswordValid = _password.value?.let { isValidPassword(it) } ?: false
        _isPasswordValid.value = isPasswordValid

        val isWebsiteValid = _webSite.value?.let { isValidWebSite(it) } ?: false
        _isWebsiteValid.value = isWebsiteValid

        val isFirstNameValid = _firstName.value?.let { isValidFirstName(it) } ?: false
        _isFirstNameValid.value = isFirstNameValid

        val isUriNotNull = _uri.value?.let { isUriEmpty(it) } ?: false

        _isInputValid.value = isEmailValid && isPasswordValid && isWebsiteValid && isFirstNameValid && isUriNotNull
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile(Patterns.EMAIL_ADDRESS.pattern())
        return pattern.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z]).{8,}\$".toRegex()
        return passwordPattern.matches(password)
    }

    private fun isValidWebSite(webSite: String): Boolean {
        val pattern = Pattern.compile(Patterns.WEB_URL.pattern())
        return pattern.matcher(webSite).matches()
    }

    private fun isValidFirstName(firstName: String): Boolean {
        return firstName.length >= 3
    }

    private fun isUriEmpty(uri: Uri?): Boolean {
        return uri?.isAbsolute != null
    }

}