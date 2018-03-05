package com.example.dell.battleship

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        createBattleField()
    }



    fun createBattleField(){

        var rows  = 8
        var colums = 8



        for(i in 0..rows){
            var row = TableRow(this)
            for(j in 0..colums){
                val item : ImageButton = ImageButton(this)
                item.setBackgroundResource(R.drawable.item_drawable)
                item.setOnClickListener(){
                    item.setBackgroundResource(R.drawable.item_missed)
                }
                row.addView(item)
            }
            board.addView(row)
        }


    }
}
