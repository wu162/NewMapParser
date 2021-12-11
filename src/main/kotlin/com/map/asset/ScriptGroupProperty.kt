package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log
import com.map.util.toBoolean

class ScriptGroupProperty: Asset() {

    companion object {
        private const val TAG = "ScriptGroupProperty"
    }
    lateinit var scriptGroupName: String
    var isActive = false
    var isSubroutine = false
    val scripts = mutableListOf<Script>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
        log(TAG, "parseSpecial")
        scriptGroupName = reader.readUint16AsciiStr()
        isActive = reader.readByte().toBoolean()
        isSubroutine = reader.readByte().toBoolean()
    }
}