package com.example.dell.battleship.engine.ships

import com.example.dell.battleship.engine.ships.Ship

class Submarine() : Ship(){

    init{
        typeName = "submarine"
        size = 1
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}