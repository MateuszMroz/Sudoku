package com.sudoku.game.model

data class Statistics(
    val gamesPlayed: Int = 0,
    val gamesCompleted: Int = 0,
    val totalTime: Long = 0L,
    val bestTimeEasy: Long = Long.MAX_VALUE,
    val bestTimeMedium: Long = Long.MAX_VALUE,
    val bestTimeHard: Long = Long.MAX_VALUE,
    val bestTimeExpert: Long = Long.MAX_VALUE,
    val easyCompleted: Int = 0,
    val mediumCompleted: Int = 0,
    val hardCompleted: Int = 0,
    val expertCompleted: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0
) {
    fun getBestTime(difficulty: Difficulty): Long {
        return when (difficulty) {
            Difficulty.EASY -> bestTimeEasy
            Difficulty.MEDIUM -> bestTimeMedium
            Difficulty.HARD -> bestTimeHard
            Difficulty.EXPERT -> bestTimeExpert
        }
    }
    
    fun getCompletedCount(difficulty: Difficulty): Int {
        return when (difficulty) {
            Difficulty.EASY -> easyCompleted
            Difficulty.MEDIUM -> mediumCompleted
            Difficulty.HARD -> hardCompleted
            Difficulty.EXPERT -> expertCompleted
        }
    }
}

