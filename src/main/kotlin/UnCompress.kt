import util.*
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.lang.Exception

fun uncompressIfNeeded(path: String): IMemoryStream {
    FileInputStream(path).use { fileStream ->
        BufferedInputStream(fileStream).use { bufferStream ->
            val flag = bufferStream.readUInt32Le()
            when (flag) {
                5390661L -> {
                    var uncompressedSize = bufferStream.readUInt32Le()
                    val headByte1 = bufferStream.readByte()
                    assert(headByte1 and 0b00111110 == 0b00010000)
                    var largeFilesFlag = (headByte1 and 0b10000000) != 0
                    var compressedSizePresent = (headByte1 and 0b00000001) != 0
                    val headByte2 = bufferStream.readByte()
                    assert(headByte1.toInt() == 0b11111011)
                    if (compressedSizePresent) {
                        if (largeFilesFlag) {
                            val compressedLLength = bufferStream.readUInt32()
                        } else {
                            val compressedLLength = bufferStream.readUInt24()
                        }
                    }
                    if (largeFilesFlag) {
                        uncompressedSize = bufferStream.readUInt32()
                    } else {
                        //通常是这条分支
                        uncompressedSize = bufferStream.readUInt24().toLong()
                    }
                    return uncompress(bufferStream, uncompressedSize)
                }
                1884121923L -> {
                }
            }
        }
    }
    throw Exception("uncompress exception")
}

fun uncompress(bufferStream: BufferedInputStream, uncompressedSize: Long): IMemoryStream {
    log("UnCompress","uncompressedSize: $uncompressedSize")
    val stream = MemoryStream(uncompressedSize.toInt())
    stream.use { memoryStream ->
        while (true) {
            val byte = bufferStream.readByte()
            if (byte and 0x80 == 0) {
                Execute2ByteCommand(bufferStream, memoryStream, byte)
            } else if (byte and 0x40 == 0) {
                Execute3ByteCommand(bufferStream, memoryStream, byte)
            } else if (byte and 0x20 == 0) {
                Execute4ByteCommand(bufferStream, memoryStream, byte)
            } else {
                val count = ((byte and 0x1F) + 1) shl 2
                if (count <= 112) {
                    Execute1ByteCommand(bufferStream, memoryStream, byte)
                } else {
                    ExecuteStopCommand(bufferStream, memoryStream, byte)
                    break
                }
            }
//            log("UnCompress","pos: ${memoryStream.getCurrentPos()}")
//            if (memoryStream.getCurrentPos() > 5900) {
//                break
//            }
        }
    }

    log("UnCompress", "pos: ${stream.getCurrentPos()}")
    return stream
}

fun ExecuteStopCommand(bufferStream: BufferedInputStream, memoryStream: MemoryStream, byte1: Int) {
    bufferStream.write(memoryStream, byte1 and 0x03)
}

fun Execute1ByteCommand(bufferStream: BufferedInputStream, memoryStream: MemoryStream, byte1: Int) {
//    log("Execute1ByteCommand"," ${((byte1 and 0x1F) + 1) shl 2}")
    bufferStream.write(memoryStream,((byte1 and 0x1F) + 1) shl 2)
}

fun Execute4ByteCommand(bufferStream: BufferedInputStream, memoryStream: MemoryStream, byte1: Int) {
    val byte2 = bufferStream.readByte()
    val byte3 = bufferStream.readByte()
    val byte4 = bufferStream.readByte()
    bufferStream.write(memoryStream,  byte1 and 0x03)

    val referencedDataLength = (((byte1 and 0x0C) shr 2) shl 8) + byte4 + 5
    val referencedDataDistance = (((byte1 and 0x10) shr 4) shl 16) + (byte2 shl 8) + byte3 + 1

//    log("Execute4ByteCommand"," ${byte1 and 0x03}  |  $referencedDataLength  | $referencedDataDistance")
    memoryStream.selfCopy(referencedDataDistance, referencedDataLength)

}

fun Execute3ByteCommand(bufferStream: BufferedInputStream, memoryStream: MemoryStream, byte1: Int) {
    val byte2 = bufferStream.readByte()
    val byte3 = bufferStream.readByte()
    bufferStream.write(memoryStream, byte2 shr 6)

    val referencedDataLength = (byte1 and 0x3F) + 4
    val referencedDataDistance = ((byte2 and 0x3F) shl 8) + byte3 + 1

//    log("Execute3ByteCommand"," ${byte2 shr 6} | $referencedDataLength | $referencedDataDistance")
    memoryStream.selfCopy( referencedDataDistance, referencedDataLength)
}

fun Execute2ByteCommand(bufferStream: BufferedInputStream, memoryStream: MemoryStream, byte1: Int) {
    val byte2 = bufferStream.readByte()
    bufferStream.write(memoryStream, byte1 and 0x03)

    val referencedDataLength = ((byte1 and 0x1C) shr 2) + 3
    val referencedDataDistance = ((byte1 and 0x60) shl 3) + byte2 + 1

//    log("Execute2ByteCommand","$byte1 | $byte2 | ${byte1 and 0x03} | $referencedDataLength | $referencedDataDistance")

    memoryStream.selfCopy(referencedDataDistance, referencedDataLength)


}
