package com.gve.testapplication.core.data.roomjsonstore

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.concurrent.Callable


/**
 * Created by gve on 15/11/2017.
 */

@Entity(tableName = "roomJsonTable")
class RoomJson constructor(@field:PrimaryKey var id: String = "",
                           var timeStamp: Long = 0L,
                           var json: String ="") {

    companion object {

        fun getEmptyRoom(emptyJson: Callable<String>): RoomJson {
            return RoomJson(json = emptyJson.call())
        }
    }
}
