package com.gve.testapplication.apium.songlist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gve.testapplication.apium.albumdetail.data.MapperSong;
import com.gve.testapplication.apium.albumdetail.data.Song;
import com.gve.testapplication.apium.albumlist.data.ConstItunes;
import com.gve.testapplication.apium.albumlist.data.DataListRaw;
import com.gve.testapplication.apium.albumlist.data.DataRaw;
import com.gve.testapplication.test_common.BaseTest;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gve on 15/11/2017.
 */

public class SongDataTest extends BaseTest {

    private Gson gson;

    @Before
    public void setUp() {
        final GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    @Test
    public void deserializeWrapperTypeSong() {
        DataRaw dataRaw = gson.fromJson(SongDataTestUtils.SONG_JSON, DataRaw.class);

        assertEquals(ConstItunes.WRAPPER_SONG_TYPE, dataRaw.getWrapperType());
    }

    @Test
    public void deserializeWrapperTypeAlbum() {
        DataRaw dataRaw = gson.fromJson(SongDataTestUtils.COLLECTION_TYPE_ALBUM_JSON, DataRaw.class);

        assertEquals(ConstItunes.WRAPPER_COLLECTION_TYPE, dataRaw.getWrapperType());
        assertEquals(ConstItunes.COLLECTION_ALBUM_TYPE, dataRaw.getCollectionType());
    }

    @Test
    public void deserializeRawDataFromGet() {
        DataListRaw dataListRaw = gson.fromJson(SongDataTestUtils.RAW_JSON_GET_LIST, DataListRaw.class);

        assertEquals(SongDataTestUtils.RESULT_COUNT, dataListRaw.getDataRawList().size());
    }

    @Test
    public void dataRawIsSong() {
        DataRaw dataRaw = gson.fromJson(SongDataTestUtils.SONG_JSON, DataRaw.class);
        assertTrue(DataRaw.Companion.isSong(dataRaw));
    }

    @Test
    public void dataRawIsNotSong() {
        DataRaw dataRaw = gson.fromJson(SongDataTestUtils.COLLECTION_TYPE_ALBUM_JSON, DataRaw.class);
        assertFalse(DataRaw.Companion.isSong(dataRaw));
    }

    @Test
    public void getListSongFromListRaw() {
        DataListRaw dataListRaw = gson.fromJson(SongDataTestUtils.RAW_JSON_GET_LIST, DataListRaw.class);

        TestObserver<List<Song>> testObserver =
                Single.just(dataListRaw.getDataRawList())
                        .flatMap(list -> MapperSong.INSTANCE.getMapperRawToSongList().apply(list))
                        .test();

        testObserver.assertValueAt(0, songs -> songs.size() == SongDataTestUtils.RESULT_COUNT_SONG);
    }


}
