package com.map

import com.map.asset.Asset
import com.map.asset.AssetDescItem
import com.map.asset.PlayerScriptsList
import com.map.asset.Teams
import java.lang.Exception

private const val TAG = "com.map.MapFile"

class MapFile(private val reader: IMapStreamReader) {

    val indexNameMap = mutableMapOf<Int, String>()
    private val assets  = mutableListOf<Asset>()

    fun parse() {
        val flag = reader.readUInt32Le()
        assert(flag == 1884121923L)
        val assetCount = reader.readUInt32Le()
        val assetDescList = mutableListOf<AssetDescItem>()
        for (i in 0 until assetCount) {
            val length = reader.readByte()
            val assetDescItem = AssetDescItem(
                String(reader.readBytes(length)),
                reader.readUInt32Le().toInt()
            )
            assetDescList.add(assetDescItem)
            indexNameMap[assetDescItem.index.toInt()] = assetDescItem.name
        }
        log(TAG, getDescStr(assetDescList))
        parseAsset()
        log(TAG, "assetDescList size: ${assetDescList.size} | assets size: ${assets.size}")
    }

    private fun parseAsset() {
        while (reader.hasMore()) {
            val index = reader.readUInt32Le().toInt()
            if (indexNameMap.containsKey(index)) {
                val name = indexNameMap[index]!!
                when (name) {
                    "Teams" -> {
                        assets.add(Teams().let { it.parse(index, reader, this);it })
                    }
                    "PlayerScriptsList" -> {
                        assets.add(PlayerScriptsList().let { it.parse(index, reader, this);it })
                    }
                    else -> {
                        assets.add(Asset().let { it.parse(index, reader, this);it })
                    }
                }
            } else {
                throw Exception("invalid asset index")
            }
        }
    }

    private fun getDescStr(assetDescList: MutableList<AssetDescItem>): String {
        var res = ""
        assetDescList.forEach {
            res += it.name + "\n"
        }
        return res
    }
}