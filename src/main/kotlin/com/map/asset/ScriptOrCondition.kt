package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log

class ScriptOrCondition: Asset() {

    companion object {
        private const val TAG = "ScriptOrCondition"
    }

    val scriptConditions = mutableListOf<ScriptCondition>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
        log(TAG, "parseSpecial")
        while (reader.hasMore()) {
            scriptConditions.add(ScriptCondition().let { it.parse(reader,mapFile);it })
        }
    }
}