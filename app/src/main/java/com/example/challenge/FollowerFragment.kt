package com.example.challenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.adapter.FollowAdapter
import com.example.challenge.databinding.FragmentFollowerBinding
import com.example.challenge.databinding.FragmentFollowingBinding

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.follower(UserDetailActivity.username)

        viewModel.follower.observe(viewLifecycleOwner) { _follower ->
            if (_follower.isNotEmpty()) {
                binding.tvNotFound.visibility = View.GONE
                binding.rvFollower.visibility = View.VISIBLE
                val adapter = FollowAdapter(_follower)
                binding.rvFollower.adapter = adapter
            } else {
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvFollower.visibility = View.GONE
            }
        }

        binding.rvFollower.layoutManager = LinearLayoutManager(requireActivity())

        binding.rvFollower.setHasFixedSize(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}