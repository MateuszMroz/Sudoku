package com.sudoku.game.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sudoku.game.model.Difficulty
import com.sudoku.game.model.Statistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "statistics")

class StatisticsRepository(private val context: Context) {
    
    private object PreferencesKeys {
        val GAMES_PLAYED = intPreferencesKey("games_played")
        val GAMES_COMPLETED = intPreferencesKey("games_completed")
        val TOTAL_TIME = longPreferencesKey("total_time")
        val BEST_TIME_EASY = longPreferencesKey("best_time_easy")
        val BEST_TIME_MEDIUM = longPreferencesKey("best_time_medium")
        val BEST_TIME_HARD = longPreferencesKey("best_time_hard")
        val BEST_TIME_EXPERT = longPreferencesKey("best_time_expert")
        val EASY_COMPLETED = intPreferencesKey("easy_completed")
        val MEDIUM_COMPLETED = intPreferencesKey("medium_completed")
        val HARD_COMPLETED = intPreferencesKey("hard_completed")
        val EXPERT_COMPLETED = intPreferencesKey("expert_completed")
        val CURRENT_STREAK = intPreferencesKey("current_streak")
        val LONGEST_STREAK = intPreferencesKey("longest_streak")
    }
    
    val statisticsFlow: Flow<Statistics> = context.dataStore.data.map { preferences ->
        Statistics(
            gamesPlayed = preferences[PreferencesKeys.GAMES_PLAYED] ?: 0,
            gamesCompleted = preferences[PreferencesKeys.GAMES_COMPLETED] ?: 0,
            totalTime = preferences[PreferencesKeys.TOTAL_TIME] ?: 0L,
            bestTimeEasy = preferences[PreferencesKeys.BEST_TIME_EASY] ?: Long.MAX_VALUE,
            bestTimeMedium = preferences[PreferencesKeys.BEST_TIME_MEDIUM] ?: Long.MAX_VALUE,
            bestTimeHard = preferences[PreferencesKeys.BEST_TIME_HARD] ?: Long.MAX_VALUE,
            bestTimeExpert = preferences[PreferencesKeys.BEST_TIME_EXPERT] ?: Long.MAX_VALUE,
            easyCompleted = preferences[PreferencesKeys.EASY_COMPLETED] ?: 0,
            mediumCompleted = preferences[PreferencesKeys.MEDIUM_COMPLETED] ?: 0,
            hardCompleted = preferences[PreferencesKeys.HARD_COMPLETED] ?: 0,
            expertCompleted = preferences[PreferencesKeys.EXPERT_COMPLETED] ?: 0,
            currentStreak = preferences[PreferencesKeys.CURRENT_STREAK] ?: 0,
            longestStreak = preferences[PreferencesKeys.LONGEST_STREAK] ?: 0
        )
    }
    
    suspend fun updateStatisticsOnGameComplete(difficulty: Difficulty, time: Long) {
        context.dataStore.edit { preferences ->
            val gamesPlayed = (preferences[PreferencesKeys.GAMES_PLAYED] ?: 0) + 1
            val gamesCompleted = (preferences[PreferencesKeys.GAMES_COMPLETED] ?: 0) + 1
            val totalTime = (preferences[PreferencesKeys.TOTAL_TIME] ?: 0L) + time
            val currentStreak = (preferences[PreferencesKeys.CURRENT_STREAK] ?: 0) + 1
            val longestStreak = preferences[PreferencesKeys.LONGEST_STREAK] ?: 0
            
            preferences[PreferencesKeys.GAMES_PLAYED] = gamesPlayed
            preferences[PreferencesKeys.GAMES_COMPLETED] = gamesCompleted
            preferences[PreferencesKeys.TOTAL_TIME] = totalTime
            preferences[PreferencesKeys.CURRENT_STREAK] = currentStreak
            
            if (currentStreak > longestStreak) {
                preferences[PreferencesKeys.LONGEST_STREAK] = currentStreak
            }
            
            // Update best time for the difficulty level
            when (difficulty) {
                Difficulty.EASY -> {
                    val bestTime = preferences[PreferencesKeys.BEST_TIME_EASY] ?: Long.MAX_VALUE
                    if (time < bestTime) {
                        preferences[PreferencesKeys.BEST_TIME_EASY] = time
                    }
                    preferences[PreferencesKeys.EASY_COMPLETED] = 
                        (preferences[PreferencesKeys.EASY_COMPLETED] ?: 0) + 1
                }
                Difficulty.MEDIUM -> {
                    val bestTime = preferences[PreferencesKeys.BEST_TIME_MEDIUM] ?: Long.MAX_VALUE
                    if (time < bestTime) {
                        preferences[PreferencesKeys.BEST_TIME_MEDIUM] = time
                    }
                    preferences[PreferencesKeys.MEDIUM_COMPLETED] = 
                        (preferences[PreferencesKeys.MEDIUM_COMPLETED] ?: 0) + 1
                }
                Difficulty.HARD -> {
                    val bestTime = preferences[PreferencesKeys.BEST_TIME_HARD] ?: Long.MAX_VALUE
                    if (time < bestTime) {
                        preferences[PreferencesKeys.BEST_TIME_HARD] = time
                    }
                    preferences[PreferencesKeys.HARD_COMPLETED] = 
                        (preferences[PreferencesKeys.HARD_COMPLETED] ?: 0) + 1
                }
                Difficulty.EXPERT -> {
                    val bestTime = preferences[PreferencesKeys.BEST_TIME_EXPERT] ?: Long.MAX_VALUE
                    if (time < bestTime) {
                        preferences[PreferencesKeys.BEST_TIME_EXPERT] = time
                    }
                    preferences[PreferencesKeys.EXPERT_COMPLETED] = 
                        (preferences[PreferencesKeys.EXPERT_COMPLETED] ?: 0) + 1
                }
            }
        }
    }
    
    suspend fun incrementGamesPlayed() {
        context.dataStore.edit { preferences ->
            val gamesPlayed = (preferences[PreferencesKeys.GAMES_PLAYED] ?: 0) + 1
            preferences[PreferencesKeys.GAMES_PLAYED] = gamesPlayed
        }
    }
    
    suspend fun resetStatistics() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

