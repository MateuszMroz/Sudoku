package com.sudoku.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sudoku.game.data.StatisticsRepository
import com.sudoku.game.viewmodel.SudokuViewModel

class SudokuViewModelFactory(
    private val repository: StatisticsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SudokuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SudokuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

