package com.example.dell.battleship.screens.home

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class HomeViewModel {
    private val _currentTime = MutableLiveData<Long>()

    val currentTime: LiveData<Long>
        get() = _currentTime


    val currentTimeString = Transformations.map(_currentTime) {
        DateUtils.formatElapsedTime(it)
    }

    fun setNewTime(newValue: Long) {
        _currentTime.value = newValue
    }
}