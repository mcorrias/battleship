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
import com.example.dell.battleship.engine.ships.*
import java.util.*

class GameViewModel : ViewModel(){
    companion object{
        private const val GAME_OVER_TIME = 0L
        private const val GAME_TIME_INTERVAL = 1000L
        private const val TOTAL_GAME_TIME = 60000L
    }

    val numberOfRows = 10
    val numberOfColumns = 10

    private val _numberOfMoves = MutableLiveData<Int>()
    val numberOfMoves: LiveData<Int>
        get() = _numberOfMoves

    private val _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long>
        get() = _currentTime

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished : LiveData<Boolean>
        get() = _eventGameFinished

    val currentTimeString = Transformations.map(currentTime){time ->
        DateUtils.formatElapsedTime(time)
    }

    private var ships: MutableList<Ship> = mutableListOf()
    private val random: Random = Random()
    private var attackedCells : MutableList<Pair<Int,Int>> = mutableListOf()

    private val timer : CountDownTimer

    init {
        timer = object :CountDownTimer(TOTAL_GAME_TIME, GAME_TIME_INTERVAL){
            override fun onFinish() {
                _currentTime.value = GAME_OVER_TIME
                onGameFinished()
            }

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ GAME_TIME_INTERVAL
            }

        }

        timer.start()
        Log.i("GameViewModel", "GameViewModel created")
        _numberOfMoves.value = 0
        placeRandomShips()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
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

    fun onGameFinished() {
        _eventGameFinished.value = true
    }

    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }

    fun increaseMovesCount(){
        _numberOfMoves.value = numberOfMoves.value?.plus(1)
        Log.i("GameViewModel", "moves: ${numberOfMoves.value}")
    }

    fun saveAttackedCell(attackedCell : Pair<Int, Int>){
        attackedCells.add(attackedCell)
    }

    fun getAttackedCellColor(attackedCell : Pair<Int, Int>, gameContext: Context) : Int{

        for( ship in ships ){
            if( ship.coordinates.contains( attackedCell ) ){
                return ship.getResourceId(gameContext)
            }
        }
        return R.drawable.item_missed
    }

    fun getCellColor(cell : Pair<Int, Int>, gameContext: Context) : Int{
        return if(attackedCells.contains(cell)){
            getAttackedCellColor(cell, gameContext)
        }else{
            R.drawable.item_drawable
        }
    }

    private fun placeRandomShip(ship : Ship){

        val isHorizontal: Boolean = (random.nextInt() % 2) != 0
        val columnsBound = numberOfColumns-ship.size
        val topLeftX = getRandomCoordinate(isHorizontal,columnsBound,numberOfRows)
        val topLeftY = getRandomCoordinate(isHorizontal,numberOfRows,columnsBound)

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