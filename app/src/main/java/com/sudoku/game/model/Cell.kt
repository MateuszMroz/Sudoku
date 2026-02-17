package com.sudoku.game.model

data class Cell(
    val row: Int,
    val col: Int,
    val value: Int,
    val isInitial: Boolean = false,
    val isError: Boolean = false,
    val notes: Set<Int> = emptySet()
)

