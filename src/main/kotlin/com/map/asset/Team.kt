package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile

class Team: IItem {
    lateinit var property: AssetPropertyCollection

    override fun parse(reader: IMapStreamReader, mapFile: MapFile) {
        property = AssetPropertyCollection().let { it.parse(reader, mapFile);it }
    }
}