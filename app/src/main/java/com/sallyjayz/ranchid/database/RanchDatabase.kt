package com.sallyjayz.ranchid.database

import android.content.Context
import androidx.room.*
import com.sallyjayz.ranchid.model.allkeepers.AllKeepers
import com.sallyjayz.ranchid.model.allowners.AllOwners
import com.sallyjayz.ranchid.model.animalbreed.AnimalBreed
import com.sallyjayz.ranchid.model.animaltype.AnimalType
import com.sallyjayz.ranchid.model.dashboard.DashboardExit
import com.sallyjayz.ranchid.model.dashboard.DashboardKeepers
import com.sallyjayz.ranchid.model.dashboard.DashboardOwners
import com.sallyjayz.ranchid.model.dashboard.DashboardTagged
import com.sallyjayz.ranchid.model.farmlocation.FarmLocation
import com.sallyjayz.ranchid.model.lga.LGA
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock
import com.sallyjayz.ranchid.model.state.States
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassport

@Database(entities = [
    States::class,
    LGA::class,
    FarmLocation::class,
    AllOwners::class,
    AllKeepers::class,
    DashboardExit::class,
    DashboardKeepers::class,
    DashboardOwners::class,
    DashboardTagged::class,
    UnusedPassport::class,
    OfflineOwner::class,
    OfflineKeeper::class,
    OfflineTagLivestock::class,
    AnimalType::class,
    AnimalBreed::class,
    AllUnusedEnumeratorTag::class],
    version = 3,
    exportSchema = false
)

abstract class RanchDatabase : RoomDatabase() {

    abstract fun stateDao(): StateDao
    abstract fun lgaDao(): LgaDao
    abstract fun farmLocationDao(): FarmLocationDao
    abstract fun allOwnersDao(): AllOwnersDao
    abstract fun allKeepersDao(): AllKeepersDao
    abstract fun dashboardActivitiesDao(): DashboardActivitiesDao
    abstract fun unusedPassportDao(): UnusedPassportDao
    abstract fun offlineOwnerDao(): OfflineOwnerDao
    abstract fun offlineKeeperDao(): OfflineKeeperDao
    abstract fun offlineTagLivestockDao(): OfflineTagLivestockDao
    abstract fun animalTypeDao(): AnimalTypeDao
    abstract fun animalBreedDao(): AnimalBreedDao
    abstract fun unusedEnumeratorTagDao(): UnusedEnumeratorTagDao

    companion object {
        @Volatile private var instance: RanchDatabase? = null

        fun getDatabase(context: Context): RanchDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, RanchDatabase::class.java, "RanchIDDatabase")
                .fallbackToDestructiveMigration()
//                .createFromAsset("database/ranchid.db")
                .build()
    }

}