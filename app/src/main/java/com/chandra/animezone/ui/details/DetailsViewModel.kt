package com.chandra.animezone.ui.details

import androidx.lifecycle.ViewModel
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val animeAPI: AnimeAPI) : ViewModel() {


}