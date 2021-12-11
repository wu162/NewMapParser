package com.map.asset

enum class AssetPropertyType(val value: Int) {
    Boolean(0),
    Integer(1),
    RealNumber(2),
    AsciiString(3),
    UnicodeString(4),
    Unknown(5)
}