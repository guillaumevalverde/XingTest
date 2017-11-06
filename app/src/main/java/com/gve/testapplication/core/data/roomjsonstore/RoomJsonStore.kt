package com.gve.testapplication.core.data.roomjsonstore

import android.support.v4.util.Pair
import com.gve.testapplication.core.data.AppDataBase
import com.gve.testapplication.core.data.ReactiveStoreSingular

import java.util.Date
import java.util.concurrent.Callable

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.functions.Function

/**
 * Created by gve on 15/11/2017.
 */

class RoomJsonStore<Value> @Inject
constructor(private val appDataBase: AppDataBase,
            private var getKey: Function<Value, String>,
            private val getObjFromJson: Function<String, Value>,
            private val getJsonFromObj: Function<Value, String>,
            private val getEmptyValue: Callable<String>) : ReactiveStoreSingular<Value> {

    @Throws(Exception::class)
    override fun storeSingular(model: Value) {
        val roomJson = RoomJson(getKey.apply(model), time.call(), getJsonFromObj.apply(model))
        appDataBase.roomJsonModel().add(roomJson)
    }

    override fun getSingular(key: String): Flowable<Pair<Long, Value>> {

        val first = appDataBase.roomJsonModel().getItembyIdSingle(key).toFlowable()
                .onErrorReturn { _ -> RoomJson.getEmptyRoom(getEmptyValue) }

        val second = appDataBase.roomJsonModel().getItembyId(key)

        return Flowable.concat(first, second)
                .map { roomJson -> Pair(roomJson.timeStamp, getObjFromJson.apply(roomJson.json)) }
    }

    companion object {

        val time: Callable<Long>
            get() {
                val date = Date()
                return Callable { date.time }
            }
    }

}
