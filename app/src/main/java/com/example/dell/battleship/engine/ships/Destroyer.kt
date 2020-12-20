package com.example.dell.battleship.engine.ships

class Destroyer() : Ship(){

    init{
        typeName = "destroyer"
        size = 2
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}