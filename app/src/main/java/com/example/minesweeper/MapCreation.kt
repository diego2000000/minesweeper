package com.example.minesweeper


var amountOfMines = 0
var minesModifier = 20
fun createMap(row: Int, column: Int): Array<Array<Int>> {
    var size = row //must be bigger than 2
    var size1 = column //must be bigger than 2
    val fullMapsize: Float = size.toFloat() * size1.toFloat()
    var columns = Array(size) { Array(size1) { 0 } }


    //amount of mines calculation

    //println(fullMapsize) //debug
    var minesWithinLimit = false
    while (minesWithinLimit == false) {
        if(fullMapsize<(8*8)) {
            amountOfMines = (1..(8*8)).random()
        } else if (fullMapsize<(16*16)){
            amountOfMines = (10..(16*16)).random()
        } else{
            amountOfMines = (40..(25*25)).random()
        }

        if (amountOfMines < (fullMapsize - ((fullMapsize / 100) * (100 - minesModifier)))) {
            minesWithinLimit = true
        }
        //println(amountOfMines) //debug
    }
    //mine placement
    for (i in 0..(amountOfMines - 1)) {
        var x = (0..(size - 1)).random()
        var y = (0..(size1 - 1)).random()
        // print("X=" + x + "Y=" + y)
        if (columns[x][y] != 9) {
            columns[x][y] = 9
        } else {
            var mineDubblePlaced = true
            while (mineDubblePlaced == true) {
                x = (0..(size - 1)).random()
                y = (0..(size1 - 1)).random()
                // println("dubble placed"+"X=" + x + "Y=" + y) //debug
                if (columns[x][y] != 9) {
                    columns[x][y] = 9
                    mineDubblePlaced = false
                }
            }
        }
    }

    fun check(x: Int, y: Int) {
        var numberValue = 0
        if (columns[x][y] != 9) {
            when (x) {
                0 -> when (y) {
                    0 -> {
                        if (columns[x][y + 1] == 9) { // right
                            numberValue++
                        }
                        if (columns[x + 1][y] == 9) {   //down
                            numberValue++
                        }
                        if (columns[x + 1][y + 1] == 9) { //down right
                            numberValue++
                        }
                    }

                    (size1 - 1) -> {
                        if (columns[x][y - 1] == 9) { //left
                            numberValue++
                        }
                        if (columns[x + 1][y] == 9) {  //down
                            numberValue++
                        }
                        if (columns[x + 1][y - 1] == 9) { //down left
                            numberValue++
                        }
                    }

                    else -> {
                        if (columns[x][y - 1] == 9) {   //left
                            numberValue++
                        }
                        if (columns[x][y + 1] == 9) {   //right
                            numberValue++
                        }
                        if (columns[x + 1][y] == 9) {   //down
                            numberValue++
                        }
                        if (columns[x + 1][y - 1] == 9) { //down left
                            numberValue++
                        }
                        if (columns[x + 1][y + 1] == 9) { //down right
                            numberValue++
                        }
                    }
                }

                (size - 1) -> {
                    when (y) {
                        0 -> {
                            if (columns[x - 1][y] == 9) { //up
                                numberValue++
                            }
                            if (columns[x][y + 1] == 9) { //right
                                numberValue++
                            }
                            if (columns[x - 1][y + 1] == 9) {  //up right
                                numberValue++
                            }
                        }

                        (size1 - 1) -> {
                            if (columns[x - 1][y] == 9) {   //up
                                numberValue++
                            }
                            if (columns[x][y - 1] == 9) { //left
                                numberValue++
                            }
                            if (columns[x - 1][y - 1] == 9) { //up left
                                numberValue++
                            }
                        }

                        else -> {
                            if (columns[x - 1][y] == 9) { //up
                                numberValue++
                            }
                            if (columns[x][y + 1] == 9) { //right
                                numberValue++
                            }
                            if (columns[x][y - 1] == 9) { //left
                                numberValue++
                            }
                            if (columns[x - 1][y - 1] == 9) { //up left
                                numberValue++
                            }
                            if (columns[x - 1][y + 1] == 9) { //up right
                                numberValue++
                            }

                        }
                    }
                }

                else -> {
                    when (y) {
                        0 -> {

                            if (columns[x - 1][y] == 9) { //up
                                numberValue++
                            }
                            if (columns[x][y + 1] == 9) { //right
                                numberValue++
                            }
                            if (columns[x + 1][y] == 9) { //down
                                numberValue++
                            }
                            if (columns[x - 1][y + 1] == 9) { //up right
                                numberValue++
                            }
                            if (columns[x + 1][y + 1] == 9) { //down right
                                numberValue++
                            }
                        }

                        (size1 - 1) -> {

                            if (columns[x - 1][y] == 9) {   //up
                                numberValue++
                            }
                            if (columns[x][y - 1] == 9) { //left
                                numberValue++
                            }
                            if (columns[x + 1][y] == 9) { //down
                                numberValue++
                            }
                            if (columns[x - 1][y - 1] == 9) { //up left
                                numberValue++
                            }
                            if (columns[x + 1][y - 1] == 9) { //down left
                                numberValue++
                            }
                        }

                        else -> {

                            if (columns[x + 1][y] == 9) { //down
                                numberValue++
                            }
                            if (columns[x + 1][y - 1] == 9) { //down left
                                numberValue++
                            }
                            if (columns[x + 1][y + 1] == 9) { //down right
                                numberValue++
                            }
                            if (columns[x][y + 1] == 9) { //right
                                numberValue++
                            }
                            if (columns[x - 1][y + 1] == 9) {//up right
                                numberValue++
                            }
                            if (columns[x][y - 1] == 9) {//left
                                numberValue++
                            }
                            if (columns[x - 1][y - 1] == 9) {//up left
                                numberValue++
                            }
                            if (columns[x - 1][y] == 9) {//up
                                numberValue++
                            }
                        }
                    }
                }
            }
            columns[x][y] = numberValue
        }
    }

    for (y in 0..(columns[0].size - 1)) {
        for (x in 0..(columns.size - 1)) {
            check(x, y)
        }
    }
    return columns
}
