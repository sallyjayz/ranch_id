package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag
import com.sallyjayz.ranchid.repository.UnusedEnumeratorTagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 14-May-23.
 */


@HiltViewModel
class UnusedEnumeratorTagViewModel @Inject constructor(
    private val unusedEnumeratorTagRepository: UnusedEnumeratorTagRepository,
    context: Application
): AndroidViewModel(context)  {

    fun getSelectedUnusedEnumeratorTag(selectedEnumeratorTag: String) =
        unusedEnumeratorTagRepository.getSelectedUnusedEnumeratorTag(selectedEnumeratorTag)

    fun getTagRowIfExist(tagId: String): Boolean = unusedEnumeratorTagRepository.getTagRowIfExist(tagId)

    fun updateEnumeratorTagById(status: String, id: Int): Int {
        return unusedEnumeratorTagRepository.updateEnumeratorTagById(status, id)
    }

    /*fun getSelectedUnusedEnumeratorTag(selectedEnumeratorTag: String, status: String) =
        unusedEnumeratorTagRepository.getSelectedUnusedEnumeratorTag(selectedEnumeratorTag, status)*/

    suspend fun insertUnusedEnumeratorTag(allUnusedEnumeratorTag: AllUnusedEnumeratorTag) =
        unusedEnumeratorTagRepository.saveUnusedEnumeratorTag(allUnusedEnumeratorTag)

    fun deleteAllUnusedEnumeratorTag() = unusedEnumeratorTagRepository.deleteAllUnusedEnumeratorTag()

}