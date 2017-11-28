package com.gve.testapplication.ListOfRepoFeature.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gve on 28/11/2017.
 */

public class RepositoryRepo {

    private GitHubApiService service;
    private static final String REPO_XING = "xing";
    private static final int NUM_PER_PAGE = 10;

    @Inject
    public RepositoryRepo(GitHubApiService service) {
        this.service = service;
    }

    
    public Single<List<Repository>> fetch(int page) {
        return service.getRepo(REPO_XING, page, NUM_PER_PAGE)
                .subscribeOn(Schedulers.io())
                .flatMap(MapperRepoRawToRepo.INSTANCE.getMapperListRepoRawToRepo());
    }

}
