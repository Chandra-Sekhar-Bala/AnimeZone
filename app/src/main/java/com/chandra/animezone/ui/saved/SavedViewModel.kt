package com.chandra.animezone.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.models.Response
import com.chandra.animezone.models.toResponse
import com.chandra.animezone.repository.database.AnimeDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val dao: AnimeDAO): ViewModel() {
    private var _animeList = MutableLiveData<List<Response>>()
    val animeList: LiveData<List<Response>>
        get() = _animeList


    fun getAllAnime() {
        viewModelScope.launch {
            val data = dao.getAllAnime()
            _animeList.value = data.toResponse()
        }
    }

}