package com.mammates.mammates_seller_v1.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenPreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val TOKEN = stringPreferencesKey("token")

    suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences[TOKEN] = ""
        }
    }

}