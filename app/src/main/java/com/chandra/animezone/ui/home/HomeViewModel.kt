package com.chandra.animezone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.AnimeList
import com.chandra.animezone.models.PopularResponse
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: AnimeAPI) : ViewModel() {

    var topAnimeList = MutableLiveData<AnimeList>()

    private var _popularAnimeList = MutableLiveData<List<PopularResponse>>()
    val popularAnimeList: LiveData<List<PopularResponse>>
        get() = _popularAnimeList

    init {
        Log.d(CONSTANTS.TAG, "Calling")
        getPopularAnime()
    }

    private fun getPopularAnime() {
        viewModelScope.launch {
            try {
                Log.d(CONSTANTS.TAG, "trying")
                val data = api.getPopularAnime()
                _popularAnimeList.value = data.data!!
            } catch (e: Exception) {
                Log.d(CONSTANTS.TAG, "error: ${e.message}")
            }
        }
    }
}