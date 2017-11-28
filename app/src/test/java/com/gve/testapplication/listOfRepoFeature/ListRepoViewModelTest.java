package com.gve.testapplication.listOfRepoFeature;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gve.testapplication.ListOfRepoFeature.data.RepositoryRepo;
import com.gve.testapplication.ListOfRepoFeature.presentation.ListRepoViewModel;
import com.gve.testapplication.ListOfRepoFeature.presentation.RepoDisplayableMapper;
import com.gve.testapplication.core.recyclerview.DisplayableItem;
import com.gve.testapplication.listOfRepoFeature.data.PojoUtils;
import com.gve.testapplication.test_common.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * Created by gve on 29/11/2017.
 */

public class ListRepoViewModelTest extends BaseTest {

    @Mock
    private RepositoryRepo repositoryRepo;

    private Gson gson;

    @Before
    public void setUp() {
        final GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    @Test
    public void listRepoViewModelWithoutAnyScrollTest() {
        Mockito.when(repositoryRepo.fetch(anyInt())).thenReturn(PojoUtils.getList(gson));
        ListRepoViewModel viewModel = new ListRepoViewModel(new RepoDisplayableMapper(), repositoryRepo);

        TestSubscriber<List<DisplayableItem>> testSubscriber = viewModel.getDisplayableList().test();

        assertEquals(1, testSubscriber.values().size());
        assertEquals(2, testSubscriber.values().get(0).size());
        testSubscriber.assertNoErrors();
        testSubscriber.assertNotComplete();
    }

    @Test
    public void listRepoViewModelWithScrollTest() throws Exception {
        Mockito.when(repositoryRepo.fetch(anyInt())).thenReturn(PojoUtils.getList(gson));
        ListRepoViewModel viewModel = new ListRepoViewModel(new RepoDisplayableMapper(), repositoryRepo);
        List<List<DisplayableItem>> listResult = new ArrayList<>();
        TestSubscriber<List<DisplayableItem>> testSubscriber = new TestSubscriber<>();

        viewModel.getDisplayableList()
                .doOnNext(list -> {
                    System.out.println("flowable 2 on next " + list.size());
                    listResult.add(list);
                })
                .subscribe(testSubscriber);
        viewModel.callableFetch().call();


        testSubscriber.assertNoErrors();
        testSubscriber.assertNotComplete();
        assertEquals(3, testSubscriber.values().size());
        assertEquals(3, listResult.size());

        // first load of 2 items
        assertEquals(2, listResult.get(0).size());

        // enable fetch, add progress bar to the current list
        assertEquals(3, listResult.get(1).size());

        // new list fetched and added to current list removing the progress bar item
        assertEquals(4, listResult.get(2).size());
    }
}
