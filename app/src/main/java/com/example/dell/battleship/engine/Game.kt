package com.example.dell.battleship.engine

import com.example.dell.battleship.engine.ships.*
import java.util.*

class Game private constructor(private val rows: Int, private val colums: Int){

    var ships: MutableList<Ship> = mutableListOf()
    private val random: Random = Random()

    enum class CellStatus(val value : String){
        SHIP("ship"),
        WATER("water"),
        UNDEFINED("undefined"), }

    //var attackedCells: MutableList<Pair<Int, Int>> = mutableListOf()
    var guessedCells: MutableMap<Pair<Int,Int>, CellStatus> = mutableMapOf()
    var shipCellsInColumn = IntArray(11)
    var shipCellsInRow = IntArray(11)

    fun placeRandomShips() {
        placeRandomShip(Carrier())
        placeRandomShip(Battleship())
        placeRandomShip(Cruiser())
        placeRandomShip(Cruiser())
        placeRandomShip(Destroyer())
        placeRandomShip(Destroyer())
        placeRandomShip(Destroyer())
        placeRandomShip(Submarine())
        placeRandomShip(Submarine())
        placeRandomShip(Submarine())
        placeRandomShip(Submarine())
    }

    private fun placeRandomShip(ship: Ship) {

        val isHorizontal: Boolean = (random.nextInt() % 2) != 0
        val columnsBound = colums - ship.size
        val topLeftX = getRandomCoordinate(isHorizontal, columnsBound, rows)
        val topLeftY = getRandomCoordinate(isHorizontal, rows, columnsBound)

        var isClear = checkIfThereIsRoom(ship.size, isHorizontal, topLeftX, topLeftY)

        if (isClear) {
            for (i in 0  until ship.size) {
                val x = if (isHorizontal) topLeftX + i else topLeftX
                val y = if (isHorizontal) topLeftY else topLeftY + i
                shipCellsInColumn[y]++
                shipCellsInRow[x]++
                ship.coordinates[i] = Pair(x, y)
            }
            ships.add(ship)
        } else {
            placeRandomShip(ship)
        }
    }

    private fun checkIfThereIsRoom(size: Int, isHorizontal: Boolean, topLeftX: Int, topLeftY: Int): Boolean {

        for (i in 0 until size) {
            val x = if (isHorizontal) topLeftX + i else topLeftX
            val y = if (isHorizontal) topLeftY else topLeftY + i

            for (ship in ships) {
                if (ship.coordinates.contains(Pair(x, y)) || !checkIfNeighbourHoodIsEmpty(x, y)) {
                    return false
                }
            }

        }
        return true
    }

    private fun checkIfNeighbourHoodIsEmpty(x: Int, y: Int): Boolean {

        for (i in x - 1..x + 1) {
            for (j in y - 1..y + 1) {
                for (ship in ships) {
                    if (ship.coordinates.contains(Pair(i, j))) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun getRandomCoordinate(isHorizontal: Boolean, horizontalBound: Int, verticalBound: Int): Int {

        return if (isHorizontal) (1..horizontalBound).random() else (1..verticalBound).random()
    }

    companion object{
        fun createGame(rows : Int, colums:Int) : Game{
            return Game(rows,colums)
        }
    }

}