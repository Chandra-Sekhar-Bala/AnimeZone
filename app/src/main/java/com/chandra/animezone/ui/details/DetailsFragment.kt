package com.chandra.animezone.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chandra.animezone.R
import com.chandra.animezone.databinding.FragmentDetailsBinding
import com.chandra.animezone.models.Response
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

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
            bindData(it)
        }

        viewModel.existInDB.observe(this){
            val drawable = when (it) {
                true -> ContextCompat.getDrawable(requireContext(), R.drawable.saved_filled)
                false -> ContextCompat.getDrawable(requireContext(), R.drawable.saved)
            }
            binding.saveAsFavorite.setImageDrawable(drawable)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnWatchTrailer.setOnClickListener {
            sendUserToYoutube()
        }
        binding.saveAsFavorite.setOnClickListener{
            viewModel.saveMovieToDB()
        }

    }


    private fun sendUserToYoutube() {
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
        } else {
            Toast.makeText(
                requireContext(),
                "Trailer is not available at this moment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun bindData(data: Response?) {
        if (data != null) {
            youtubeLink = data.trailer?.url
            val df = DecimalFormat("#.#")
            val score = df.format(data.score).toDouble()
            binding.rating.text = String.format("%s/10", score)
            binding.title.text = data.title
            binding.totalEpisodes.text = data.episodes.toString()
            binding.ranking.text = data.rank.toString()
            binding.duration.text = data.duration
            binding.releaseOngoing.text = if (data.airing == true) "Yes" else "No"
            binding.about.text = data.synopsis

            Glide.with(this)
                .load(data.images?.jpg?.largeImageUrl)
                .placeholder(R.drawable.anime_item_template)
                .into(binding.animePoster)

            // to set background
            Glide.with(requireActivity())
                .load(data.images?.jpg?.largeImageUrl)
                .centerCrop()
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.animePoster.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }

    }
}