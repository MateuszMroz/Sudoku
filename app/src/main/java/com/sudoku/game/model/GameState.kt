package com.sudoku.game.model

data class GameState(
    val board: List<List<Cell>>,
    val solution: List<List<Int>>,
    val selectedCell: Pair<Int, Int>? = null,
    val difficulty: Difficulty = Difficulty.EASY,
    val isComplete: Boolean = false,
    val mistakes: Int = 0,
    val timeElapsed: Long = 0L,
    val notesMode: Boolean = false
)

