package com.example.dell.battleship.activities


import android.os.Bundle
import android.widget.ImageButton
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.example.dell.battleship.R
import com.example.dell.battleship.engine.Game
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    val newGame: Game = Game(this)
    val rows = 10
    val colums = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        newGame.initGame(rows, colums)
        createBattleField()
    }

    private fun createBattleField() {

        for (i in 0..rows - 1) {
            var row = TableRow(this)
            for (j in 0..colums - 1) {
                val item: ImageButton = ImageButton(this)
                item.setBackgroundResource(R.drawable.item_drawable)
                item.setOnClickListener() {
                    attack(i, j, item)
                }
                row.addView(item)
            }
            board.addView(row)
        }
    }

    private fun attack(x: Int, y: Int, item: ImageButton) {
        var resource = newGame.attack(x, y)

        item.setBackgroundResource(resource)


    }
}
