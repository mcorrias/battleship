package com.example.dell.battleship.engine

import android.content.Context
import com.example.dell.battleship.R

/**
 * Created by Dell on 08/03/2018.
 */
class Ship(var type: String, var size: Int, @Transient val context: Context) {

    val isHorizontal: Boolean = false
    val state:Int = 0
    lateinit var coordinates: Array<Pair<Int,Int>>
    lateinit var coordinatesHit: Array<Pair<Int,Int>>
    var hitNumbers: Int = 0

    fun getResourceId(): Int{

        val id: Int = context.resources.getIdentifier(type+"_drawable","drawable",context.applicationInfo.packageName)

        when(id != 0){
            true -> return id
            false->return R.drawable.item_hit
        }
    }

}