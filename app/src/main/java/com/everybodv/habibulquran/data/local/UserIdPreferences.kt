package com.everybodv.habibulquran.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.everybodv.habibulquran.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userid: DataStore<Preferences> by preferencesDataStore(name = Const.USER_ID)

class UserIdPreferences(private val context: Context) {

    private val key = stringPreferencesKey(Const.ID)

    suspend fun setId(id: String) {
        context.userid.edit {
            it[key] = id
        }
    }

    suspend fun deleteId() {
        context.userid.edit {
            it.clear()
        }
    }

    fun getId(): Flow<String> {
        return context.userid.data.map {
            it[key] ?: "NotFound"
        }
    }
}