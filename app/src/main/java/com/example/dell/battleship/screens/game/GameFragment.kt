package com.example.dell.battleship.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dell.battleship.R
import com.example.dell.battleship.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        )
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        gameViewModel.eventGameFinished.observe(viewLifecycleOwner, Observer { hasFinished->
            if(hasFinished){
                gameFinished()
            }
        })

        createBattleField()

        return binding.root
    }

    private fun createBattleField() {
        val rows = gameViewModel.numberOfRows
        val columns = gameViewModel.numberOfColumns

        for (i in 0..rows) {
            var row = TableRow(requireContext())
            for (j in 0..columns) {
                if((i == 0 || j == 0) && i != j){
                    val hintView = TextView(requireContext())
                    if(i == 0){
                        hintView.text = gameViewModel.game.shipCellsInColumn[j].toString()
                    }
                    if(j == 0){
                        hintView.text = gameViewModel.game.shipCellsInRow[i].toString()
                    }
                    row.addView(hintView)

                }
                else if(i != 0 && j != 0) {
                    val cellPosition = Pair(i, j)
                    val item = ImageButton(requireContext())
                    item.tag = "cell$i-$j"
                    item.setBackgroundResource(gameViewModel.getCellColor(cellPosition, requireContext()))
                    item.setOnClickListener {
                        attack(cellPosition, item)
                    }
                    row.addView(item)
                }else if(i==0 && j==0){
                    val item = ImageButton(requireContext())
                    item.setBackgroundResource(R.drawable.item_missed)
                    row.addView(item)
                }

            }
            binding.board.addView(row)
        }
    }

    private fun attack(cellPosition: Pair<Int, Int>, item: ImageButton) {
        gameViewModel.increaseMovesCount()
        gameViewModel.saveAttackedCell(cellPosition)
        changeAttackedCellColor(cellPosition, item)
    }

    private fun changeAttackedCellColor(cellPosition: Pair<Int, Int>, item: ImageButton) {
        val resource = gameViewModel.getAttackedCellColor(cellPosition,requireContext())
        item.setBackgroundResource(resource)
    }

    private fun gameFinished(){
        val action = GameFragmentDirections.actionGameFragmentToHomeFragment()
        findNavController().navigate(action)
        gameViewModel.onGameFinishedComplete()
    }
}