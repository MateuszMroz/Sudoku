# Sudoku - Android Game

Modern Sudoku game created in Kotlin using Jetpack Compose and Material Design 3.

## Features

### ✨ Main Features
- **4 difficulty levels**: Easy, Medium, Hard, Expert
- **Smart board generator**: Automatic generation of solvable Sudoku puzzles
- **Real-time validation**: Instant error detection
- **Notes system**: Ability to add notes in cells
- **Hints**: Hint system for challenging moments
- **Timer**: Game time tracking

### 📊 Statistics
- Number of started and completed games
- Best times for each difficulty level
- Total play time
- Streak system
- Statistics for each difficulty level separately

### 🎨 Interface
- **Material Design 3**: Modern, intuitive interface
- **Jetpack Compose**: Smooth animations and responsive UI
- **Color highlighting**: Highlighting of selected cell, row, column, and 3x3 square
- **Error marking**: Visual indication of incorrect moves

## Technologies

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design System**: Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Persistence**: DataStore (for statistics)
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Project Structure

```
app/src/main/java/com/sudoku/game/
├── data/
│   └── StatisticsRepository.kt      # Statistics management
├── logic/
│   ├── SudokuGenerator.kt           # Board generation
│   └── SudokuValidator.kt           # Move validation
├── model/
│   ├── Cell.kt                      # Cell model
│   ├── Difficulty.kt                # Difficulty levels
│   ├── GameState.kt                 # Game state
│   └── Statistics.kt                # Statistics model
├── ui/
│   ├── components/
│   │   ├── NumberPad.kt             # Number pad
│   │   └── SudokuBoard.kt           # Sudoku board
│   ├── screens/
│   │   ├── GameScreen.kt            # Game screen
│   │   ├── MenuScreen.kt            # Main menu
│   │   └── StatisticsScreen.kt      # Statistics screen
│   └── theme/
│       ├── Color.kt                 # App colors
│       ├── Theme.kt                 # Material 3 theme
│       └── Type.kt                  # Typography
├── viewmodel/
│   └── SudokuViewModel.kt           # Game ViewModel
├── MainActivity.kt                  # Main activity
└── SudokuViewModelFactory.kt        # ViewModel factory
```

## How to Run

### Option 1: Android Studio (recommended)
1. Open project in Android Studio
2. Wait for Gradle sync (automatic)
3. Select device/emulator
4. Click Run (▶️)

### Option 2: Command Line
```bash
# Build the project
./gradlew build

# Install on connected device
./gradlew installDebug

# Or install the ready APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Option 3: Ready APK
Ready APK files are located in:
- Debug: `app/build/outputs/apk/debug/app-debug.apk` (7.7 MB)
- Release: `app/build/outputs/apk/release/app-release-unsigned.apk` (5.3 MB)

## Requirements

- Android Studio Hedgehog | 2023.1.1 or newer
- JDK 8 or newer
- Android SDK 34
- Device with Android 7.0 (API 24) or newer

## Game Rules

Sudoku is a number logic game where the goal is to fill a 9×9 grid with digits from 1 to 9 so that:

1. Each row contains all digits from 1 to 9
2. Each column contains all digits from 1 to 9
3. Each 3×3 square contains all digits from 1 to 9

## In-Game Features

- **Cell selection**: Click on a cell to select it
- **Number input**: Use the number pad at the bottom of the screen
- **Notes**: Enable notes mode to add possible values in cells
- **Hint**: Click the hint button to reveal the correct value for the selected cell
- **Clear**: Use the delete button to clear a cell

## License

This project was created as a demonstration of programming skills in Kotlin and Jetpack Compose.
