package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.WaterRecord
import com.example.data.WaterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class WaterViewModel(private val repository: WaterRepository) : ViewModel() {

    // Water target in ml (2L = 2000ml)
    val dailyGoalMl: Int = 2000

    private fun getStartOfToday(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    // Observe today's records
    val todayRecords: StateFlow<List<WaterRecord>> = repository.allRecords
        .map { records ->
            val startOfToday = getStartOfToday()
            records.filter { it.timestamp >= startOfToday }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Sum of today's intake
    val totalIntakeMl: StateFlow<Int> = todayRecords
        .map { records -> records.sumOf { it.amountMl } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Progress as a float [0f..1f]
    val progress: StateFlow<Float> = totalIntakeMl
        .map { intake ->
            if (intake >= dailyGoalMl) 1f else intake.toFloat() / dailyGoalMl
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    // Add water in ml (defaults to 250ml)
    fun addWater(amountMl: Int = 250) {
        viewModelScope.launch {
            repository.insert(WaterRecord(amountMl = amountMl))
        }
    }

    // Delete a specific record
    fun deleteRecord(recordId: Long) {
        viewModelScope.launch {
            repository.deleteById(recordId)
        }
    }

    // Reset today's intake (delete all records of today or clear all)
    fun resetToday() {
        viewModelScope.launch {
            // We can clear all database records for simplicity, or delete only today's records.
            // Let's clear all since it's a simple tracker and that's expected for a full reset.
            repository.clearAll()
        }
    }

    // Define ViewModel factory to inject the Repository
    class Factory(private val repository: WaterRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
                return WaterViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
