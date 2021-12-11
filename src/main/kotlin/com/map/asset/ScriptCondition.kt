package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.util.toBoolean

class ScriptCondition: IItem {

    lateinit var scriptContent: ScriptContent
    var IsInverted = false

    override fun parse(reader: IMapStreamReader, mapFile: MapFile) {
        scriptContent = ScriptContent().let { it.parse(reader.readUInt32Le().toInt(),reader,mapFile);it }
        IsInverted = reader.readByte().toBoolean()
    }
}