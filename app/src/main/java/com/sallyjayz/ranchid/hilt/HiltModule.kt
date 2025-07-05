package com.sallyjayz.ranchid.hilt

import com.sallyjayz.ranchid.database.*
import com.sallyjayz.ranchid.model.farmlocation.FarmLocationWithState
import com.sallyjayz.ranchid.repository.*
import com.sallyjayz.ranchid.repository.vet.AnimalTypeWithVaccineRepository
import com.sallyjayz.ranchid.repository.vet.LivestockWithTreatmentHistoryRepository
import com.sallyjayz.ranchid.repository.vet.LivestockWithVaccinationHistoryRepository
import com.sallyjayz.ranchid.repository.vet.TreatmentRepository
import com.sallyjayz.ranchid.repository.vet.TreatmentTypeRepository
import com.sallyjayz.ranchid.repository.vet.VaccinationRepository
import com.sallyjayz.ranchid.repository.vet.VetDashboardActivitiesRepository
import com.sallyjayz.ranchid.service.auth.AuthApiService
import com.sallyjayz.ranchid.service.dashboard.DashboardActivitiesApiService
import com.sallyjayz.ranchid.service.register.keeper.KeeperApiService
import com.sallyjayz.ranchid.service.farmlocation.FarmLocationApiService
import com.sallyjayz.ranchid.service.farmlocation.FarmLocationWithStateApiService
import com.sallyjayz.ranchid.service.generalinformation.GeneralInformationApiService
import com.sallyjayz.ranchid.service.lga.StateLgaApiService
import com.sallyjayz.ranchid.service.register.packinglist.PackingListApiService
import com.sallyjayz.ranchid.service.register.animaltypebreed.AnimalTypeBreedApiService
import com.sallyjayz.ranchid.service.register.owner.OwnerApiService
import com.sallyjayz.ranchid.service.register.registrationlocation.RegistrationLocationApiService
import com.sallyjayz.ranchid.service.register.taglivestock.TagLivestockApiService
import com.sallyjayz.ranchid.service.register.unusedenumeratortag.UnusedEnumeratorTagApiService
import com.sallyjayz.ranchid.service.register.unusedpassport.UnusedPassportApiService
import com.sallyjayz.ranchid.service.register.usedenumeratortag.UsedEnumeratorTagApiService
import com.sallyjayz.ranchid.service.report.ReportListApiService
import com.sallyjayz.ranchid.service.vet.AnimalTypeWithVaccineApiService
import com.sallyjayz.ranchid.service.vet.LivestockWithTreatmentHistoryApiService
import com.sallyjayz.ranchid.service.vet.LivestockWithVaccinationHistoryApiService
import com.sallyjayz.ranchid.service.vet.TreatmentApiService
import com.sallyjayz.ranchid.service.vet.TreatmentTypeApiService
import com.sallyjayz.ranchid.service.vet.VaccinationApiService
import com.sallyjayz.ranchid.service.vet.VetDashboardActivitiesApiService
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
    fun provideFarmLocationWithStateRepository(farmLocationWithStateApiService: FarmLocationWithStateApiService, farmLocationWithStateDao: FarmLocationWithStateDao) =
        FarmLocationWithStateRepository(farmLocationWithStateApiService, farmLocationWithStateDao)

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
    fun provideUnusedEnumeratorTagRepository(unusedEnumeratorTagApiService: UnusedEnumeratorTagApiService,
                                             unusedEnumeratorTagDao: UnusedEnumeratorTagDao) =
        UnusedEnumeratorTagRepository(unusedEnumeratorTagApiService, unusedEnumeratorTagDao)

    @Provides
    fun provideUsedEnumeratorTagRepository(usedEnumeratorTagApiService: UsedEnumeratorTagApiService,
                                           usedEnumeratorTagDao: UsedEnumeratorTagDao) =
        UsedEnumeratorTagRepository(usedEnumeratorTagApiService, usedEnumeratorTagDao)

    @Provides
    fun provideAnimalTypeRepository(animalTypeBreedApiService: AnimalTypeBreedApiService, animalTypeDao: AnimalTypeDao) =
        AnimalTypeRepository(animalTypeBreedApiService, animalTypeDao)

    @Provides
    fun provideAnimalBreedRepository(animalTypeBreedApiService: AnimalTypeBreedApiService, animalBreedDao: AnimalBreedDao) =
        AnimalBreedRepository(animalTypeBreedApiService, animalBreedDao)

    @Provides
    fun providePackingListRepository(packingListApiService: PackingListApiService,
                                     livestockDataDao: LivestockDataDao,
                                     packingListDao: PackingListDao) =
        PackingListRepository(packingListApiService, livestockDataDao, packingListDao)

    @Provides
    fun provideGeneralInformationRepository(generalInformationApiService: GeneralInformationApiService) =
        GeneralInformationRepository(generalInformationApiService)

    @Provides
    fun provideReportListRepository(reportListApiService: ReportListApiService) =
        ReportListRepository(reportListApiService)

    @Provides
    fun provideVetDashboardActivitiesRepository(vetDashboardActivitiesApiService: VetDashboardActivitiesApiService,
                                             vetDashboardActivitiesDao: VetDashboardActivitiesDao) =
        VetDashboardActivitiesRepository(vetDashboardActivitiesApiService, vetDashboardActivitiesDao)

    @Provides
    fun provideLivestockWithVaccinationHistoryRepository(
        livestockWithVaccinationHistoryApiService: LivestockWithVaccinationHistoryApiService
    ) = LivestockWithVaccinationHistoryRepository(livestockWithVaccinationHistoryApiService)

    @Provides
    fun provideVaccinationRepository(
        vaccinationApiService: VaccinationApiService
    ) = VaccinationRepository(vaccinationApiService)

    @Provides
    fun provideLivestockWithTreatmentHistoryRepository(
        livestockWithTreatmentHistoryApiService: LivestockWithTreatmentHistoryApiService
    ) = LivestockWithTreatmentHistoryRepository(livestockWithTreatmentHistoryApiService)

    @Provides
    fun provideTreatmentRepository(
        treatmentApiService: TreatmentApiService
    ) = TreatmentRepository(treatmentApiService)

    @Provides
    fun provideVetAnimalTypeWithVaccineRepository(animalTypeWithVaccineApiService: AnimalTypeWithVaccineApiService,
                                                  vetAnimalTypeWithVaccineDao: VetAnimalTypeWithVaccineDao) =
        AnimalTypeWithVaccineRepository(animalTypeWithVaccineApiService, vetAnimalTypeWithVaccineDao)

    @Provides
    fun provideVetTreatmentTypeRepository(treatmentTypeApiService: TreatmentTypeApiService,
                                          vetTreatmentTypeDao: VetTreatmentTypeDao) =
        TreatmentTypeRepository(treatmentTypeApiService, vetTreatmentTypeDao)

}