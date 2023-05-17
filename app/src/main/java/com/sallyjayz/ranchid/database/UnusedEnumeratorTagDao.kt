package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag

/**
 * Created by Salama Jatau on 14-May-23.
 */

@Dao
interface UnusedEnumeratorTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnusedEnumeratorTag(allUnusedEnumeratorTag: AllUnusedEnumeratorTag)

    @Query("SELECT * FROM unused_enumerator_tag WHERE tag_id = :tagId")
    fun getSelectedUnusedEnumeratorTag(tagId: String): LiveData<AllUnusedEnumeratorTag?>

    /*@Query("SELECT * FROM unused_enumerator_tag WHERE tag_id = :tagId AND status = :status")
    fun getSelectedUnusedEnumeratorTag(tagId: String, status: String): LiveData<AllUnusedEnumeratorTag>*/

    @Query("SELECT EXISTS(SELECT * FROM unused_enumerator_tag WHERE tag_id = :tagId)")
    fun doesRowExist(tagId: String) : Boolean

    @Query("UPDATE unused_enumerator_tag SET status = :status WHERE id = :id")
    fun updateEnumeratorTagById(status: String, id: Int): Int

    @Query("DELETE FROM unused_enumerator_tag")
    fun deleteAllUnusedEnumeratorTag()

}