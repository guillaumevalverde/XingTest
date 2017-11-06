package com.gve.testapplication.apium.albumlist.data

/**
 * Created by gve on 14/11/2017.
 */


data class DataRaw(val wrapperType: String?,
                   val collectionType: String?,
                   val collectionId: Int?,
                   val artistId: Int?,
                   val artistName: String?,
                   val collectionName: String?,
                   val trackCount: Int?,
                   val artworkUrl100: String?,
                   val trackName: String?,
                   val trackTimeMillis: Long?,
                   val trackId: Long?
                   ) {
  companion object {

         fun isAlbum(dataRaw: DataRaw): Boolean {
            return dataRaw.wrapperType?.contentEquals(ConstItunes.WRAPPER_COLLECTION_TYPE) ?: false
                    && dataRaw.collectionType?.contentEquals(ConstItunes.COLLECTION_ALBUM_TYPE) ?: false
        }

        fun isSong(dataRaw: DataRaw): Boolean {
            return dataRaw.wrapperType?.contentEquals(ConstItunes.WRAPPER_SONG_TYPE) ?: false
        }
    }
}
