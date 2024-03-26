package com.dicoding.githubappsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubappsubmission.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followAdapter: FollowAdapter
    private val followViewModel by viewModels<FollowViewModel>()

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_USERNAME = "username"

        fun newInstance(position: Int, username: String): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_USERNAME, username)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)
        val username = requireArguments().getString(ARG_USERNAME) ?: ""

        followAdapter = FollowAdapter()
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.adapter = followAdapter

        if (position == 0) {
            followViewModel.getFollowerList(username)
            followViewModel.followerList.observe(viewLifecycleOwner) { followers ->
                followAdapter.submitList(followers)
            }
        } else {
            followViewModel.getFollowingList(username)
            followViewModel.followingList.observe(viewLifecycleOwner) { following ->
                followAdapter.submitList(following)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
