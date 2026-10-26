package com.example.minesweeper

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.minesweeper.ui.theme.MinesweeperTheme
import java.util.Objects
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.verticalScroll
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinesweeperTheme {
                Scaffold(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) { innerPadding ->
                    Map(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Map(name: String, modifier: Modifier = Modifier) {

    var columns by remember { mutableStateOf(createMap(row, column)) }
    var drawarray by remember { mutableStateOf(Array(row) { Array(column) { 10 } }) }
    var refresh by remember { mutableStateOf(0) }
    val currentRefresh = refresh
    var gameOver by remember { mutableStateOf(false) }
    var gameWon by remember { mutableStateOf(false) }

    var rowInput by remember { mutableStateOf(row.toString()) }
    var columnInput by remember { mutableStateOf(column.toString()) }
    var mineModifer by remember { mutableStateOf(minesModifier.toString()) }

    var settingsOpen by remember { mutableStateOf(false) }
    var sizeError by remember { mutableStateOf(false) }


    var mines by remember { mutableStateOf(amountOfMines) }
    val emptyTile = painterResource(R.drawable.empty0)
    val oneTile = painterResource(R.drawable.empty1)
    val twoTile = painterResource(R.drawable.empty2)
    val threeTile = painterResource(R.drawable.empty3)
    val fourTile = painterResource(R.drawable.empty4)
    val fiveTile = painterResource(R.drawable.empty5)
    val sixTile = painterResource(R.drawable.empty6)
    val sevenTile = painterResource(R.drawable.empty7)
    val eightTile = painterResource(R.drawable.empty8)
    val mineTile = painterResource(R.drawable.mine)
    val flag = painterResource(R.drawable.flag)
    val nothing = painterResource(R.drawable.nothing)
    var xpos by remember { mutableStateOf(0) }
    var ypos by remember { mutableStateOf(0) }

    val context = LocalContext.current

    val vibrator = remember(context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.getSystemService(VibratorManager::class.java)
                ?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text("Mines $mines")
            Spacer(Modifier.width(16.dp))
            TextButton(onClick = {
                rowInput = row.toString()
                columnInput = column.toString()
                sizeError = false
                settingsOpen = true
            }) { Text("Settings") }


            Spacer(modifier = Modifier.height(16.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)

            ) {
                val tileSize = maxWidth / 8

                val horizontalScroll = rememberScrollState()
                val verticalScroll = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(horizontalScroll)
                        .verticalScroll(verticalScroll)
                ) {


                    Column {
                        for (y in 0..(drawarray[0].size - 1)) {
                            Row {
                                for (x in 0..(drawarray.size - 1)) {
                                    when (drawarray[x][y]) {
                                        10 -> {
                                            Image(
                                                nothing, null, Modifier
                                                    .size(tileSize)
                                                    .combinedClickable(
                                                        onClick = {
                                                            if (!gameOver && !gameWon && drawarray[x][y] == 10) {
                                                                if (columns[x][y] == 9) {

                                                                    drawarray[x][y] = 9
                                                                    gameOver = true
                                                                } else {
                                                                    reveal(x, y, columns, drawarray)
                                                                    if (checkWin(
                                                                            columns,
                                                                            drawarray
                                                                        )
                                                                    ) {
                                                                        gameWon = true
                                                                    }
                                                                }

                                                                xpos = x
                                                                ypos = y
                                                            }
                                                            refresh++
                                                        },
                                                        onLongClick = {
                                                            drawarray[x][y] = 11
                                                            xpos = x
                                                            ypos = y
                                                            mines--
                                                            vibrateFlag(vibrator, removing = false)
                                                            refresh++
                                                        })
                                            )
                                        }

                                        11 -> {
                                            Image(
                                                flag, null, Modifier
                                                    .size(tileSize)
                                                    .combinedClickable(onClick = {}, onLongClick = {
                                                        drawarray[x][y] = 10
                                                        xpos = x
                                                        ypos = y
                                                        mines++
                                                        vibrateFlag(vibrator, removing = true)
                                                        refresh++
                                                    })
                                            )
                                        }

                                        0 -> {
                                            Image(
                                                emptyTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        1 -> {
                                            Image(
                                                oneTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        2 -> {
                                            Image(
                                                twoTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        3 -> {
                                            Image(
                                                threeTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        4 -> {
                                            Image(
                                                fourTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        5 -> {
                                            Image(
                                                fiveTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        6 -> {
                                            Image(
                                                sixTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        7 -> {
                                            Image(
                                                sevenTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        8 -> {
                                            Image(
                                                eightTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                        9 -> {
                                            Image(
                                                mineTile,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(tileSize)
                                            )
                                        }

                                    }

                                }

                            }
                        }
                    }
                }

            }
        }


    }
    if (gameOver) {
        AlertDialog(onDismissRequest = {}, title = { Text("Game Over") }, confirmButton = {
            TextButton(onClick = {
                columns = createMap(row, column)
                drawarray = Array(row) {
                    Array(column) { 10 }
                }
                mines = amountOfMines
                gameOver = false
                gameWon = false
                refresh++
            }
            ) { Text("Restart") }
        })
    }
    if (gameWon) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("You Win!")
            },
            text = {
                Text("Congratulations! You won!")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        columns = createMap(row, column)
                        drawarray = Array(row) {
                            Array(column) { 10 }
                        }
                        mines = amountOfMines
                        gameOver = false
                        gameWon = false
                        refresh++
                    }
                ) {
                    Text("Play Again")
                }
            }
        )
    }


    if (settingsOpen) {
        AlertDialog(
            onDismissRequest = {
                settingsOpen = false
            },
            title = {
                Text("Map Settings")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = rowInput,
                        onValueChange = {
                            rowInput = it.filter { c -> c.isDigit() }
                            sizeError = false
                        },
                        label = { Text("Rows (2–25)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = columnInput,
                        onValueChange = {
                            columnInput = it.filter { c -> c.isDigit() }
                            sizeError = false
                        },
                        label = { Text("Columns (3–25)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = mineModifer,
                        onValueChange = {
                            mineModifer = it.filter { c -> c.isDigit() }
                            sizeError = false
                        }, label = { Text("max % of Mines") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    if (sizeError) {
                        Text(
                            text = "Rows must be 2–25 and columns 3–25 and max %of Mines 1-90.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newRows = rowInput.toIntOrNull()
                        val newColumns = columnInput.toIntOrNull()
                        val newMaxMines = mineModifer.toIntOrNull()

                        if (
                            newRows != null &&
                            newColumns != null &&
                            newMaxMines != null &&
                            newRows in 2..25 &&
                            newColumns in 3..25 &&
                            newMaxMines in 1..90
                        ) {
                            row = newRows
                            column = newColumns
                            minesModifier = newMaxMines

                            columns = createMap(newRows, newColumns)

                            drawarray = Array(newRows) {
                                Array(newColumns) { 10 }
                            }

                            mines = amountOfMines
                            gameOver = false
                            gameWon = false
                            xpos = 0
                            ypos = 0
                            refresh++

                            settingsOpen = false
                        } else {
                            sizeError = true
                        }
                    }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        settingsOpen = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

}

fun reveal(x: Int, y: Int, columns: Array<Array<Int>>, drawarray: Array<Array<Int>>) {
    if (x !in columns.indices || y !in columns[0].indices) return
    if (drawarray[x][y] != 10) return
    if (columns[x][y] == 9) return
    drawarray[x][y] = columns[x][y]
    if (columns[x][y] == 0) {
        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue

                reveal(x + dx, y + dy, columns, drawarray)
            }
        }
    }
}

fun checkWin(
    columns: Array<Array<Int>>,
    drawarray: Array<Array<Int>>
): Boolean {
    for (x in columns.indices) {
        for (y in columns[x].indices) {

            if (columns[x][y] != 9 && drawarray[x][y] == 10) {
                return false
            }
        }
    }

    return true
}

fun vibrateFlag(vibrator: Vibrator?, removing: Boolean) {
    if (vibrator == null || !vibrator.hasVibrator()) return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = if (removing) {
            // Two short pulses when removing a flag
            VibrationEffect.createWaveform(
                longArrayOf(0, 100, 50, 120),
                intArrayOf(0, 255, 0, 255),
                -1
            )
        } else {
            // One stronger pulse when placing a flag
            VibrationEffect.createOneShot(180, 255)
        }

        vibrator.vibrate(effect)
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(if (removing) 250 else 180)
    }
}



