package com.example.minesweeper

import android.os.Bundle
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.minesweeper.ui.theme.MinesweeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinesweeperTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
fun Map(name: String, modifier: Modifier= Modifier){
    val columns = createMap(row,column)
    val emptyTile= painterResource(R.drawable.empty0)
    val oneTile=painterResource(R.drawable.empty1)
    val twoTile=painterResource(R.drawable.empty2)
    val threeTile=painterResource(R.drawable.empty3)
    val fourTile=painterResource(R.drawable.empty4)
    val fiveTile=painterResource(R.drawable.empty5)
    val sixTile=painterResource(R.drawable.empty6)
    val sevenTile=painterResource(R.drawable.empty7)
    val eightTile=painterResource(R.drawable.empty8)
    val mineTile=painterResource(R.drawable.mine)
    val flag=painterResource(R.drawable.flag)
    var xpos by remember { mutableStateOf(0) }
    var ypos by remember { mutableStateOf(0) }


    Box(
        modifier= Modifier.fillMaxWidth().fillMaxWidth(),
        contentAlignment = Alignment.Center,
        
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier= Modifier.height(38.dp))
            Text("Mines $amountOfMines")

            Spacer(modifier= Modifier.height(16.dp))

            BoxWithConstraints(
                modifier= Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)

            ) {
                val tileSize = maxWidth /row

                Column(
                ) {
                    for (y in 0 .. (columns[0].size -1 )){
                        Row() {
                            for (x in 0..(columns.size-1)){
                                when(columns[x][y]){
                                    0-> {
                                        Image(
                                            emptyTile,
                                            contentDescription = null,
                                            modifier= Modifier
                                                .size(tileSize)
                                                .combinedClickable(
                                                    onClick = {
                                                        xpos = x
                                                        ypos = y
                                                    },
                                                    onLongClick = {}

                                                )

                                        )
                                    }
                                    1->{Image(
                                        oneTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            }
                                    )}
                                    2->{Image(
                                        twoTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            }
                                    )}
                                    3->{Image(
                                        threeTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            }                                    )}
                                    4->{Image(
                                        fourTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            })}
                                    5->{Image(
                                        fiveTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            }
                                    )}
                                    6->{Image(
                                        sixTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            }
                                    )}
                                    7->{Image(
                                        sevenTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            })}
                                    8->{Image(
                                        eightTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            })}
                                    9->{Image(
                                        mineTile,
                                        contentDescription = null,
                                        modifier= Modifier
                                            .size(tileSize)
                                            .clickable{
                                                xpos = x
                                                ypos = y
                                            })}
                                }

                        }

                }

            }
                    Column() {
                        Spacer(modifier= Modifier.height(38.dp))
                        Text("Position $xpos $ypos")
                    }
        }
    }
            Spacer(modifier= Modifier.height(16.dp))




     }
 }

}

