package com.mokresh.tretton37.di

import com.mokresh.tretton37.QuestionsRepository
import com.mokresh.tretton37.api.ApiServices
import com.mokresh.tretton37.view.QuestionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule by lazy {
    module {
        single { QuestionsRepository(get()) }
    }
}

val viewModelModule by lazy {
    module {
        viewModel { QuestionsViewModel(get()) }
    }
}

val serviceModule by lazy {
    module {
        single {
            ApiServices.create()
        }
    }

}
