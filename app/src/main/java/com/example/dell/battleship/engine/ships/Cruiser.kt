package com.example.dell.battleship.engine.ships

class Cruiser() : Ship(){

    init{
        typeName = "cruiser"
        size = 3
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}