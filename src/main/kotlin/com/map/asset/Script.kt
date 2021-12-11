package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log
import com.map.util.toBoolean
import com.map.util.toSequentialScriptTarget

class Script: Asset() {

    companion object {
        private const val TAG = "Script"
    }

    lateinit var scriptName: String
    lateinit var scriptConmment: String
    lateinit var scriptCondiComment: String
    lateinit var scriptActComment: String
    var isActive: Boolean = false
    var DeactivateUponSuccess: Boolean = false
    var ActiveInEasy: Boolean = false
    var ActiveInMedium: Boolean = false
    var ActiveInHard: Boolean = false
    var IsSubroutine: Boolean = false
    var ActionsFireSequentially = false
    var LoopActions = false
    var LoopCount = 0
    var SequentialTargetType = SequentialScriptTarget.Unit
    lateinit var SequentialTargetName: String
    lateinit var Unknown: String

    val scriptOrConditions = mutableListOf<ScriptOrCondition>()
    val scriptActionTrues = mutableListOf<ScriptActionTrue>()
    val scriptActionFalses = mutableListOf<ScriptActionFalse>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
//        log(TAG, "parseSpecial")
//        scriptName = reader.readUint16AsciiStr()
//        scriptConmment = reader.readUint16AsciiStr()
//        scriptCondiComment = reader.readUint16AsciiStr()
//        scriptActComment = reader.readUint16AsciiStr()
//
//        isActive = reader.readByte().toBoolean()
//        DeactivateUponSuccess = reader.readByte().toBoolean()
//        ActiveInEasy = reader.readByte().toBoolean()
//        ActiveInMedium = reader.readByte().toBoolean()
//        ActiveInHard = reader.readByte().toBoolean()
//        IsSubroutine = reader.readByte().toBoolean()
//        ActionsFireSequentially = reader.readByte().toBoolean()
//        LoopActions = reader.readByte().toBoolean()
//
//        LoopCount = reader.readUInt32Le().toInt()
//        SequentialTargetType = reader.readByte().toSequentialScriptTarget()
//        SequentialTargetName = reader.readUint16AsciiStr()
//        Unknown = reader.readUint16AsciiStr()
//
//        if (reader.hasMore()) {
//            val index = reader.readUInt32Le().toInt()
//            if (mapFile.indexNameMap.containsKey(index)) {
//                val name = mapFile.indexNameMap[index]!!
//                when (name) {
//                    "ScriptOrCondition" -> {
//                        scriptOrConditions.add(ScriptOrCondition().let { it.parse(index, reader, mapFile);it })
//                    }
//                    "ScriptActionTrue" -> {
//                        scriptActionTrues.add(ScriptActionTrue().let { it.parse(index, reader, mapFile);it })
//                    }
//                    "ScriptActionFalse" -> {
//                        scriptActionFalses.add(ScriptActionFalse().let { it.parse(index, reader, mapFile);it })
//                    }
//                    else -> {
//                        throw Exception("invalid asset name $name")
//                    }
//                }
//            } else {
//                throw Exception("invalid asset index $index")
//            }
//        }
    }
}