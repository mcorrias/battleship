package com.example.dell.battleship

import android.content.Context
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


    public fun initGame(rows:Int, columns:Int){
        rowsNumber = rows
        columnsNumber = columns
        placeRandomShips()
    }

    private fun placeRandomShips(){
        placeRandomShip(5,  carrier)
        placeRandomShip(4,  battleShip)
        placeRandomShip(3,  cruiser)
        placeRandomShip(2,  destroyer)
        placeRandomShip(2,  destroyer)
        placeRandomShip(1,  destroyer)
        placeRandomShip(1,  destroyer)
    }

    private fun placeRandomShip(size: Int, type:String){

        val isHorizontal: Boolean = (random.nextInt() % 2) != 0
        val topLeftX = getRandomCoordinate(isHorizontal,columnsNumber-size,rowsNumber)
        val topLeftY = getRandomCoordinate(isHorizontal,rowsNumber,columnsNumber-size)

    }

    private fun getRandomCoordinate(isHorizontal: Boolean, horizontalBound: Int,  verticalBound: Int):Int{
        return if (isHorizontal) random.nextInt(horizontalBound) else random.nextInt(verticalBound)
    }
}