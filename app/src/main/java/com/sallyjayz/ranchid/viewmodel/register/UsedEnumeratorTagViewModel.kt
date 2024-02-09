package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sallyjayz.ranchid.model.usedenumeratortag.UsedEnumeratorTag
import com.sallyjayz.ranchid.repository.UsedEnumeratorTagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 23-Jun-23.
 */

@HiltViewModel
class UsedEnumeratorTagViewModel @Inject constructor(
    private val usedEnumeratorTagRepository: UsedEnumeratorTagRepository,
    context: Application
): AndroidViewModel(context) {

    suspend fun insertUsedEnumeratorTag(usedEnumeratorTag: UsedEnumeratorTag) =
        usedEnumeratorTagRepository.saveUsedEnumeratorTag(usedEnumeratorTag)

    fun getSelectedUsedEnumeratorTag(selectedEnumeratorTag: String) =
        usedEnumeratorTagRepository.getSelectedUsedEnumeratorTag(selectedEnumeratorTag)

    fun deleteUsedEnumeratorTag() = usedEnumeratorTagRepository.deleteUsedEnumeratorTag()
}