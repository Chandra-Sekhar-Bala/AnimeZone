package com.chandra.animezone.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.Response
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val animeAPI: AnimeAPI) : ViewModel() {

    private var _searchAnimeList = MutableLiveData<List<Response>>()
    val searchAnimeList: LiveData<List<Response>>
        get() = _searchAnimeList
    private var searchJob: Job? = null

    fun searchThisAnime(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            Log.d(CONSTANTS.TAG, "inside vieModeScope")
            try {
                val data = animeAPI.getParticularAnime(query)
                _searchAnimeList.value = data.data

            } catch (e: Exception) {
            Log.d(CONSTANTS.TAG, "getTopAnime error: ${e.message}")
        }
    }
}
}