package com.map.util

import com.map.asset.SequentialScriptTarget
import java.lang.Exception

fun Int.toBoolean(): Boolean {
    return this != 0
}

fun Int.toSequentialScriptTarget(): SequentialScriptTarget {
    return when (this) {
        0 -> {
            SequentialScriptTarget.Team
        }
        1 -> {
            SequentialScriptTarget.Unit
        }
        else -> {
            throw Exception("Unknown SequentialScriptTarget $this")
        }
    }
}