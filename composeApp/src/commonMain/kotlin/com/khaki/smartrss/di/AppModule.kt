package com.khaki.smartrss.di

import com.khaki.repository.HatenaFeedRSSRepository
import com.khaki.repository.OtherFeedRssRepository
import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repository.RssCategoryRepository
import com.khaki.repository.RssFeedRepository
import com.khaki.repository.SettingRepository
import com.khaki.repository.ZennFeedRSSRepository
import com.khaki.repositoryimpl.HatenaFeedRSSRepositoryImpl
import com.khaki.repositoryimpl.OtherFeedRssRepositoryImpl
import com.khaki.repositoryimpl.QiitaFeedRSSRepositoryImpl
import com.khaki.repositoryimpl.RssCategoryRepositoryImpl
import com.khaki.repositoryimpl.RssFeedRepositoryImpl
import com.khaki.repositoryimpl.SettingRepositoryImpl
import com.khaki.repositoryimpl.ZennFeedRSSRepositoryImpl
import com.khaki.smartrss.ui.screen.allfeeds.AllFeedsViewModel
import com.khaki.smartrss.ui.screen.allfeeds.usecase.AllFeedsUseCase
import com.khaki.smartrss.ui.screen.bookmark.BookmarkFeedsViewModel
import com.khaki.smartrss.ui.screen.bookmark.usecase.BookmarkFeedsUseCase
import com.khaki.smartrss.ui.screen.rss.RssViewModel
import com.khaki.smartrss.ui.screen.rss.usecase.RssUseCase
import com.khaki.smartrss.ui.screen.setting.SettingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Repository bindings
    singleOf(::QiitaFeedRSSRepositoryImpl) bind QiitaFeedRSSRepository::class

    singleOf(::ZennFeedRSSRepositoryImpl) bind ZennFeedRSSRepository::class

    singleOf(::HatenaFeedRSSRepositoryImpl) bind HatenaFeedRSSRepository::class

    singleOf(::OtherFeedRssRepositoryImpl) bind OtherFeedRssRepository::class

    singleOf(::RssCategoryRepositoryImpl) bind RssCategoryRepository::class

    singleOf(::RssFeedRepositoryImpl) bind RssFeedRepository::class

    singleOf(::SettingRepositoryImpl) bind SettingRepository::class

    // UseCase
    single {
        RssUseCase(
            qiitaFeedsRssRepository = get<QiitaFeedRSSRepository>(),
            zennFeedsRssRepository = get<ZennFeedRSSRepository>(),
            hatenaFeedsRssRepository = get<HatenaFeedRSSRepository>(),
            otherFeedsRssRepository = get<OtherFeedRssRepository>(),
            rssCategoryRepository = get<RssCategoryRepository>(),
            rssFeedRepository = get<RssFeedRepository>(),
        )
    }

    single {
        AllFeedsUseCase(
            rssFeedRepository = get<RssFeedRepository>(),
            qiitaFeedsRssRepository = get<QiitaFeedRSSRepository>(),
            zennFeedsRssRepository = get<ZennFeedRSSRepository>(),
            hatenaFeedsRssRepository = get<HatenaFeedRSSRepository>(),
            otherFeedsRssRepository = get<OtherFeedRssRepository>(),
            rssCategoryRepository = get<RssCategoryRepository>(),
        )
    }

    single {
        BookmarkFeedsUseCase(
            rssFeedRepository = get<RssFeedRepository>(),
        )
    }

    // ViewModel - use factory to create a new instance when requested
    factoryOf(::RssViewModel)

    factoryOf(::AllFeedsViewModel)

    factoryOf(::BookmarkFeedsViewModel)

    factoryOf(::SettingViewModel)
}
