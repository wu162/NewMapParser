package com.map.asset

import com.map.IMapStreamReader
import com.map.MapFile
import com.map.log

class Teams(): Asset() {

    companion object {
        private const val TAG = "Teams"
    }

    val teams = mutableListOf<Team>()

    override fun parseSpecial(reader: IMapStreamReader, mapFile: MapFile) {
        val numTeams = reader.readUInt32Le()
        for (i in 0 until numTeams) {
            teams.add(Team().let {
                it.parse(reader, mapFile);
                it
            })
        }
        log(TAG, "teams size: ${teams.size}")
    }
}