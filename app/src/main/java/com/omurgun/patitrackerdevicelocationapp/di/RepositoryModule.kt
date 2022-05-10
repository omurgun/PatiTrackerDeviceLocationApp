package com.omurgun.patitrackerdevicelocationapp.di

import com.omurgun.patitrackerdevicelocationapp.data.remote.PatiService
import com.omurgun.patitrackerdevicelocationapp.data.repo.DataRepository
import com.omurgun.patitrackerdevicelocationapp.domain.repoInterfaces.IDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataRepo(api: PatiService) = DataRepository(api) as IDataRepository



}