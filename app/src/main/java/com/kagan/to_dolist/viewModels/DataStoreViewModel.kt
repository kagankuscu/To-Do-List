package com.kagan.to_dolist.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kagan.to_dolist.dataStorePreferences.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {

    private var categoryRepo = CategoryRepository(application)

    val personal = categoryRepo.getPersonalFlow.asLiveData()
    val meeting = categoryRepo.getMeetingFlow.asLiveData()
    val shopping = categoryRepo.getShoppingFlow.asLiveData()
    val study = categoryRepo.getStudyFlow.asLiveData()
    val work = categoryRepo.getWorkFlow.asLiveData()

    fun saveCategory(
        personal: Boolean,
        meeting: Boolean,
        shopping: Boolean,
        study: Boolean,
        work: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepo.saveCategory(personal, meeting, shopping, study, work)
        }
    }
}