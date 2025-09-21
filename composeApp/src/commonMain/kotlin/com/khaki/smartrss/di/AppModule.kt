package com.khaki.smartrss.di

import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repositoryimpl.QiitaFeedRSSRepositoryImpl
import com.khaki.smartrss.ui.screen.rss.usecase.RssUseCase
import com.khaki.smartrss.ui.screen.rss.RssViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Repository bindings
    singleOf(::QiitaFeedRSSRepositoryImpl) bind QiitaFeedRSSRepository::class

    // UseCase
    singleOf(::RssUseCase)

    // ViewModel - use factory to create a new instance when requested
    factoryOf(::RssViewModel)
}
