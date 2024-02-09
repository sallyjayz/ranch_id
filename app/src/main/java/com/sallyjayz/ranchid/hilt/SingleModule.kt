package com.sallyjayz.ranchid.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sallyjayz.ranchid.database.RanchDatabase
import com.sallyjayz.ranchid.service.auth.AuthApiService
import com.sallyjayz.ranchid.service.dashboard.DashboardActivitiesApiService
import com.sallyjayz.ranchid.service.farmlocation.FarmLocationApiService
import com.sallyjayz.ranchid.service.generalinformation.GeneralInformationApiService
import com.sallyjayz.ranchid.service.lga.StateLgaApiService
import com.sallyjayz.ranchid.service.register.packinglist.PackingListApiService
import com.sallyjayz.ranchid.service.register.animaltypebreed.AnimalTypeBreedApiService
import com.sallyjayz.ranchid.service.register.keeper.KeeperApiService
import com.sallyjayz.ranchid.service.register.owner.OwnerApiService
import com.sallyjayz.ranchid.service.register.registrationlocation.RegistrationLocationApiService
import com.sallyjayz.ranchid.service.register.taglivestock.TagLivestockApiService
import com.sallyjayz.ranchid.service.register.unusedenumeratortag.UnusedEnumeratorTagApiService
import com.sallyjayz.ranchid.service.register.unusedpassport.UnusedPassportApiService
import com.sallyjayz.ranchid.service.register.usedenumeratortag.UsedEnumeratorTagApiService
import com.sallyjayz.ranchid.service.report.ReportListApiService
import com.sallyjayz.ranchid.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
class SingleModule {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
        unusedPassportHeaderInterceptor: UnusedPassportHeaderInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .addInterceptor(unusedPassportHeaderInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(" https://www.test-api.naitsng.com")
//            .baseUrl("http://164.90.233.167")
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideOwnerApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): OwnerApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(OwnerApiService::class.java)

    @Provides
    fun provideKeeperApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): KeeperApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(KeeperApiService::class.java)

    @Provides
    fun provideRegistrationLocationApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): RegistrationLocationApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(RegistrationLocationApiService::class.java)

    @Provides
    fun provideTagLivestockApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): TagLivestockApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(TagLivestockApiService::class.java)

    @Provides
    fun provideFarmLocationApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): FarmLocationApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(FarmLocationApiService::class.java)

    @Provides
    fun provideStateLgaApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): StateLgaApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(StateLgaApiService::class.java)

    @Provides
    fun provideDashboardActivitiesApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): DashboardActivitiesApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(DashboardActivitiesApiService::class.java)

    @Provides
    fun provideUnusedPassportApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): UnusedPassportApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UnusedPassportApiService::class.java)

    @Provides
    fun provideAnimalTypeBreedApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): AnimalTypeBreedApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(AnimalTypeBreedApiService::class.java)

    @Provides
    fun provideUnusedEnumeratorTagApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): UnusedEnumeratorTagApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UnusedEnumeratorTagApiService::class.java)

    @Provides
    fun providePackingListApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): PackingListApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(PackingListApiService::class.java)

    @Provides
    fun provideUsedEnumeratorTagApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): UsedEnumeratorTagApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UsedEnumeratorTagApiService::class.java)

    @Provides
    fun provideGeneralInformationApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): GeneralInformationApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(GeneralInformationApiService::class.java)

    @Provides
    fun provideReportListApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): ReportListApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(ReportListApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = RanchDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideNetworkStatusTracker(@ApplicationContext context: Context) = NetworkStatusTracker(context)

    @Singleton
    @Provides
    fun provideStateDao(db: RanchDatabase) = db.stateDao()

    @Singleton
    @Provides
    fun provideLGADao(db: RanchDatabase) = db.lgaDao()

    @Singleton
    @Provides
    fun provideFarmLocationDao(db: RanchDatabase) = db.farmLocationDao()

    @Singleton
    @Provides
    fun provideAllOwnersDao(db: RanchDatabase) = db.allOwnersDao()

    @Singleton
    @Provides
    fun provideAllKeepersDao(db: RanchDatabase) = db.allKeepersDao()

    @Singleton
    @Provides
    fun provideDashboardActivitiesDao(db: RanchDatabase) = db.dashboardActivitiesDao()

    @Singleton
    @Provides
    fun provideUnusedPassportDao(db: RanchDatabase) = db.unusedPassportDao()

    @Singleton
    @Provides
    fun provideOwnerDao(db: RanchDatabase) = db.offlineOwnerDao()

    @Singleton
    @Provides
    fun provideKeeperDao(db: RanchDatabase) = db.offlineKeeperDao()

    @Singleton
    @Provides
    fun provideTagLivestockDao(db: RanchDatabase) = db.offlineTagLivestockDao()

    @Singleton
    @Provides
    fun provideAnimalTypeDao(db: RanchDatabase) = db.animalTypeDao()

    @Singleton
    @Provides
    fun provideAnimalBreedDao(db: RanchDatabase) = db.animalBreedDao()

    @Singleton
    @Provides
    fun provideUnusedEnumeratorTag(db: RanchDatabase) = db.unusedEnumeratorTagDao()

    @Singleton
    @Provides
    fun provideUsedEnumeratorTag(db: RanchDatabase) = db.usedEnumeratorTagDao()

    @Singleton
    @Provides
    fun provideLivestockData(db: RanchDatabase) = db.livestockDataDao()

    @Singleton
    @Provides
    fun providePackingList(db: RanchDatabase) = db.packingListDao()

}