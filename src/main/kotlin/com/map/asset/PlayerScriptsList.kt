package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log

class PlayerScriptsList: Asset() {

    companion object {
        private const val TAG = "PlayerScriptsList"
    }

    val scriptsLists = mutableListOf<ScriptsList>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
        log(TAG, "parseSpecial")
        while (reader.hasMore()) {
            scriptsLists.add(ScriptsList().let { it.parse(reader.readUInt32Le().toInt(),reader, mapFile);it })
        }
    }
}