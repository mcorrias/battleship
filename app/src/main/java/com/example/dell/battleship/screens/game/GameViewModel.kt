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

private const val GAME_OVER_TIME = 0L
private const val GAME_TIME_INTERVAL = 1000L
private const val TOTAL_GAME_TIME = 60000L

class GameViewModel(
        private val timer: GameCountDownTimer = DefaultCountDownTimer(TOTAL_GAME_TIME, GAME_TIME_INTERVAL)
) : ViewModel() {
    val numberOfRows = 10
    val numberOfColumns = 10

    private val _numberOfMoves = MutableLiveData<Int>()
    val numberOfMoves: LiveData<Int>
        get() = _numberOfMoves

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    val currentTimeString: LiveData<String> = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }


    var game : Game

    init {
        timer.attach(this)
        timer.start()
        Log.i("GameViewModel", "GameViewModel created")
        _numberOfMoves.value = 0
        _eventGameFinished.value = false
        game = Game.createGame(numberOfRows,numberOfColumns)
        game.placeRandomShips()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun tick(millisUntilFinished: Long) {
        _currentTime.value = millisUntilFinished / GAME_TIME_INTERVAL
    }

    fun onGameFinished() {
        _currentTime.value = GAME_OVER_TIME
        _eventGameFinished.value = true
    }

    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }

    fun increaseMovesCount() {
        _numberOfMoves.value = numberOfMoves.value?.plus(1)
        Log.i("GameViewModel", "moves: ${numberOfMoves.value}")
    }

    fun saveAttackedCell(attackedCell: Pair<Int, Int>) {
        game.attackedCells.add(attackedCell)
    }

    fun getAttackedCellColor(attackedCell: Pair<Int, Int>, gameContext: Context): Int {

        for (ship in game.ships) {
            if (ship.coordinates.contains(attackedCell)) {
                ship.hit++
                if(ship.isSinked){
                    //reveal neighbours
                }
                
                return ship.getResourceId(gameContext)
            }
        }
        return R.drawable.item_missed
    }

    fun getCellColor(cell: Pair<Int, Int>, gameContext: Context): Int {
        return if (game.attackedCells.contains(cell)) {
            getAttackedCellColor(cell, gameContext)
        } else {
            R.drawable.item_drawable
        }
    }



    interface GameCountDownTimer {
        fun attach(viewModel: GameViewModel)
        fun start()
        fun cancel()
    }

}

open class DefaultCountDownTimer(
        private val millisInFuture :Long ,
        private val countDownInterval : Long) : GameViewModel.GameCountDownTimer {
    private lateinit var timer: CountDownTimer

    override fun attach(viewModel: GameViewModel) {
        timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onFinish() {
                viewModel.onGameFinished()
            }

            override fun onTick(millisUntilFinished: Long) {
                viewModel.tick(millisUntilFinished)

            }

        }
    }

    override fun start() {
        timer.start()
    }

    override fun cancel() {
        timer.cancel()
    }

    fun finish(){
        timer.onFinish()
    }
}

