package com.example.challenge

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.adapter.FollowAdapter
import com.example.challenge.adapter.UserAdapter
import com.example.challenge.databinding.FragmentFollowingBinding
import com.example.challenge.model.ItemsItem

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.following(UserDetailActivity.username)

        viewModel.following.observe(viewLifecycleOwner) { _following ->
            if (_following.isNotEmpty()) {
                binding.tvNotFound.visibility = View.GONE
                binding.rvFollowing.visibility = View.VISIBLE
                val adapter = FollowAdapter(_following)
                binding.rvFollowing.adapter = adapter
            } else {
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvFollowing.visibility = View.GONE
            }
        }

        binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())

        binding.rvFollowing.setHasFixedSize(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}