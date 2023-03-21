package com.chandra.animezone.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chandra.animezone.models.Response
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val animeAPI: AnimeAPI) : ViewModel() {
    private var _topAnimeList = MutableLiveData<List<Response>>()
    val topAnimeList: LiveData<List<Response>>
        get() = _topAnimeList
    fun getAnimeDetails(id: Int) {

    }


}