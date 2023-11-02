package com.example.githubusers.presentation.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _fragmentFollowBinding: FragmentFollowBinding? = null
    private val binding get() = _fragmentFollowBinding!!
    private val viewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragmentFollowBinding = FragmentFollowBinding.inflate(inflater, container, false)
        return _fragmentFollowBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userTab = arguments?.getString("data")
        binding.rvFollow.layoutManager = LinearLayoutManager(activity)
        val detailTab = arguments?.getString("tab_titles")
        if (detailTab == GET_FOLLOWERS) {
            userTab?.let { viewModel.getUserFollower(it) }
        } else if (detailTab == GET_FOLLOWING) {
            userTab?.let { viewModel.getUserFollowing(it) }
        }


        binding.rvFollow.layoutManager = LinearLayoutManager(activity)

        viewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.listFollow.observe(viewLifecycleOwner) {
            val adapter = FollowAdapter(it)
            binding.apply {
                rvFollow.adapter = adapter
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val TAB_TITLES = "tab_titles"
        const val GET_FOLLOWERS = "Followers"
        const val GET_FOLLOWING = "Following"
        const val DATA = "data"
    }

}