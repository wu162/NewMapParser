package com.map.asset

import com.map.*
import java.lang.Exception

open class Asset() {
    var index: Int = -1
    lateinit var name: String
    protected lateinit var bytes: ByteArray

    companion object {
        private const val TAG = "Asset"
    }

    fun parse(index: Int, reader: IMapStreamReader, mapFile: MapFile) {
        this.index = index
        val version = reader.readUInt16Le()
        val size = reader.readUInt32Le()
        bytes = reader.readBytes(size.toInt())
        if (mapFile.indexNameMap.containsKey(index)) {
            name = mapFile.indexNameMap[index]!!
        } else {
            throw Exception("invalid asset index")
        }
        log(TAG, "size: $size | left: ${reader.leftBytes()}")
        getReader(bytes).use {
            parseSpecial(it, mapFile)
        }
    }

    open fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {

    }
}