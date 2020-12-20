package com.example.dell.battleship

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableRow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dell.battleship.databinding.FragmentGameBinding
import com.example.dell.battleship.engine.Game

class GameFragment : Fragment() {

    lateinit var newGame: Game
    lateinit var binding : FragmentGameBinding
    private val rows = 10
    private val columns = 10
    private var movesCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        newGame = Game(requireContext())

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        )

        newGame.initGame(rows, columns)
        createBattleField()

        return binding.root
    }

    private fun createBattleField() {

        for (i in 0..rows - 1) {
            var row = TableRow(requireContext())
            for (j in 0..columns - 1) {
                val item: ImageButton = ImageButton(requireContext())
                item.setBackgroundResource(R.drawable.item_drawable)
                item.setOnClickListener() {
                    val cellPosition = Pair(i,j)
                    attack(cellPosition, item)
                }
                row.addView(item)
            }
            binding.board.addView(row)
        }
    }

    private fun attack(cellPosition : Pair<Int,Int>, item: ImageButton) {
       changeAttackedCellColor(cellPosition,item)
       movesCount++
       binding.movesCountText.text = movesCount.toString()
    }

    private fun changeAttackedCellColor(cellPosition : Pair<Int,Int>, item: ImageButton){
        val resource = newGame.attack(cellPosition)
        item.setBackgroundResource(resource)
    }
}