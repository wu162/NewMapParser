package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log

class ScriptsList: Asset() {

    companion object {
        private const val TAG = "ScriptsList"
    }

    val scripts = mutableListOf<Script>()
    val scriptGroups = mutableListOf<ScriptGroup>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
        log(TAG, "parseSpecial")
        while (reader.hasMore()) {
            val index = reader.readUInt32Le().toInt()
            if (mapFile.indexNameMap.containsKey(index)) {
                val name = mapFile.indexNameMap[index]!!
                when (name) {
                    "Script" -> {
                        scripts.add(Script().let { it.parse(index, reader, mapFile);it })
                    }
                    "ScriptGroup" -> {
                        scriptGroups.add(ScriptGroup().let { it.parse(index, reader, mapFile);it })
                    }
                    else -> {
                        throw Exception("invalid asset name $name")
                    }
                }
            } else {
                throw Exception("invalid asset index $index")
            }
        }
    }
}