package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile

interface IItem {
    fun parse(reader: IMapStreamReader, mapFile: MapFile) {}
    fun parse(index: Int, reader: IMapStreamReader, mapFile: MapFile) {}
}