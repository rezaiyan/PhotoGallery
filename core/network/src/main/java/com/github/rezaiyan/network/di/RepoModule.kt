package com.github.rezaiyan.network.di

import com.github.rezaiyan.domain.repo.FlickrRepo
import com.github.rezaiyan.network.repo.FlickrRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepoModule {

    @Binds
    @Singleton
    fun bindFlickrRepo(impl: FlickrRepoImpl): FlickrRepo
}