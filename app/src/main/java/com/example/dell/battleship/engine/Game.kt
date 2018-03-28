package com.example.dell.battleship.engine

import android.content.Context
import com.example.dell.battleship.R
import com.example.dell.battleship.R.id.board
import com.example.dell.battleship.engine.Constants.Companion.EMPTY
import com.example.dell.battleship.engine.Constants.Companion.SHIP
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
    val gameContext = context
    var rowsNumber: Int = 0
    var columnsNumber: Int = 0
    var ships: MutableList<Ship> = mutableListOf()


    public fun initGame(rows:Int, columns:Int){
        rowsNumber = rows
        columnsNumber = columns
        placeRandomShips()
    }

    public fun attack(x:Int,y:Int) : Int{

        for( ship in ships ){
            if( ship.coordinates.contains( Pair(x,y) ) ){
                return ship.getResourceId()
            }
        }
        return R.drawable.item_missed
    }

    private fun placeRandomShips(){
        placeRandomShip(5, carrier)
        placeRandomShip(4, battleShip)
        placeRandomShip(3, cruiser)
        placeRandomShip(2, destroyer)
        placeRandomShip(2, destroyer)
        placeRandomShip(1, submarine)
        placeRandomShip(1, submarine)
    }

    private fun placeRandomShip(size: Int , type:String){

        val isHorizontal: Boolean = (random.nextInt() % 2) != 0
        val topLeftX = getRandomCoordinate(isHorizontal,columnsNumber-size,rowsNumber)
        val topLeftY = getRandomCoordinate(isHorizontal,rowsNumber,columnsNumber-size)

        var isClear = checkIfThereIsRoom(size , isHorizontal , topLeftX , topLeftY)

        if (isClear) {
            val newShip = Ship(type, size, isHorizontal, gameContext)
            for (i in 0..(size-1) ) {
                val x = if (isHorizontal) topLeftX + i else topLeftX
                val y = if (isHorizontal) topLeftY else topLeftY + i
                newShip.coordinates[i] = Pair(x,y)
            }
            ships.add(newShip)
        } else {
            placeRandomShip(size, type)
        }
    }

    private fun checkIfThereIsRoom(size: Int, isHorizontal: Boolean, topLeftX:Int, topLeftY: Int) : Boolean{

        for (i in 0..(size - 1)) {
            val x = if (isHorizontal) topLeftX + i else topLeftX
            val y = if (isHorizontal) topLeftY else topLeftY + i

            for( ship in ships){
                if (ship.coordinates.contains(Pair(x, y))  || !checkIfNeighbourHoodIsEmpty(x,y)) {
                    return false
                }
            }

        }
        return true
    }

    private fun checkIfNeighbourHoodIsEmpty(x:Int,y:Int): Boolean{

        for(i in x-1..x+1){
            for(j in y-1..y+1){
                for( ship in ships){
                    if(ship.coordinates.contains(Pair(i,j))){
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun getRandomCoordinate(isHorizontal: Boolean, horizontalBound: Int,  verticalBound: Int):Int{
        return if (isHorizontal) random.nextInt(horizontalBound) else random.nextInt(verticalBound)
    }
}