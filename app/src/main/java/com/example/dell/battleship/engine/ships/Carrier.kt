package com.example.dell.battleship.engine.ships

class Carrier() : Ship(){

    init{
        typeName = "carrier"
        size = 5
        coordinates = arrayOfNulls<Pair<Int,Int>>(size)
    }


}