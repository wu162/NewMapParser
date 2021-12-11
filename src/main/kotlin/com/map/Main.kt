package com.map

fun main() {
    val stream = uncompressIfNeeded("./files/hard choice(new).map")
//    (stream as MemoryStream).toFile()
    MapFile(MapStreamReader(stream.getAllBytes())).parse()
}
