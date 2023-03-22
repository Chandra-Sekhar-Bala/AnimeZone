package com.chandra.animezone.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chandra.animezone.adapter.ItemClickListener
import com.chandra.animezone.adapter.PopularAnimeAdapter
import com.chandra.animezone.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemClickListener {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: PopularAnimeAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        adapter = PopularAnimeAdapter(this)
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.layoutManager = layoutManager
        binding.searchRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCross.setOnClickListener {
            binding.searchAnime.text.clear()
        }

        viewModel.searchAnimeList.observe(this){
            adapter.submitList(it)
        }

        binding.searchAnime.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    viewModel.searchThisAnime(s.toString())
                    showNoResultLayout(false)
                } else {
                    showNoResultLayout(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun showNoResultLayout(noResult: Boolean) {
        when (noResult) {
            true -> {
                binding.searchRecyclerView.visibility = View.GONE
                binding.noSearchResult.animate()
                    .alpha(1f)
                    .setDuration(200)
                    .withEndAction { binding.noSearchResult.visibility = View.VISIBLE }
            }
            false -> {
                binding.searchRecyclerView.visibility = View.VISIBLE
                binding.noSearchResult.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction { binding.noSearchResult.visibility = View.GONE }
            }
        }
    }

    override fun OnItemCLicked(anime_id: Int?) {
        if (anime_id == null) {
            Toast.makeText(
                requireContext(),
                "Details not available at this moment",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val direction = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(anime_id)
            findNavController().navigate(direction)
        }
    }

}