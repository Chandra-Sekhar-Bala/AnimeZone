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
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var adapter: PopularAnimeAdapter
    private lateinit var layoutManager: GridLayoutManager


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
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.topAnimeRecycler.layoutManager = layoutManager
        binding.topAnimeRecycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        viewModel.popularAnimeList.observe(this) {
            binding.imageSlider.setImageList(imageList = it)
        }
        viewModel.topAnimeList.observe(this) {
            adapter.submitList(it)
        }
        // for pagination : Listening to recyclerView scrolling
        binding.topAnimeRecycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount
                    if (lastVisible == totalItemCount - 4 || lastVisible == totalItemCount - 5) {
                        viewModel.loadNextTopAnimePage()
                    }
                }
            })
    }
}
