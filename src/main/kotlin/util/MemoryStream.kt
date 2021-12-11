package util

import java.io.*
import java.lang.Exception

interface IMemoryStream : Closeable {
    fun write(inputStream: InputStream, count: Int)

    fun reset()

    fun seek(pos: Int)

    fun getCurrentPos(): Int

    fun size(): Int

    fun selfCopy(distance: Int, length: Int)
}

open class MemoryStream(private val size: Int) : IMemoryStream {

    private val buf = ByteArray(size){2}
    protected var pos = 0

    override fun write(inputStream: InputStream, count: Int) {
        val buffer = ByteArray(count)
        val readCount = inputStream.read(buffer)
        if (readCount < 0) {
            throw Exception("no data read")
        }
        for (i in 0 until count) {
            buf[pos + i] = buffer[i]
        }
        pos += readCount
        if (readCount < count) {
            log("MemoryStream", "write: only write $readCount bytes")
        }
    }

    override fun reset() {
        pos = 0
    }

    override fun seek(position: Int) {

    }

    override fun getCurrentPos(): Int {
        return pos
    }

    override fun size(): Int {
        return size
    }

    override fun selfCopy(distance: Int, length: Int) {
        val oldPos = getCurrentPos()
        if(distance < 0) {
            log("MemoryStream","selfCopy | distance: $distance")
        }
        assert(distance < 0 && oldPos - distance >= 0 && oldPos + length < size && oldPos - distance + length <= oldPos)
        try {
            var left = length
            val prevPos = pos
            val rightEnd = if (getCurrentPos() - distance + length > pos) pos else getCurrentPos() - distance + length
            val range = buf.copyOfRange(getCurrentPos() - distance, rightEnd)
            for (i in 0 until range.size) {
                buf[pos + i] = range[i]
            }

            val copyCount = (rightEnd - (getCurrentPos() - distance))
            left -= copyCount

            var oldPos1 = pos
            pos += copyCount

            while (left > 0) {
                val rightEnd1 = if (oldPos1 + left > pos) pos else oldPos1 + left
                val range1 = buf.copyOfRange(oldPos1, rightEnd1)
                for (j in 0 until range1.size) {
                    buf[pos + j] = range1[j]
                }


                val copyCount1 = range1.size
                left -= copyCount1
                oldPos1 = pos
                pos += copyCount1
            }
            assert(pos - prevPos == length)
//            System.arraycopy(buf, getCurrentPos() - distance, buf, getCurrentPos(), length)
//            val count = rightEnd - (getCurrentPos() - distance)
//            pos += count
        } catch (e: Exception) {
            log("MemoryStream","oldPos: $oldPos | distance: $distance | length: $length")
            e.printStackTrace()
            System.exit(-1)
        }
    }

    override fun close() {

    }

    fun toFile() {
        if (File("./files/map_un.map").exists()) {
            File("./files/map_un.map").delete()
        }
        FileOutputStream("./files/map_un.map").use { outputStream ->
            outputStream.write(buf,0,size)
        }
    }

}