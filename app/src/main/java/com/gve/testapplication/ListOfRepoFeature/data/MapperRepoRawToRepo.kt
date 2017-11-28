package com.gve.testapplication.ListOfRepoFeature.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function

/**
 * Created by gve on 28/11/2017.
 */

object MapperRepoRawToRepo {

    val mapperRepoRawToRepo: io.reactivex.functions.Function<RepoRaw, Repository> =
            Function { repoRaw -> Repository(repoRaw.id,
                    repoRaw.name ?: "",
                    repoRaw.description?: "",
                    repoRaw.owner.login,
                    repoRaw.url,
                    repoRaw.owner.html_url,
                    repoRaw.fork) }

    val mapperListRepoRawToRepo: io.reactivex.functions.Function<List<RepoRaw>, Single<List<Repository>>> =
            Function { repoRaw -> Observable.fromIterable<RepoRaw>(repoRaw)
                    .map(mapperRepoRawToRepo)
                    .toList()}

}