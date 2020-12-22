package com.example.dell.battleship.engine.ships

class Cruiser() : Ship(){

    init{
        _typeName = "cruiser"
        _size = 3
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}