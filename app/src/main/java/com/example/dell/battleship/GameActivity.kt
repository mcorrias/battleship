package com.example.dell.battleship

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    val newGame :Game = Game(this)
    val rows  = 10
    val colums = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        newGame.initGame(rows,colums)
        createBattleField()
    }

    private fun createBattleField(){

        for(i in 0..rows - 1 ){
            var row = TableRow(this)
            for(j in 0..colums - 1){
                val item : ImageButton = ImageButton(this)
                item.setBackgroundResource(R.drawable.item_drawable)
                item.setOnClickListener(){
                    attack(i, j, item)
                }
                row.addView(item)
            }
            board.addView(row)
        }
    }

    private fun attack(x:Int , y:Int, item: ImageButton){
        var hasHit = newGame.attack(x,y)
        if(hasHit){
            item.setBackgroundResource(R.drawable.item_hit)
        }
        else {
            item.setBackgroundResource(R.drawable.item_missed)
        }
    }
}
