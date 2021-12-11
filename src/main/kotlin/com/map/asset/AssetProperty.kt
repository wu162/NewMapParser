package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile

class AssetProperty: IItem {
    lateinit var key: AssetPropertyKey
    lateinit var value: Any

    override fun parse(reader: IMapStreamReader, mapFile: MapFile) {
        key = AssetPropertyKey().let { it.parse(reader, mapFile);it }
        when (key.propertyType) {
            AssetPropertyType.Boolean -> {
                value = reader.readByte()
            }
            AssetPropertyType.Integer -> {
                value = reader.readUInt32Le()
            }
            AssetPropertyType.RealNumber -> {
                value = reader.readUInt32Le()
            }
            AssetPropertyType.AsciiString -> {
                value = reader.readUint16AsciiStr()
            }
            AssetPropertyType.UnicodeString -> {
                value = reader.readUint16UnicodeStr()
            }
            AssetPropertyType.Unknown -> {
                value = reader.readUint16AsciiStr()
            }
        }
    }
}