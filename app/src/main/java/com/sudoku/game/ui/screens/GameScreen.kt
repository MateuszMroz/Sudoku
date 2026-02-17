package com.sudoku.game.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudoku.game.R
import com.sudoku.game.model.GameState
import com.sudoku.game.ui.components.NumberPad
import com.sudoku.game.ui.components.SudokuBoard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameState: GameState,
    onCellClick: (Int, Int) -> Unit,
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    onNotesClick: () -> Unit,
    onHintClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(gameState.difficulty.displayNameRes),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = formatTime(gameState.timeElapsed),
                            fontSize = 14.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.game_back)
                        )
                    }
                },
                actions = {
                    Text(
                        text = stringResource(R.string.game_mistakes, gameState.mistakes),
                        modifier = Modifier.padding(end = 16.dp),
                        fontSize = 16.sp
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SudokuBoard(
                board = gameState.board,
                selectedCell = gameState.selectedCell,
                onCellClick = onCellClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            
            NumberPad(
                onNumberClick = onNumberClick,
                onClearClick = onClearClick,
                onNotesClick = onNotesClick,
                onHintClick = onHintClick,
                notesMode = gameState.notesMode
            )
        }
        
        // Victory dialog
        if (gameState.isComplete) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text(stringResource(R.string.victory_title)) },
                text = {
                    Column {
                        Text(stringResource(R.string.victory_message))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.victory_time, formatTime(gameState.timeElapsed)))
                        Text(stringResource(R.string.victory_mistakes, gameState.mistakes))
                    }
                },
                confirmButton = {
                    TextButton(onClick = onBackClick) {
                        Text(stringResource(R.string.victory_ok))
                    }
                }
            )
        }
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}

