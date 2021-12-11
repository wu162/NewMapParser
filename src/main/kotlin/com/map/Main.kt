package com.map

fun main() {
    val stream = uncompressIfNeeded("./files/hard choice(new).map")
//    (stream as MemoryStream).toFile()
    getReader(stream.getAllBytes()).use {
        MapFile(it).parse()
    }
}
