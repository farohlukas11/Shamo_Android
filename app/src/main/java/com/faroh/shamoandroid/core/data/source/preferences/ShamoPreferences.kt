package com.faroh.shamoandroid.core.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShamoPreferences @Inject constructor(
    private val context: Context
) {
    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun saveUser(userData: RegisterAndLoginResponse) {
        context.userPreferencesDataStore.edit {
            it[USER_TOKEN] = userData.data?.accessToken.toString()
            it[USER_NAME] = userData.data?.user?.name.toString()
            it[USER_USERNAME] = userData.data?.user?.username.toString()
            it[USER_EMAIL] = userData.data?.user?.email.toString()
            it[USER_PROFILE_PHOTO] = userData.data?.user?.profilePhotoUrl.toString()
        }
    }

    fun getUser(): Flow<UserPreferences> = context.userPreferencesDataStore.data.map {
        UserPreferences(
            token = it[USER_TOKEN] ?: "",
            name = it[USER_NAME] ?: "",
            username = it[USER_USERNAME] ?: "",
            email = it[USER_EMAIL] ?: "",
            userProfilePhoto = it[USER_PROFILE_PHOTO] ?: ""
        )
    }

    suspend fun setState(state: Boolean) {
        context.userPreferencesDataStore.edit {
            it[STATE_KEY] = state
        }
    }

    fun getState(): Flow<Boolean> {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }


    companion object {
        const val DATA_STORE_NAME = "shamo_android"

        private val USER_TOKEN = stringPreferencesKey("USER_TOKEN")
        private val USER_NAME = stringPreferencesKey("USER_NAME")
        private val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        private val USER_USERNAME = stringPreferencesKey("USER_USERNAME")
        private val USER_PROFILE_PHOTO = stringPreferencesKey("USER_PROFILE_PHOTO")

        private val STATE_KEY = booleanPreferencesKey("state")
    }
}