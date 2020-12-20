package com.example.dell.battleship.engine

import android.content.Context
import com.example.dell.battleship.R
import com.example.dell.battleship.engine.ships.*
import java.util.*

/**
 * Created by Dell on 05/03/2018.
 */

class Game(@Transient val context: Context){


    val random: Random = Random()
    val gameContext = context
    var rowsNumber: Int = 0
    var columnsNumber: Int = 0
    var ships: MutableList<Ship> = mutableListOf()


    fun initGame(rows:Int, columns:Int){
        rowsNumber = rows
        columnsNumber = columns
        placeRandomShips()
    }

    fun attack(x:Int,y:Int) : Int{

        for( ship in ships ){
            if( ship.coordinates.contains( Pair(x,y) ) ){
                return ship.getResourceId(gameContext)
            }
        }
        return R.drawable.item_missed
    }

    private fun placeRandomShips(){
        placeRandomShip(Carrier())
        placeRandomShip(Battleship())
        placeRandomShip(Cruiser())
        placeRandomShip(Destroyer())
        placeRandomShip(Destroyer())
        placeRandomShip(Submarine())
        placeRandomShip(Submarine())
    }

    private fun placeRandomShip(ship : Ship){

        val isHorizontal: Boolean = (random.nextInt() % 2) != 0
        val columnsBound = columnsNumber-ship.size
        val topLeftX = getRandomCoordinate(isHorizontal,columnsBound,rowsNumber)
        val topLeftY = getRandomCoordinate(isHorizontal,rowsNumber,columnsBound)

        var isClear = checkIfThereIsRoom(ship.size , isHorizontal , topLeftX , topLeftY)

        if (isClear) {
            for (i in 0..(ship.size-1) ) {
                val x = if (isHorizontal) topLeftX + i else topLeftX
                val y = if (isHorizontal) topLeftY else topLeftY + i
                ship.coordinates[i] = Pair(x,y)
            }
            ships.add(ship)
        } else {
            placeRandomShip(ship)
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