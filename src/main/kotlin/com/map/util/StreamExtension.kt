package com.map

import java.io.BufferedInputStream

fun BufferedInputStream.readByte(): Int {
    val buffer = ByteArray(1)
    read(buffer)
    return buffer[0].toInt() and 0x000000FF
}

fun BufferedInputStream.readUInt32Le(): Long {
    val buffer = ByteArray(4)
    read(buffer)
    return ((buffer[0].toLong() and 0x00000000000000FF)
            or ((buffer[1].toLong() shl 8) and 0x000000000000FF00)
            or ((buffer[2].toLong() shl 16) and 0x0000000000FF0000)
            or ((buffer[3].toLong() shl 24) and 0x00000000FF000000))
}

fun BufferedInputStream.readUInt32(): Long {
    val buffer = ByteArray(4)
    read(buffer)
    return (((buffer[0].toLong() shl 24) and 0x00000000FF000000)
            or ((buffer[1].toLong() shl 16) and 0x0000000000FF0000)
            or ((buffer[2].toLong() shl 8) and 0x000000000000FF00)
            or ((buffer[3].toLong() shl 0) and 0x00000000000000FF))
}

fun BufferedInputStream.readUInt24(): Int {
    val buffer = ByteArray(3)
    read(buffer)
    return (((buffer[0].toInt() shl 16) and 0x00FF0000)
            or ((buffer[1].toInt() shl 8) and 0x0000FF00)
            or ((buffer[2].toInt() shl 0) and 0x000000FF))
}

fun BufferedInputStream.write(outputStream: IMemoryStream, count: Int) {
    outputStream.write(this, count)
}