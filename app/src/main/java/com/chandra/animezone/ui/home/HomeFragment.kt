package com.chandra.animezone.ui.home

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.R
import com.chandra.animezone.adapter.PopularAnimeAdapter
import com.chandra.animezone.databinding.FragmentHomeBinding
import com.chandra.animezone.repository.network.AnimeAPI
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    lateinit var adapter : PopularAnimeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        adapter = PopularAnimeAdapter()
        binding.searchResultRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchResultRecycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val imageList = ArrayList<SlideModel>()
        viewModel.popularAnimeList.observe(this){
            for( item in it){
                imageList.add(SlideModel(item.entry!!.images!!.jpg!!.largeImageUrl, item.entry.title, ScaleTypes.CENTER_INSIDE))
            }
            binding.imageSlider.setImageList(imageList = imageList)
        }
        viewModel.topAnimeList.observe(this){
            Log.d(CONSTANTS.TAG, "Data is : ${it.toString()} ")
            adapter.submitList(it)
        }
    }
}
