package com.map

import java.io.ByteArrayInputStream
import java.io.Closeable
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

interface IMapStreamReader: Closeable {

    fun readByte(): Int

    fun readUInt32Le(): Long

    fun readUInt16Le(): Int

    fun readBytes(count: Int): ByteArray

    fun hasMore(): Boolean

    fun readUInt24Le(): Int

    fun readUint16AsciiStr(): String

    fun readUint16UnicodeStr(): String

    fun leftBytes(): Int

    fun readFloat(): Float
}

class MapStreamReader(bytes: ByteArray): IMapStreamReader {

    private val stream = ByteArrayInputStream(bytes)

    companion object {
        private const val TAG = "MapStreamReader"
    }

    override fun readByte(): Int {
        return stream.read()
    }

    override fun readUInt32Le(): Long {
        return stream.read().toLong() or (stream.read().toLong() shl 8) or (stream.read().toLong() shl 16) or (stream.read().toLong() shl 24)
    }

    override fun readUInt16Le(): Int {
        return stream.read() or (stream.read() shl 8)
    }

    override fun readBytes(count: Int): ByteArray {
//        log(TAG, "readBytes: $count | left: ${stream.available()}")
        var buf = ByteArray(count)
        stream.read(buf)
        return buf
    }

    override fun hasMore(): Boolean {
        return stream.available() > 0
    }

    override fun readUInt24Le(): Int {
        return stream.read() or (stream.read() shl 8) or (stream.read() shl 16)
    }

    override fun readUint16AsciiStr(): String {
        val length = readUInt16Le()
        return String(readBytes(length), Charsets.US_ASCII)
    }

    override fun readUint16UnicodeStr(): String {
        val length = readUInt16Le()
        return String(readBytes(length), Charsets.UTF_16LE)
    }

    override fun leftBytes(): Int {
        return stream.available()
    }

    override fun readFloat(): Float {
        //TODO
        return 0f
    }

    override fun close() {
        stream.close()
    }

}

class MapArrayStreamReader(val buffer: ByteArray): IMapStreamReader {

    private var pos = 0

    companion object {
        private const val TAG = "MapStreamReader"
    }

    override fun readByte(): Int {
        return buffer[pos++].toInt() and 0x000000FF
    }

    override fun readUInt32Le(): Long {
        return readByte().toLong() or (readByte().toLong() shl 8) or (readByte().toLong() shl 16) or (readByte().toLong() shl 24)
    }

    override fun readUInt16Le(): Int {
        return readByte() or (readByte() shl 8)
    }

    override fun readBytes(count: Int): ByteArray {
//        log(TAG, "readBytes: $count | left: ${stream.available()}")
        val res = buffer.copyOfRange(pos, pos + count)
        pos += count
        return res
    }

    override fun hasMore(): Boolean {
        return pos < buffer.size
    }

    override fun readUInt24Le(): Int {
        return readByte() or (readByte() shl 8) or (readByte() shl 16)
    }

    override fun readUint16AsciiStr(): String {
        val length = readUInt16Le()
        return String(readBytes(length), Charsets.US_ASCII)
    }

    override fun readUint16UnicodeStr(): String {
        val length = readUInt16Le()
        return String(readBytes(length), Charsets.UTF_16LE)
    }

    override fun leftBytes(): Int {
        return buffer.size - pos
    }

    override fun readFloat(): Float {
        return java.lang.Float.intBitsToFloat(readUInt32Le().toInt())
    }

    override fun close() {

    }

}

fun getReader(bytes: ByteArray): IMapStreamReader {
    return MapArrayStreamReader(bytes)
}