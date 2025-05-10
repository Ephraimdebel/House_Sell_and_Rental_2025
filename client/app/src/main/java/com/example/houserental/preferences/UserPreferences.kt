package com.example.houserental.preferences

import LoginResponse
import UserData
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_ID = stringPreferencesKey("user_id")
    val USER_ROLE = stringPreferencesKey("user_role")
    val USER_TOKEN = stringPreferencesKey("user_token")

    suspend fun saveUser(context: Context, token: String, name: String, email: String, id: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = true
            prefs[USER_NAME] = name
            prefs[USER_EMAIL] = email
            prefs[USER_ID] = id
            prefs[USER_ROLE] = role
            prefs[USER_TOKEN] = token
        }
    }

    suspend fun logout(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear()  // Clear all preferences
        }
    }

    fun getUser(context: Context): Flow<Pair<String?, String?>> {
        return context.dataStore.data.map { prefs ->
            Pair(prefs[USER_NAME], prefs[USER_EMAIL])
        }
    }

    fun isLoggedIn(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[IS_LOGGED_IN] ?: false
        }
    }

    fun getUserDetails(context: Context): Flow<LoginResponse> {
        return context.dataStore.data.map { prefs ->
            LoginResponse(
                msg = "Loaded from preferences",
                token = prefs[USER_TOKEN] ?: "",
                username = prefs[USER_NAME] ?: "",
                userid = prefs[USER_ID]?.toIntOrNull() ?: -1,
                role = prefs[USER_ROLE] ?: "",
                email = prefs[USER_EMAIL] ?:""
            )
        }
    }

}
