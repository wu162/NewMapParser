package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import java.lang.Exception
import kotlin.properties.Delegates

class AssetPropertyKey : IItem {
    lateinit var name: String
        private set
    var propertyNameIndex = -1
        private set
    var propertyType: AssetPropertyType = AssetPropertyType.Unknown

    /**
     * 参考 [AssetPropertyType][AssetPropertyType]
     */
    override fun parse(reader: IMapStreamReader, mapFile: MapFile) {
        propertyType = getType(reader.readByte())
        propertyNameIndex = reader.readUInt24Le()
        name = mapFile.indexNameMap[propertyNameIndex]!!
    }

    fun getType(type: Int):  AssetPropertyType{
        return when (type) {
            0 -> AssetPropertyType.Boolean
            1 -> AssetPropertyType.Integer
            2 -> AssetPropertyType.RealNumber
            3 -> AssetPropertyType.AsciiString
            4 -> AssetPropertyType.UnicodeString
            5 -> AssetPropertyType.Unknown
            else -> throw Exception("invalid AssetPropertyType $type")
        }
    }
}