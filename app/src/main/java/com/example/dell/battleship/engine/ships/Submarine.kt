package com.example.dell.battleship.engine.ships

import com.example.dell.battleship.engine.ships.Ship

class Submarine() : Ship(){

    init{
        _typeName = "submarine"
        _size = 1
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}