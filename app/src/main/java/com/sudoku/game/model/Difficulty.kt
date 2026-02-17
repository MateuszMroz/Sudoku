package com.sudoku.game.model

import androidx.annotation.StringRes
import com.sudoku.game.R

enum class Difficulty(val cellsToRemove: Int, @StringRes val displayNameRes: Int) {
    EASY(30, R.string.difficulty_easy),
    MEDIUM(40, R.string.difficulty_medium),
    HARD(50, R.string.difficulty_hard),
    EXPERT(60, R.string.difficulty_expert)
}

