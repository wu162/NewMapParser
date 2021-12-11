package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile

class AssetPropertyCollection: IItem {
    val propertyList = mutableListOf<AssetProperty>()

    override fun parse(reader: IMapStreamReader, mapFile: MapFile) {
        val numsProperties = reader.readUInt16Le()
        for (i in 0 until numsProperties) {
            propertyList.add(AssetProperty().let { it.parse(reader, mapFile);it })
        }
    }

}