package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log

class ScriptContent: Asset() {

    companion object {
        private const val TAG = "ScriptContent"
    }

    var contentType = -1
    lateinit var key: AssetPropertyKey
    var numArguments = -1
    val scriptArguments = mutableListOf<ScriptArgument>()
    var unknown = -1

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
        log(TAG, "parseSpecial")
        contentType = reader.readUInt32Le().toInt()
        key = AssetPropertyKey().let { it.parse(reader,mapFile);it }
        numArguments = reader.readUInt32Le().toInt()
        for (i in 0 until numArguments) {
            scriptArguments.add(ScriptArgument().let { it.parse(reader,mapFile);it })
        }
        unknown = reader.readUInt32Le().toInt()
    }
}