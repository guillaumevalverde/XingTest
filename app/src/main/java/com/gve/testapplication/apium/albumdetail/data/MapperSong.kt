package com.gve.testapplication.apium.albumdetail.data

import com.gve.testapplication.apium.albumlist.data.DataRaw

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function

/**
 * Created by gve on 07/11/2017.
 */

object MapperSong {

    val mapperArticleRawToSong: Function<DataRaw, Song> =
            Function { dataRaw -> if (!DataRaw.isSong(dataRaw)) { throw IllegalArgumentException() }

        Song(dataRaw.trackId ?: 0,
             dataRaw.trackName ?: "",
             dataRaw.artistName ?: "",
             dataRaw.trackTimeMillis ?: 0L,
             dataRaw.collectionId?.toLong() ?: 0)
    }

    val mapperRawToSongList: Function<List<DataRaw>, Single<List<Song>>> =
        Function { dataRaw -> Observable.fromIterable<DataRaw>(dataRaw)
                .filter { dataRaw1 -> DataRaw.isSong(dataRaw1) }
                .map(mapperArticleRawToSong)
                .toList() }
}


