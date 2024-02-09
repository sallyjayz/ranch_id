package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.database.UsedEnumeratorTagDao
import com.sallyjayz.ranchid.model.usedenumeratortag.UsedEnumeratorTag
import com.sallyjayz.ranchid.service.register.usedenumeratortag.UsedEnumeratorTagApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 20-Jun-23.
 */
class UsedEnumeratorTagRepository @Inject constructor(
    private val usedEnumeratorTagApiService: UsedEnumeratorTagApiService,
    private val usedEnumeratorTagDao: UsedEnumeratorTagDao
) {

    fun getAllUsedEnumeratorTag(username: String) = apiRequestFlow {
        usedEnumeratorTagApiService.getAllUsedEnumeratorTag(username)
    }

    suspend fun saveUsedEnumeratorTag(usedEnumeratorTag: UsedEnumeratorTag) =
        usedEnumeratorTagDao.insertUsedEnumeratorTag(usedEnumeratorTag)

    fun getSelectedUsedEnumeratorTag(selectedUsedEnumeratorTag: String) =
        usedEnumeratorTagDao.getSelectedUsedEnumeratorTag(selectedUsedEnumeratorTag)

    fun deleteUsedEnumeratorTag() = usedEnumeratorTagDao.deleteAllUsedEnumeratorTag()

}