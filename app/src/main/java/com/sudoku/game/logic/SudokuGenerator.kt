package com.sudoku.game.logic

import com.sudoku.game.model.Cell
import com.sudoku.game.model.Difficulty
import kotlin.random.Random

class SudokuGenerator {
    
    fun generatePuzzle(difficulty: Difficulty): Pair<List<List<Cell>>, List<List<Int>>> {
        // Generate complete board
        val solution = generateCompleteSudoku()
        
        // Create a copy for removing numbers
        val puzzle = solution.map { it.toMutableList() }.toMutableList()
        
        // Remove numbers according to difficulty level
        removeCells(puzzle, difficulty.cellsToRemove)
        
        // Convert to Cell objects
        val board = puzzle.mapIndexed { row, rowList ->
            rowList.mapIndexed { col, value ->
                Cell(
                    row = row,
                    col = col,
                    value = value,
                    isInitial = value != 0
                )
            }
        }
        
        return Pair(board, solution)
    }
    
    private fun generateCompleteSudoku(): List<List<Int>> {
        val board = MutableList(9) { MutableList(9) { 0 } }
        fillBoard(board)
        return board
    }
    
    private fun fillBoard(board: MutableList<MutableList<Int>>): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    val numbers = (1..9).shuffled()
                    for (num in numbers) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num
                            if (fillBoard(board)) {
                                return true
                            }
                            board[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }
    
    private fun isValid(board: List<List<Int>>, row: Int, col: Int, num: Int): Boolean {
        // Check row
        if (board[row].contains(num)) return false
        
        // Check column
        if (board.any { it[col] == num }) return false
        
        // Check 3x3 box
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (i in boxRow until boxRow + 3) {
            for (j in boxCol until boxCol + 3) {
                if (board[i][j] == num) return false
            }
        }
        
        return true
    }
    
    private fun removeCells(board: MutableList<MutableList<Int>>, count: Int) {
        var removed = 0
        val positions = mutableListOf<Pair<Int, Int>>()
        
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                positions.add(Pair(i, j))
            }
        }
        
        positions.shuffle()
        
        for ((row, col) in positions) {
            if (removed >= count) break
            board[row][col] = 0
            removed++
        }
    }
}

