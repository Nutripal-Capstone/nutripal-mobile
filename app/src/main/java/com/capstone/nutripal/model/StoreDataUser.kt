package com.capstone.nutripal.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class StoreDataUser (private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataUser")

        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwtToken")
        private val STATE_KEY = booleanPreferencesKey("state")
    }

    fun getUserToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    fun getUserJwtToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[JWT_TOKEN_KEY] ?: ""
        }
    }

    fun getState(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }

    suspend fun login(token: String?) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token ?: ""
            preferences[STATE_KEY] = true
        }
    }

    suspend fun saveJwt(jwtToken: String?) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN_KEY] = jwtToken ?: ""
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences.remove(TOKEN_KEY)
            preferences.remove(JWT_TOKEN_KEY)
        }
    }
}