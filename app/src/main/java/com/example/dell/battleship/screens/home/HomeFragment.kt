package com.example.dell.battleship.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dell.battleship.R
import com.example.dell.battleship.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)

        binding.playButton.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
        }

        return binding.root
    }
}