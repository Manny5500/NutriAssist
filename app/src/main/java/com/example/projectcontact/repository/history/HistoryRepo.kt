package com.example.projectcontact.repository.history

import com.example.projectcontact.model.History

interface HistoryRepo {
    suspend fun getHistory(fullName:String):List<History>
    suspend fun updateHistory(history: History, fullName:String)
    suspend fun saveHistory(history: History, fullName: String)
}