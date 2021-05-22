package com.example.dell.battleship.screens.game

import android.content.Context
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.dell.battleship.R
import com.example.dell.battleship.engine.Game
import com.example.dell.battleship.engine.ships.*
import java.util.*

class GameViewModel : ViewModel() {
    val numberOfRows = 10
    val numberOfColumns = 10

    private val _numberOfMoves = MutableLiveData<Int>()
    val numberOfMoves: LiveData<Int>
        get() = _numberOfMoves

    private val _startTime = MutableLiveData<Long>()
    val startTime: LiveData<Long>
        get() = _startTime

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    var game : Game

    init {
        Log.i("GameViewModel", "GameViewModel created")
        _numberOfMoves.value = 0
        _eventGameFinished.value = false
        game = Game.createGame(numberOfRows,numberOfColumns)
        game.placeRandomShips()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun setStartTime(time : Long){
        _startTime.value = time
    }

    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }

    private fun increaseMovesCount() {
        _numberOfMoves.value = numberOfMoves.value?.plus(1)
    }

    fun guessCellType(cellPosition : Pair<Int, Int>) : Int {
        increaseMovesCount()
        var resourceId :Int

        when(game.guessedCells[cellPosition]){
            Game.CellStatus.UNDEFINED ->{
                game.guessedCells[cellPosition] = Game.CellStatus.SHIP
                resourceId = R.drawable.destroyer_drawable
            }
            Game.CellStatus.SHIP ->{
                game.guessedCells[cellPosition] = Game.CellStatus.WATER
                resourceId = R.drawable.item_missed
            }
            Game.CellStatus.WATER ->{
                game.guessedCells[cellPosition] = Game.CellStatus.UNDEFINED
                resourceId = R.drawable.item_drawable
            }
            else ->{
                game.guessedCells[cellPosition] = Game.CellStatus.SHIP
                resourceId = R.drawable.destroyer_drawable
            }
        }
        return resourceId
    }

    fun getCellType(cellPosition: Pair<Int, Int>) :Int{
        var resourceId :Int

        when(game.guessedCells[cellPosition]){
            Game.CellStatus.UNDEFINED ->{
                resourceId = R.drawable.item_drawable
            }
            Game.CellStatus.SHIP ->{
                resourceId = R.drawable.destroyer_drawable
            }
            Game.CellStatus.WATER ->{
                resourceId = R.drawable.item_missed
            }
            else ->{
                resourceId = R.drawable.item_drawable
            }
        }
        return resourceId
    }

//    fun saveAttackedCell(attackedCell: Pair<Int, Int>) {
//        game.attackedCells.add(attackedCell)
//    }

//    fun getAttackedCellColor(attackedCell: Pair<Int, Int>, gameContext: Context): Int {
//
//        for (ship in game.ships) {
//            if (ship.coordinates.contains(attackedCell)) {
//                ship.hit++
//                if(ship.isSinked){
//                    //reveal neighbours
//                }
//
//                return ship.getResourceId(gameContext)
//            }
//        }
//        return R.drawable.item_missed
//    }

//    fun getCellColor(cell: Pair<Int, Int>, gameContext: Context): Int {
//        return if (game.attackedCells.contains(cell)) {
//            getAttackedCellColor(cell, gameContext)
//        } else {
//            R.drawable.item_drawable
//        }
//    }

}