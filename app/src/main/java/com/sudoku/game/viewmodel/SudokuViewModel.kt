package com.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudoku.game.data.StatisticsRepository
import com.sudoku.game.logic.SudokuGenerator
import com.sudoku.game.logic.SudokuValidator
import com.sudoku.game.model.Cell
import com.sudoku.game.model.Difficulty
import com.sudoku.game.model.GameState
import com.sudoku.game.model.Statistics
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SudokuViewModel(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {
    
    private val generator = SudokuGenerator()
    private val validator = SudokuValidator()
    
    private val _gameState = MutableStateFlow(
        GameState(
            board = List(9) { row ->
                List(9) { col ->
                    Cell(row, col, 0)
                }
            },
            solution = List(9) { List(9) { 0 } }
        )
    )
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    
    private val _statistics = MutableStateFlow(Statistics())
    val statistics: StateFlow<Statistics> = _statistics.asStateFlow()
    
    private var timerJob: Job? = null
    
    init {
        viewModelScope.launch {
            statisticsRepository.statisticsFlow.collect { stats ->
                _statistics.value = stats
            }
        }
    }
    
    fun startNewGame(difficulty: Difficulty) {
        val (board, solution) = generator.generatePuzzle(difficulty)
        _gameState.value = GameState(
            board = board,
            solution = solution,
            difficulty = difficulty,
            selectedCell = null,
            isComplete = false,
            mistakes = 0,
            timeElapsed = 0L
        )
        
        startTimer()
        
        viewModelScope.launch {
            statisticsRepository.incrementGamesPlayed()
        }
    }
    
    fun selectCell(row: Int, col: Int) {
        val currentCell = _gameState.value.selectedCell
        _gameState.value = _gameState.value.copy(
            selectedCell = if (currentCell == Pair(row, col)) null else Pair(row, col)
        )
    }
    
    fun enterNumber(number: Int) {
        val selected = _gameState.value.selectedCell ?: return
        val (row, col) = selected
        val cell = _gameState.value.board[row][col]
        
        if (cell.isInitial) return
        
        if (_gameState.value.notesMode) {
            // Notes mode
            val newNotes = if (cell.notes.contains(number)) {
                cell.notes - number
            } else {
                cell.notes + number
            }
            updateCell(row, col, cell.copy(notes = newNotes))
        } else {
            // Normal input mode
            val newCell = cell.copy(
                value = number,
                notes = emptySet()
            )
            updateCell(row, col, newCell)
            
            // Check if the move is valid
            if (number != 0 && !validator.isValidMove(_gameState.value.board, row, col, number)) {
                _gameState.value = _gameState.value.copy(
                    mistakes = _gameState.value.mistakes + 1
                )
            }
            
            // Check if the game is complete
            checkCompletion()
        }
    }
    
    fun clearCell() {
        val selected = _gameState.value.selectedCell ?: return
        val (row, col) = selected
        val cell = _gameState.value.board[row][col]
        
        if (cell.isInitial) return
        
        updateCell(row, col, cell.copy(value = 0, notes = emptySet()))
    }
    
    fun toggleNotesMode() {
        _gameState.value = _gameState.value.copy(
            notesMode = !_gameState.value.notesMode
        )
    }
    
    fun getHint() {
        val selected = _gameState.value.selectedCell ?: return
        val (row, col) = selected
        val cell = _gameState.value.board[row][col]
        
        if (cell.isInitial) return
        
        val correctValue = _gameState.value.solution[row][col]
        updateCell(row, col, cell.copy(value = correctValue, notes = emptySet()))
        
        checkCompletion()
    }
    
    private fun updateCell(row: Int, col: Int, newCell: Cell) {
        val newBoard = _gameState.value.board.mapIndexed { r, rowList ->
            if (r == row) {
                rowList.mapIndexed { c, cell ->
                    if (c == col) newCell else cell
                }
            } else {
                rowList
            }
        }
        
        // Check for errors
        val boardWithErrors = validator.checkErrors(newBoard)
        
        _gameState.value = _gameState.value.copy(board = boardWithErrors)
    }
    
    private fun checkCompletion() {
        if (validator.isComplete(_gameState.value.board)) {
            _gameState.value = _gameState.value.copy(isComplete = true)
            stopTimer()
            
            viewModelScope.launch {
                statisticsRepository.updateStatisticsOnGameComplete(
                    _gameState.value.difficulty,
                    _gameState.value.timeElapsed
                )
            }
        }
    }
    
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _gameState.value = _gameState.value.copy(
                    timeElapsed = _gameState.value.timeElapsed + 1
                )
            }
        }
    }
    
    private fun stopTimer() {
        timerJob?.cancel()
    }
    
    fun pauseGame() {
        stopTimer()
    }
    
    fun resumeGame() {
        if (!_gameState.value.isComplete) {
            startTimer()
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}

