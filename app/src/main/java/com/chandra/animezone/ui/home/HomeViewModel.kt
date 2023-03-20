package com.chandra.animezone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.AnimeList
import com.chandra.animezone.models.PopularResponse
import com.chandra.animezone.models.Response
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_DefaultViewModelFactories_ActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val api: AnimeAPI) : ViewModel() {
    private var _topAnimeList = MutableLiveData<List<Response>>()
    val topAnimeList : LiveData<List<Response>>
    get() = _topAnimeList

    private var _popularAnimeList = MutableLiveData<List<PopularResponse>>()
    val popularAnimeList: LiveData<List<PopularResponse>>
        get() = _popularAnimeList
    private var pageNumber = 1
    init {
        getPopularAnime()
        getTopAnime()
    }

    private fun getTopAnime() {
        viewModelScope.launch {
            try {
                val data = api.getTopAnime(pageNumber)
                _topAnimeList.value = data.data
                pageNumber = data.pagination?.currentPage?.plus(1) ?: 1
            } catch (e: Exception) {
                Log.d(CONSTANTS.TAG, "getTopAnime error: ${e.message}")
            }
        }
    }

    private fun getPopularAnime() {
        viewModelScope.launch {
            try {
                val data = api.getPopularAnime()
                _popularAnimeList.value = data.data!!
            } catch (e: Exception) {
                Log.d(CONSTANTS.TAG, "getPopularAnime error: ${e.message}")
            }
        }
    }


}