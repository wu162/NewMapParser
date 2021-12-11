package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile

class ScriptArgument: IItem {
    var argumentType = -1
    var position = Position(0f,0f,0f)

    var intValue:Int = -1
    var floatValue = -1f
    var stringValue = ""

    override fun parse(reader: IMapStreamReader, mapFile: MapFile) {
        argumentType = reader.readUInt32Le().toInt()
        if (argumentType == 16) {
            position = Position(reader.readFloat(),reader.readFloat(),reader.readFloat())
        } else {
            intValue = reader.readUInt32Le().toInt()
            floatValue = reader.readFloat()
            stringValue = reader.readUint16AsciiStr()
        }
    }
}