package com.gve.testapplication.apium.albumlist.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function

/**
 * Created by gve on 07/11/2017.
 */

object MapperAlbum {

    var mapperArticleRawToArticle : Function<DataRaw, Album> =
            Function { dataRaw ->
        if (!DataRaw.isAlbum(dataRaw)) {
            throw IllegalArgumentException()
        }

        Album(dataRaw.collectionId!!.toLong(),
                dataRaw.collectionName ?: "",
                dataRaw.artistName ?: "",
                dataRaw.artworkUrl100 ?: "",
                dataRaw.trackCount ?: 0)
    }

    var mapperRawToAlbumList: Function<List<DataRaw>, Single<List<Album>>> =
            Function { dataRaw -> Observable.fromIterable<DataRaw>(dataRaw)
                                        .filter { dataRaw1 -> DataRaw.isAlbum(dataRaw1) }
                                        .map(mapperArticleRawToArticle)
                                        .toList()
    }

}
