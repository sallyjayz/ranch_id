package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.UnusedEnumeratorTagDao
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag
import com.sallyjayz.ranchid.service.register.unusedenumeratortag.UnusedEnumeratorTagApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class UnusedEnumeratorTagRepository @Inject constructor(
    private val unusedEnumeratorTagApiService: UnusedEnumeratorTagApiService,
    private val unusedEnumeratorTagDao: UnusedEnumeratorTagDao
) {

    fun getUnusedEnumeratorTagById(username: String, tagId: String) = apiRequestFlow {
        unusedEnumeratorTagApiService.getUnusedEnumeratorTagById(username, tagId)
    }

    fun getAllUnusedEnumeratorTag(username: String) = apiRequestFlow {
        unusedEnumeratorTagApiService.getAllUnusedEnumeratorTag(username)
    }

    suspend fun saveUnusedEnumeratorTag(allUnusedEnumeratorTag: AllUnusedEnumeratorTag) =
        unusedEnumeratorTagDao.insertUnusedEnumeratorTag(allUnusedEnumeratorTag)

    fun getSelectedUnusedEnumeratorTag(selectedUnusedEnumeratorTag: String) =
        unusedEnumeratorTagDao.getSelectedUnusedEnumeratorTag(selectedUnusedEnumeratorTag)

    fun getTagRowIfExist(tagId: String): Boolean = unusedEnumeratorTagDao.doesRowExist(tagId)

    fun updateEnumeratorTagById(status: String, id: Int): Int {
        return unusedEnumeratorTagDao.updateEnumeratorTagById(status, id)
    }


    /*fun getSelectedUnusedEnumeratorTag(selectedUnusedEnumeratorTag: String, status: String) =
        unusedEnumeratorTagDao.getSelectedUnusedEnumeratorTag(selectedUnusedEnumeratorTag, status)*/

    fun deleteAllUnusedEnumeratorTag() = unusedEnumeratorTagDao.deleteAllUnusedEnumeratorTag()

}