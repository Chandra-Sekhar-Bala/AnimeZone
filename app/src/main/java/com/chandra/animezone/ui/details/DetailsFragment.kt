package com.chandra.animezone.ui.details

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.R
import com.chandra.animezone.databinding.FragmentDetailsBinding
import com.chandra.animezone.models.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private var youtubeLink: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        val args: DetailsFragmentArgs by navArgs()
        viewModel.getAnimeDetails(args.id)
    }

    override fun onResume() {
        super.onResume()
        viewModel.animeDetails.observe(this) {
            Log.d(CONSTANTS.TAG, "onResume: $it")
        }
        viewModel.animeDetails.observe(this) {
            bindData(it)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnWatchTrailer.setOnClickListener {
            if (youtubeLink != null) {
                // Open in YouTube app if available
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                intent.setPackage("com.google.android.youtube")
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                } else {
                    // Open in web browser if YouTube app is not installed
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
                }
            }else{
                Toast.makeText(requireContext(), "Trailer is not available at this moment", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindData(data: Response?) {
        if (data != null) {
            Glide.with(this)
                .load(data.images?.jpg?.largeImageUrl)
                .placeholder(R.drawable.anime_item_template)
                .into(binding.animePoster)
            youtubeLink = data.trailer?.url
            binding.rating.text = String.format("%s/10", data.score)
            binding.title.text = data.title
            binding.totalEpisodes.text = data.episodes.toString()
            binding.ranking.text = data.rank.toString()
            binding.duration.text = data.duration
            binding.releaseOngoing.text = if (data.airing == true) "Yes" else "No"
            binding.about.text = data.synopsis
        }
    }


}