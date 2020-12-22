package com.example.dell.battleship.engine.ships

class Carrier() : Ship(){

    init{
        _typeName = "carrier"
        _size = 5
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }


}