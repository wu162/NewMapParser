package util

interface MapStreamReader {

    fun readByte(): Byte

    fun readUInt32Le(): Long

    fun readUInt16Le(): Int

    fun readBytes(): ByteArray
}