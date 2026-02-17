package com.sudoku.game.logic

import com.sudoku.game.model.Cell

class SudokuValidator {
    
    fun isValidMove(board: List<List<Cell>>, row: Int, col: Int, num: Int): Boolean {
        if (num == 0) return true
        
        // Check row
        for (c in 0 until 9) {
            if (c != col && board[row][c].value == num) {
                return false
            }
        }
        
        // Check column
        for (r in 0 until 9) {
            if (r != row && board[r][col].value == num) {
                return false
            }
        }
        
        // Check 3x3 box
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (r in boxRow until boxRow + 3) {
            for (c in boxCol until boxCol + 3) {
                if ((r != row || c != col) && board[r][c].value == num) {
                    return false
                }
            }
        }
        
        return true
    }
    
    fun isComplete(board: List<List<Cell>>): Boolean {
        // Check if all cells are filled
        if (board.any { row -> row.any { it.value == 0 } }) {
            return false
        }
        
        // Check if all moves are valid
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (!isValidMove(board, row, col, board[row][col].value)) {
                    return false
                }
            }
        }
        
        return true
    }
    
    fun checkErrors(board: List<List<Cell>>): List<List<Cell>> {
        return board.mapIndexed { row, rowList ->
            rowList.mapIndexed { col, cell ->
                if (cell.value != 0 && !cell.isInitial) {
                    val isError = !isValidMove(board, row, col, cell.value)
                    cell.copy(isError = isError)
                } else {
                    cell
                }
            }
        }
    }
}

