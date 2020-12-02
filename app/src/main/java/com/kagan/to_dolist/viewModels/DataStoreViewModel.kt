package com.kagan.to_dolist.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kagan.to_dolist.dataStore.NameDataStore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {

    private val nameDataStore = NameDataStore(application)

    val user = nameDataStore.getUser.asLiveData()

    fun saveUser(user: String) {
        viewModelScope.launch(IO) {
            nameDataStore.saveUser(user)
        }
    }
}