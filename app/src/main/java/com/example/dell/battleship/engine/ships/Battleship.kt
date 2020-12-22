package com.example.dell.battleship.engine.ships

class Battleship() : Ship() {

    init {
        _typeName = "battleship"
        _size = 4
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}