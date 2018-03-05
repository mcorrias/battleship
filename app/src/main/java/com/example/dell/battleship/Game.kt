package com.example.dell.battleship

import android.content.Context
import com.example.dell.battleship.Constants.Companion.EMPTY
import com.example.dell.battleship.Constants.Companion.SHIP
import com.example.dell.battleship.R.id.board
import java.security.SecureRandom
import java.util.*

/**
 * Created by Dell on 05/03/2018.
 */
const val carrier: String = "carrier"//size five
const val battleShip: String = "battleship"//size four
const val cruiser: String = "cruiser"//size three
const val destroyer: String = "destroyer"//size two
const val submarine: String = "submarine"//size one


class Game(@Transient val context: Context){


    val random: Random = Random()
    var rowsNumber: Int = 0
    var columnsNumber: Int = 0
    lateinit var board: Array<IntArray>


    public fun initGame(rows:Int, columns:Int){
        rowsNumber = rows
        columnsNumber = columns
        board = Array(rows, { IntArray(columns) })
        placeRandomShips()
    }

    public fun attack(x:Int,y:Int) : Boolean{
        return board[x][y] != EMPTY
    }

    private fun placeRandomShips(){
        placeRandomShip(5,  carrier)
        placeRandomShip(4,  battleShip)
        placeRandomShip(3,  cruiser)
        placeRandomShip(2,  destroyer)
        placeRandomShip(2,  destroyer)
        placeRandomShip(1,  submarine)
        placeRandomShip(1,  submarine)
    }

    private fun placeRandomShip(size: Int , type:String){

        val isHorizontal: Boolean = (random.nextInt() % 2) != 0
        val topLeftX = getRandomCoordinate(isHorizontal,columnsNumber-size,rowsNumber)
        val topLeftY = getRandomCoordinate(isHorizontal,rowsNumber,columnsNumber-size)

        var isClear = checkIfThereIsRoom(size , isHorizontal , topLeftX , topLeftY)

        if (isClear) {
            for (i in 0..size ) {
                val x = if (isHorizontal) topLeftX + i else topLeftX
                val y = if (isHorizontal) topLeftY else topLeftY + i
                board[x][y] = SHIP
            }
        } else {
            placeRandomShip(size, type)
        }
    }

    private fun checkIfThereIsRoom(size: Int, isHorizontal: Boolean, topLeftX:Int, topLeftY: Int) : Boolean{

        for (i in 0..size - 1) {
            val x = if (isHorizontal) topLeftX + i else topLeftX
            val y = if (isHorizontal) topLeftY else topLeftY + i
            if (board[x][y] != 0) {
                return false
            }
        }
        return true
    }

    private fun getRandomCoordinate(isHorizontal: Boolean, horizontalBound: Int,  verticalBound: Int):Int{
        return if (isHorizontal) random.nextInt(horizontalBound) else random.nextInt(verticalBound)
    }
}