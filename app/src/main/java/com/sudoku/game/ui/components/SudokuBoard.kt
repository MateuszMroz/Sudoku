package com.sudoku.game.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudoku.game.model.Cell
import com.sudoku.game.ui.theme.*

@Composable
fun SudokuBoard(
    board: List<List<Cell>>,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            board.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    row.forEachIndexed { colIndex, cell ->
                        SudokuCell(
                            cell = cell,
                            isSelected = selectedCell == Pair(rowIndex, colIndex),
                            isHighlighted = selectedCell?.let { (r, c) ->
                                r == rowIndex || c == colIndex || 
                                (r / 3 == rowIndex / 3 && c / 3 == colIndex / 3)
                            } ?: false,
                            onClick = { onCellClick(rowIndex, colIndex) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
        
        // Draw grid lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellSize = size.width / 9
            
            // Thin lines
            for (i in 1 until 9) {
                if (i % 3 != 0) {
                    // Vertical
                    drawLine(
                        color = GridLineLight,
                        start = Offset(i * cellSize, 0f),
                        end = Offset(i * cellSize, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                    // Horizontal
                    drawLine(
                        color = GridLineLight,
                        start = Offset(0f, i * cellSize),
                        end = Offset(size.width, i * cellSize),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
            
            // Thick lines (every 3 cells)
            for (i in 0..3) {
                // Vertical
                drawLine(
                    color = GridLine,
                    start = Offset(i * 3 * cellSize, 0f),
                    end = Offset(i * 3 * cellSize, size.height),
                    strokeWidth = 3.dp.toPx()
                )
                // Horizontal
                drawLine(
                    color = GridLine,
                    start = Offset(0f, i * 3 * cellSize),
                    end = Offset(size.width, i * 3 * cellSize),
                    strokeWidth = 3.dp.toPx()
                )
            }
        }
    }
}

@Composable
fun SudokuCell(
    cell: Cell,
    isSelected: Boolean,
    isHighlighted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> CellSelected
        isHighlighted -> CellHighlight
        else -> CellBackground
    }
    
    val textColor = when {
        cell.isError -> CellError
        cell.isInitial -> CellInitial
        else -> CellUser
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (cell.value != 0) {
            Text(
                text = cell.value.toString(),
                fontSize = 24.sp,
                fontWeight = if (cell.isInitial) FontWeight.Bold else FontWeight.Normal,
                color = textColor,
                textAlign = TextAlign.Center
            )
        } else if (cell.notes.isNotEmpty()) {
            // Display notes
            NotesGrid(notes = cell.notes)
        }
    }
}

@Composable
fun NotesGrid(notes: Set<Int>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (row in 0 until 3) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0 until 3) {
                    val number = row * 3 + col + 1
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (notes.contains(number)) {
                            Text(
                                text = number.toString(),
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

