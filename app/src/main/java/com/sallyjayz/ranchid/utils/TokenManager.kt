package com.sallyjayz.ranchid.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sallyjayz.ranchid.hilt.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val USER_KEY = stringPreferencesKey("username")
        private val USER_NAME = stringPreferencesKey("name")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_ROLE = stringPreferencesKey("role")
//        private val USER_PHOTO = stringPreferencesKey("photo")
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    fun getUsername(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_KEY]
        }
    }

    fun getName(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME]
        }
    }

    fun getEmail(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_EMAIL]
        }
    }

    fun getRole(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ROLE]
        }
    }

    /*fun getPhoto(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_PHOTO]
        }
    }
*/
    suspend fun saveToken(token: String, username: String, name: String,
                          email: String, role: String/*, photo: String*/) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[USER_KEY] = username
            preferences[USER_NAME] = name
            preferences[USER_EMAIL] = email
            preferences[USER_ROLE] = role
//            preferences[USER_PHOTO] = photo
        }
    }


    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(USER_KEY)
        }
    }

}