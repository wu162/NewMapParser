package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile

class ScriptActionTrue: IItem {
    lateinit var scriptContent: ScriptContent

    override fun parse(index: Int, reader: IMapStreamReader, mapFile: MapFile) {
        scriptContent = ScriptContent().let { it.parse(index,reader,mapFile);it }
    }
}