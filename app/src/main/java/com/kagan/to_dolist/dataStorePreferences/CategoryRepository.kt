package com.kagan.to_dolist.dataStorePreferences

import android.content.Context
import android.telecom.CallAudioState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.kagan.to_dolist.databinding.CategoryShoppingBinding
import kotlinx.coroutines.flow.map

class CategoryRepository(context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "category_prefs")

    companion object {
        val CATEGORY_PERSONAL = preferencesKey<Boolean>("CATEGORY_PERSONAL")
        val CATEGORY_MEETING = preferencesKey<Boolean>("CATEGORY_MEETING")
        val CATEGORY_SHOPPING = preferencesKey<Boolean>("CATEGORY_SHOPPING")
        val CATEGORY_STUDY = preferencesKey<Boolean>("CATEGORY_STUDY")
        val CATEGORY_WORK = preferencesKey<Boolean>("CATEGORY_WORK")
    }

    suspend fun saveCategory(
        personal: Boolean,
        meeting: Boolean,
        shopping: Boolean,
        study: Boolean,
        work: Boolean
    ) {
        dataStore.edit {
            it[CATEGORY_PERSONAL] = personal
            it[CATEGORY_MEETING] = meeting
            it[CATEGORY_SHOPPING] = shopping
            it[CATEGORY_STUDY] = study
            it[CATEGORY_WORK] = work
        }
    }

    val getPersonalFlow = dataStore.data.map {
        it[CATEGORY_PERSONAL] ?: false
    }

    val getMeetingFlow = dataStore.data.map {
        it[CATEGORY_MEETING] ?: false
    }

    val getShoppingFlow = dataStore.data.map {
        it[CATEGORY_SHOPPING] ?: false
    }
    val getStudyFlow = dataStore.data.map {
        it[CATEGORY_STUDY] ?: false
    }

    val getWorkFlow = dataStore.data.map {
        it[CATEGORY_WORK] ?: false
    }

}