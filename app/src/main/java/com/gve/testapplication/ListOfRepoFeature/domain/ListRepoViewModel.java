package com.gve.testapplication.ListOfRepoFeature.domain;

import android.support.annotation.NonNull;

import com.gve.testapplication.ListOfRepoFeature.data.RepositoryRepo;
import com.gve.testapplication.ListOfRepoFeature.presentation.RepoDisplayableMapper;
import com.gve.testapplication.core.recyclerview.DisplayableItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by gve on 28/11/2017.
 */

public class ListRepoViewModel {
    private RepoDisplayableMapper mapper;
    private RepositoryRepo repo;


    @Inject
    public ListRepoViewModel(@NonNull RepoDisplayableMapper mapper,
                             @NonNull RepositoryRepo repo) {

        this.mapper = mapper;
        this.repo = repo;
    }

    public Single<List<DisplayableItem>> getDisplayableList(int page) {
        return repo.fetch(page)
                .map(list -> mapper.apply(list));
    }

}
