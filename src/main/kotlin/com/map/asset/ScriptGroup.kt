package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log
import com.map.util.toBoolean

class ScriptGroup: Asset() {

    companion object {
        private const val TAG = "ScriptGroup"
    }

    lateinit var scriptGroupName: String
    var isActive = false
    var isSubroutine = false
    val scripts = mutableListOf<Script>()
    val scriptGroupProperties = mutableListOf<ScriptGroupProperty>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
//        log(TAG, "parseSpecial")
//        scriptGroupName = reader.readUint16AsciiStr()
//        isActive = reader.readByte().toBoolean()
//        isSubroutine = reader.readByte().toBoolean()
//        while (reader.hasMore()) {
//            val index = reader.readUInt32Le().toInt()
//            if (mapFile.indexNameMap.containsKey(index)) {
//                val name = mapFile.indexNameMap[index]!!
//                when (name) {
//                    "Script" -> {
//                        scripts.add(Script().let { it.parse(index, reader, mapFile);it })
//                    }
//                    "ScriptGroupProperty" -> {
//                        scriptGroupProperties.add(ScriptGroupProperty().let { it.parse(index, reader, mapFile);it })
//                    }
//                    else -> {
//                        throw Exception("invalid asset name $name")
//                    }
//                }
//            } else {
//                throw Exception("invalid asset index $index")
//            }
//        }
    }
}