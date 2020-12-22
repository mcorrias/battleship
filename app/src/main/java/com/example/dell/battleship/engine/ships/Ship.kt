package com.example.dell.battleship.engine.ships

import android.content.Context
import com.example.dell.battleship.R

/**
 * Created by Dell on 08/03/2018.
 */
open class Ship() {

    protected lateinit var _typeName : String
    val typeName : String
        get() = _typeName

    protected  var _size  = 0
    val size :Int
        get() = _size

    lateinit var coordinates: Array<Pair<Int,Int>?>

    fun getResourceId(context: Context): Int
    {

        val id: Int = context.resources.getIdentifier(typeName+"_drawable","drawable",context.applicationInfo.packageName)

        when(id != 0){
            true -> return id
            false->return R.drawable.item_hit
        }
    }


}