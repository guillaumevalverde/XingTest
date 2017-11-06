package com.gve.testapplication.apium.songlist;

import android.support.v4.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gve.testapplication.apium.albumdetail.data.MapperSong;
import com.gve.testapplication.apium.albumdetail.data.Song;
import com.gve.testapplication.apium.albumdetail.data.SongRepo;
import com.gve.testapplication.apium.albumlist.data.DataRaw;
import com.gve.testapplication.apium.albumlist.data.ItunesNetworkService;
import com.gve.testapplication.core.data.ReactiveStore;
import com.gve.testapplication.test_common.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by gve on 16/11/2017.
 */

public class SongRepoTest extends BaseTest {

    @Mock
    private ReactiveStore<List<Song>> reactiveStore;

    @Mock
    private ItunesNetworkService service;

    private SongRepo repo;
    private Gson gson;

    @Before
    public void setUp() {
        final GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        repo = new SongRepo(service, reactiveStore);
    }

    @Test
    public void fetchDataNoProblemTest() {
        Single<List<DataRaw>> single = Single.just(SongDataTestUtils.getDataListRaw(gson));
        Mockito.when(service.fetchSongRawListData(SongDataTestUtils.getAlbumId())).thenReturn(single);

        TestObserver testObserver = repo.fetch(SongDataTestUtils.getAlbumId()).test();

        testObserver.assertComplete();
    }

    @Test
    public void fetchDataWithProblemTest() {
        Throwable error = new Throwable();
        Single<List<DataRaw>> single = Single.error(error);
        Mockito.when(service.fetchSongRawListData(SongDataTestUtils.getAlbumId())).thenReturn(single);

        TestObserver testObserver = repo.fetch(SongDataTestUtils.getAlbumId()).test();

        testObserver.assertError(error);
    }

    @Test
    public void getStreamWithNotDataInDbTest() {
        Single<List<DataRaw>> fetchDataRaw = Single.just(SongDataTestUtils.getDataListRaw(gson));
        Long dateTime = new Date().getTime();

        BehaviorSubject<Pair<Long, List<Song>>> storeMockbehaviorSubject = BehaviorSubject.create();

        String key = SongRepo.getKeyFromAlbum(SongDataTestUtils.getAlbumId());

        Mockito.when(service.fetchSongRawListData(SongDataTestUtils.getAlbumId())).thenReturn(fetchDataRaw);


        Mockito.when(reactiveStore.getSingular(key))
                .thenReturn(storeMockbehaviorSubject.toFlowable(BackpressureStrategy.LATEST));

        TestSubscriber testSubscriber = repo.getStream(SongDataTestUtils.getAlbumId()).test();

        storeMockbehaviorSubject.onNext(SongDataTestUtils.getEmpty());

        try {
            testSubscriber.assertValues(new ArrayList<Song>());
            testSubscriber.assertNotComplete();
            testSubscriber.assertNoErrors();
        } catch (Exception e) {
            assertTrue(false);
        }
        verify(service, times(1)).fetchSongRawListData(SongDataTestUtils.getAlbumId());
    }

    @Test
    public void getStreamWithDataInDbTest() {
        Single<List<DataRaw>> fetchDataRaw = Single.just(SongDataTestUtils.getDataListRaw(gson));
        Long dateTime = new Date().getTime();

        BehaviorSubject<Pair<Long, List<Song>>> storeMockbehaviorSubject = BehaviorSubject.create();

        String key = SongRepo.getKeyFromAlbum(SongDataTestUtils.getAlbumId());

        Mockito.when(reactiveStore.getSingular(key))
                .thenReturn(storeMockbehaviorSubject.toFlowable(BackpressureStrategy.LATEST));

        TestSubscriber<List<Song>> testSubscriber = repo.getStream(SongDataTestUtils.getAlbumId()).test();

        try {
            MapperSong.INSTANCE.getMapperRawToSongList().apply(SongDataTestUtils.getDataListRaw(gson))
                    .subscribe(list -> storeMockbehaviorSubject.onNext(new Pair(new Date().getTime(), list)));
        } catch (Exception e) {
            storeMockbehaviorSubject.onError(e);
        }

        try {
            testSubscriber.assertNotComplete();
            testSubscriber.assertNoErrors();
            testSubscriber.assertValueAt(0, list -> list.size() == SongDataTestUtils.RESULT_COUNT_SONG);
        } catch (Exception e) {
            assertTrue(false);
        }
        verify(service, times(0)).fetchSongRawListData(SongDataTestUtils.getAlbumId());
    }

    @Test
    public void isDataDeprecatedTest() throws Exception {
        assertTrue(SongRepo.isDataDeprecated(0l));
    }

    @Test
    public void isDataNotDeprecatedTest() throws Exception {
        long time = (new Date()).getTime();
        assertFalse(SongRepo.isDataDeprecated(time));
    }



}