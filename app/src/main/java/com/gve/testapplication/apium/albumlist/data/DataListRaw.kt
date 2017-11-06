package com.gve.testapplication.apium.albumlist.data

import com.google.gson.annotations.SerializedName

/**
 * Created by gve on 14/11/2017.
 */

data class DataListRaw(@SerializedName("results") val dataRawList: List<DataRaw>?)
