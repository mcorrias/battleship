package com.example.dell.battleship.engine.ships

class Destroyer() : Ship(){

    init{
        _typeName = "destroyer"
       _size = 2
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }

}