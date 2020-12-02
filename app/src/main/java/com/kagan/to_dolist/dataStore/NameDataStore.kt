package com.kagan.to_dolist.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.kagan.to_dolist.constants.Constant.USER
import kotlinx.coroutines.flow.map

class NameDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore("name")

    companion object {
        val NAME = preferencesKey<String>(USER)
    }

    suspend fun saveUser(user: String) {
        dataStore.edit {
            it[NAME] = user
        }
    }

    val getUser = dataStore.data.map {
        it[NAME] ?: ""
    }
}