package com.khaki.smartrss.di

import com.khaki.repository.HatenaFeedRSSRepository
import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repository.RssCategoryRepository
import com.khaki.repository.RssFeedRepository
import com.khaki.repository.ZennFeedRSSRepository
import com.khaki.repositoryimpl.HatenaFeedRSSRepositoryImpl
import com.khaki.repositoryimpl.QiitaFeedRSSRepositoryImpl
import com.khaki.repositoryimpl.RssCategoryRepositoryImpl
import com.khaki.repositoryimpl.RssFeedRepositoryImpl
import com.khaki.repositoryimpl.ZennFeedRSSRepositoryImpl
import com.khaki.smartrss.ui.screen.recomend.RecommendViewModel
import com.khaki.smartrss.ui.screen.recomend.usecase.RecommendUseCase
import com.khaki.smartrss.ui.screen.rss.RssViewModel
import com.khaki.smartrss.ui.screen.rss.usecase.RssUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Repository bindings
    singleOf(::QiitaFeedRSSRepositoryImpl) bind QiitaFeedRSSRepository::class

    singleOf(::ZennFeedRSSRepositoryImpl) bind ZennFeedRSSRepository::class

    singleOf(::HatenaFeedRSSRepositoryImpl) bind HatenaFeedRSSRepository::class

    singleOf(::RssCategoryRepositoryImpl) bind RssCategoryRepository::class

    singleOf(::RssFeedRepositoryImpl) bind RssFeedRepository::class

    // UseCase
    single {
        RssUseCase(
            qiitaFeedsRssRepository = get<QiitaFeedRSSRepository>(),
            zennFeedsRssRepository = get<ZennFeedRSSRepository>(),
            hatenaFeedsRssRepository = get<HatenaFeedRSSRepository>(),
            rssCategoryRepository = get<RssCategoryRepository>(),
            rssFeedRepository = get<RssFeedRepository>(),
        )
    }

    single {
        RecommendUseCase(
            rssFeedRepository = get<RssFeedRepository>(),
        )
    }

    // ViewModel - use factory to create a new instance when requested
    factoryOf(::RssViewModel)

    factoryOf(::RecommendViewModel)
}
