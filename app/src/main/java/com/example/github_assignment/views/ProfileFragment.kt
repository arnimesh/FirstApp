package com.example.github_assignment.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
//import com.abhyudaya.githubredesign.viewModel.ProfileViewModelFactory
import com.example.github_assignment.adapter.ViewPagerAdapter
import com.example.github_assignment.viewModel.ProfileViewModel
import com.example.github_assignment.databinding.FragmentProfileBinding
import com.example.github_assignment.retrofit.Repo_Impl
import com.example.github_assignment.retrofit.RetrofitHelper
import com.example.github_assignment.viewModel.ProfileViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!
    lateinit var userName: String
    lateinit var viewModel: ProfileViewModel
    lateinit var viewModelFactory: ProfileViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        userName = ProfileFragmentArgs.fromBundle(requireArguments()).userName
        val repository= Repo_Impl(RetrofitHelper.getInstance())
        viewModelFactory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(activity as AppCompatActivity,viewModelFactory)
            .get(ProfileViewModel::class.java)


        val adapter = activity?.let { fragmentActivity ->
            ViewPagerAdapter(childFragmentManager, lifecycle)
        }

        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Pinned Repositories"
                1 -> tab.text = "Repositories"
            }
        }.attach()


    viewModel.userData.observe(viewLifecycleOwner, Observer {

        binding.name.text = it?.name
            binding.userName.text = "@${it?.login}"
            binding.followers.text = it?.following.toString()
            binding.following.text = it?.followers.toString()
            binding.bio.text = it?.bio?.toString()
            binding.repositories.text = it?.public_repos.toString()
            binding.stars.text = 0.toString()
            Picasso.get().load(it?.avatar_image).into(binding.profileImage)

    })
        viewModel.userNotFound.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = ProfileFragmentDirections.actionProfileFragmentToUserNotFoundFragment()
                view.findNavController().navigate(action)
            }
        })


        viewModel.getDataFromApi(userName)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}