package com.example.dell.battleship.engine.ships

class Battleship() : Ship() {

    init {
        typeName = "battleship"
        size = 4
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}