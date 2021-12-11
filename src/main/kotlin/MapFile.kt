import util.IMapStreamReader
import util.log

private const val TAG = "MapFile"

class MapFile(private val reader: IMapStreamReader) {

    fun parse() {
        val flag = reader.readUInt32Le()
        assert(flag == 1884121923L)
        val assetCount = reader.readUInt32Le()
        val assetDescList = mutableListOf<AssetDescItem>()
        for (i in 0 until assetCount) {
            val length = reader.readByte()
            assetDescList.add(
                AssetDescItem(
                    length,
                    String(reader.readBytes(length)),
                    reader.readUInt32Le()
                )
            )
        }
        log(TAG, getDescStr(assetDescList))
    }

    private fun getDescStr(assetDescList: MutableList<AssetDescItem>): String {
        var res = ""
        assetDescList.forEach {
            res += it.content + "\n"
        }
        return res
    }
}