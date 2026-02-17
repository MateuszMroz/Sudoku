package com.sudoku.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sudoku.game.data.StatisticsRepository
import com.sudoku.game.model.Difficulty
import com.sudoku.game.ui.screens.GameScreen
import com.sudoku.game.ui.screens.MenuScreen
import com.sudoku.game.ui.screens.StatisticsScreen
import com.sudoku.game.ui.theme.SudokuTheme
import com.sudoku.game.viewmodel.SudokuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = StatisticsRepository(applicationContext)
        
        setContent {
            SudokuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SudokuApp(repository = repository)
                }
            }
        }
    }
}

enum class Screen {
    MENU, GAME, STATISTICS
}

@Composable
fun SudokuApp(repository: StatisticsRepository) {
    val viewModel: SudokuViewModel = viewModel(
        factory = SudokuViewModelFactory(repository)
    )
    
    var currentScreen by remember { mutableStateOf(Screen.MENU) }
    val gameState by viewModel.gameState.collectAsState()
    val statistics by viewModel.statistics.collectAsState()
    
    when (currentScreen) {
        Screen.MENU -> {
            MenuScreen(
                onDifficultySelected = { difficulty ->
                    viewModel.startNewGame(difficulty)
                    currentScreen = Screen.GAME
                },
                onStatisticsClick = {
                    currentScreen = Screen.STATISTICS
                }
            )
        }
        
        Screen.GAME -> {
            GameScreen(
                gameState = gameState,
                onCellClick = { row, col ->
                    viewModel.selectCell(row, col)
                },
                onNumberClick = { number ->
                    viewModel.enterNumber(number)
                },
                onClearClick = {
                    viewModel.clearCell()
                },
                onNotesClick = {
                    viewModel.toggleNotesMode()
                },
                onHintClick = {
                    viewModel.getHint()
                },
                onBackClick = {
                    viewModel.pauseGame()
                    currentScreen = Screen.MENU
                }
            )
        }
        
        Screen.STATISTICS -> {
            StatisticsScreen(
                statistics = statistics,
                onBackClick = {
                    currentScreen = Screen.MENU
                }
            )
        }
    }
}

