package com.picazodev.electroniclogistica.ui.locations

import com.picazodev.electroniclogistica.data.Repository
import com.picazodev.electroniclogistica.domain.LocationAlgorithm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(FragmentComponent::class)
object locationModule {

    @Provides
    @FragmentScoped
    fun provideLocationPresenter(repository: Repository, locationAlgorithm : LocationAlgorithm) : LocationsPresenter{
        return LocationsPresenterImpl(repository, locationAlgorithm)
    }


}