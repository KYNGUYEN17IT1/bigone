package com.bigone.oneone.assistant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bigone.oneone.data.AssistantDao

class AssistantViewModelFactory (
        private val dataSource: AssistantDao,
        private val application: Application):ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AssistantViewModel::class.java)){
            return AssistantViewModel(dataSource,application) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}