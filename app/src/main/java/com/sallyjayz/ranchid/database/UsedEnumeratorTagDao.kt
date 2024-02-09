package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag
import com.sallyjayz.ranchid.model.usedenumeratortag.UsedEnumeratorTag

/**
 * Created by Salama Jatau on 23-Jun-23.
 */

@Dao
interface UsedEnumeratorTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsedEnumeratorTag(usedEnumeratorTag: UsedEnumeratorTag)

    @Query("SELECT * FROM used_enumerator_tag WHERE tag_id = :tagId")
    fun getSelectedUsedEnumeratorTag(tagId: String): LiveData<UsedEnumeratorTag>

    @Query("DELETE FROM used_enumerator_tag")
    fun deleteAllUsedEnumeratorTag()

}