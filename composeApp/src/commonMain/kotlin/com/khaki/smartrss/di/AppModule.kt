package com.khaki.smartrss.di

import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repository.RssCategoryRepository
import com.khaki.repositoryimpl.QiitaFeedRSSRepositoryImpl
import com.khaki.repositoryimpl.RssCategoryRepositoryImpl
import com.khaki.smartrss.ui.screen.rss.RssViewModel
import com.khaki.smartrss.ui.screen.rss.usecase.RssUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Repository bindings
    singleOf(::QiitaFeedRSSRepositoryImpl) bind QiitaFeedRSSRepository::class

    singleOf(::RssCategoryRepositoryImpl) bind RssCategoryRepository::class

    // UseCase
    single {
        RssUseCase(
            qiitaFeedsRssRepository = get<QiitaFeedRSSRepository>(),
            rssCategoryRepository = get<RssCategoryRepository>(),
        )
    }

    // ViewModel - use factory to create a new instance when requested
    factoryOf(::RssViewModel)
}
