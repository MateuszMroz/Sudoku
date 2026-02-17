package com.sudoku.game.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sudoku.game.R

@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    onNotesClick: () -> Unit,
    onHintClick: () -> Unit,
    notesMode: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Buttons 1-5
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 1..5) {
                Button(
                    onClick = { onNumberClick(i) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = i.toString())
                }
            }
        }
        
        // Buttons 6-9 + Clear
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 6..9) {
                Button(
                    onClick = { onNumberClick(i) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = i.toString())
                }
            }
            IconButton(
                onClick = onClearClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.game_clear)
                )
            }
        }
        
        // Helper buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onNotesClick,
                modifier = Modifier.weight(1f),
                colors = if (notesMode) {
                    ButtonDefaults.outlinedButtonColors()
                } else {
                    ButtonDefaults.outlinedButtonColors()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.game_notes),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.game_notes))
            }
            
            OutlinedButton(
                onClick = onHintClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.game_hint),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.game_hint))
            }
        }
    }
}

