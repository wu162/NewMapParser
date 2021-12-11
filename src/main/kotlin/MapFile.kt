import util.IMapStreamReader
import util.log
import java.lang.Exception

private const val TAG = "MapFile"

class MapFile(private val reader: IMapStreamReader) {

    private val indexNameMap = mutableMapOf<Int, String>()
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
        while (reader.hasMore()) {
            val index = reader.readUInt32Le().toInt()
            val version = reader.readUInt16Le()
            val size = reader.readUInt32Le()
            val data = reader.readBytes(size.toInt())
            if (indexNameMap.containsKey(index)) {
                assets.add(Asset(index, indexNameMap[index]!!, data))
            } else {
                throw Exception("invalid asset index")
            }
        }
        log(TAG, "assetDescList size: ${assetDescList.size} | assets size: ${assets.size}")
    }

    private fun getDescStr(assetDescList: MutableList<AssetDescItem>): String {
        var res = ""
        assetDescList.forEach {
            res += it.name + "\n"
        }
        return res
    }

}