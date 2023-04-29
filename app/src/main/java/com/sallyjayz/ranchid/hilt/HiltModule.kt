package com.sallyjayz.ranchid.hilt

import com.sallyjayz.ranchid.database.*
import com.sallyjayz.ranchid.repository.*
import com.sallyjayz.ranchid.service.auth.AuthApiService
import com.sallyjayz.ranchid.service.dashboard.DashboardActivitiesApiService
import com.sallyjayz.ranchid.service.register.keeper.KeeperApiService
import com.sallyjayz.ranchid.service.farmlocation.FarmLocationApiService
import com.sallyjayz.ranchid.service.lga.StateLgaApiService
import com.sallyjayz.ranchid.service.register.animaltypebreed.AnimalTypeBreedApiService
import com.sallyjayz.ranchid.service.register.owner.OwnerApiService
import com.sallyjayz.ranchid.service.register.registrationlocation.RegistrationLocationApiService
import com.sallyjayz.ranchid.service.register.taglivestock.TagLivestockApiService
import com.sallyjayz.ranchid.service.register.unusedenumeratortag.UnusedEnumeratorTagApiService
import com.sallyjayz.ranchid.service.register.unusedpassport.UnusedPassportApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideAuthRepository(authApiService: AuthApiService) = AuthRepository(authApiService)

    @Provides
    fun provideOwnerRepository(ownerApiService: OwnerApiService) =
        OwnerRepository(ownerApiService)

    @Provides
    fun provideKeeperRepository(keeperApiService: KeeperApiService) = KeeperRepository(keeperApiService)

    @Provides
    fun provideRegistrationLocationRepository(registrationLocationApiService: RegistrationLocationApiService) =
        RegistrationLocationRepository(registrationLocationApiService)

    @Provides
    fun provideTagLivestockRepository(tagLivestockApiService: TagLivestockApiService) =
        TagLivestockRepository(tagLivestockApiService)

    @Provides
    fun provideFarmLocationRepository(farmLocationApiService: FarmLocationApiService, farmLocationDao: FarmLocationDao) =
        FarmLocationRepository(farmLocationApiService, farmLocationDao)

    @Provides
    fun provideStateRepository(stateLgaApiService: StateLgaApiService, stateDao: StateDao) =
        StateRepository(stateLgaApiService, stateDao)

    @Provides
    fun provideLgaRepository(stateLgaApiService: StateLgaApiService, lgaDao: LgaDao) =
        LgaRepository(stateLgaApiService, lgaDao)

    @Provides
    fun provideAllOwnersListRepository(tagLivestockApiService: TagLivestockApiService,
                                       allOwnersDao: AllOwnersDao) =
        AllOwnerListRepository(tagLivestockApiService, allOwnersDao)

    @Provides
    fun provideAllKeepersListRepository(tagLivestockApiService: TagLivestockApiService,
                                        allKeepersDao: AllKeepersDao) =
        AllKeeperListRepository(tagLivestockApiService, allKeepersDao)

    @Provides
    fun provideDashboardActivitiesRepository(dashboardActivitiesApiService: DashboardActivitiesApiService,
                                             dashboardActivitiesDao: DashboardActivitiesDao) =
        DashboardActivitiesRepository(dashboardActivitiesApiService, dashboardActivitiesDao)

    @Provides
    fun provideUnusedPassportRepository(unusedPassportApiService: UnusedPassportApiService,
                                        unusedPassportDao: UnusedPassportDao) =
        UnusedPassportRepository(unusedPassportApiService, unusedPassportDao)

    @Provides
    fun provideOfflineOwnerRepository(offlineOwnerDao: OfflineOwnerDao) =
        OfflineOwnerRepository(offlineOwnerDao)

    @Provides
    fun provideOfflineKeeperRepository(offlineKeeperDao: OfflineKeeperDao) =
        OfflineKeeperRepository(offlineKeeperDao)

    @Provides
    fun provideOfflineTagLivestockRepository(offlineTagLivestockDao: OfflineTagLivestockDao) =
        OfflineTagLivestockRepository(offlineTagLivestockDao)

    @Provides
    fun provideUnusedEnumeratorTagRepository(unusedEnumeratorTagApiService: UnusedEnumeratorTagApiService) =
        UnusedEnumeratorTagRepository(unusedEnumeratorTagApiService)

    @Provides
    fun provideAnimalTypeRepository(animalTypeBreedApiService: AnimalTypeBreedApiService, animalTypeDao: AnimalTypeDao) =
        AnimalTypeRepository(animalTypeBreedApiService, animalTypeDao)

    @Provides
    fun provideAnimalBreedRepository(animalTypeBreedApiService: AnimalTypeBreedApiService, animalBreedDao: AnimalBreedDao) =
        AnimalBreedRepository(animalTypeBreedApiService, animalBreedDao)

}