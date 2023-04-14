package com.picazodev.electroniclogistica.ui.productdetail

import com.picazodev.electroniclogistica.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(FragmentComponent::class)
object DetailModule {


    @Provides
    @FragmentScoped
    fun provideProductDetailPresenter(repository: Repository): ProductDetailPresenter{
        return ProductDetailPresenterImpl(repository)

    }
}