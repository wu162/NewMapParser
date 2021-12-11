package com.map

import java.io.ByteArrayInputStream

interface IMapStreamReader {

    fun readByte(): Int

    fun readUInt32Le(): Long

    fun readUInt16Le(): Int

    fun readBytes(count: Int): ByteArray

    fun hasMore(): Boolean
}

class MapStreamReader(bytes: ByteArray): IMapStreamReader {

    private val stream = ByteArrayInputStream(bytes)

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
        var buf = ByteArray(count)
        stream.read(buf)
        return buf
    }

    override fun hasMore(): Boolean {
        return stream.available() > 0
    }

}