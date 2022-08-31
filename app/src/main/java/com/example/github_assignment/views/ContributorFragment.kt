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
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.github_assignment.viewModel.ContributorViewModelFactory
import com.example.github_assignment.adapter.ContributorViewAdapter
import com.example.github_assignment.viewModel.ContributorViewModel
import com.example.github_assignment.databinding.FragmentContributorBinding
import com.example.github_assignment.retrofit.Repo_Impl
import com.example.github_assignment.retrofit.RetrofitHelper
import com.example.github_assignment.viewModel.ContributorViewModelFactory
import com.example.github_assignment.viewModel.ProfileViewModel
import com.example.github_assignment.viewModel.ProfileViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContributorFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
    private var _binding: FragmentContributorBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ContributorViewModel
   // lateinit var profileViewModel: ProfileViewModel
   // lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contributorViewAdapter: ContributorViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContributorBinding.inflate(inflater, container, false)
        val view = binding.root
        val repoName = ContributorFragmentArgs.fromBundle(requireArguments()).repoName
        val userName = ContributorFragmentArgs.fromBundle(requireArguments()).userName
        val repository = Repo_Impl(RetrofitHelper.getInstance())
       val viewModelFactory = ContributorViewModelFactory(repository)

    // profileViewModelFactory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(this,viewModelFactory)[ContributorViewModel::class.java]
//      profileViewModel=ViewModelProvider(activity as AppCompatActivity, profileViewModelFactory)
//          .get(ProfileViewModel::class.java)

        binding.contributorView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this.context)
        binding.contributorView.layoutManager = linearLayoutManager

//        profileViewModel.userData.observe(viewLifecycleOwner){
//            val action = ContributorFragmentDirections
//                .actionContributorFragmentToProfileFragment()
//            view.findNavController().navigate(action)
//        }
        viewModel.contributorList.observe(viewLifecycleOwner, Observer { list ->
            contributorViewAdapter = activity?.let { activity ->
                ContributorViewAdapter(activity.baseContext, list)
            }!!
            binding.contributorView.adapter = contributorViewAdapter
            contributorViewAdapter.setOnItemClickListener(object:
                ContributorViewAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val action = ContributorFragmentDirections
                        .actionContributorFragmentToProfileFragment(list[position].login)
                    view.findNavController().navigate(action)
                }
            })
        })
        CoroutineScope(Dispatchers.IO).launch {
        viewModel.getContributorFromApi(userName, repoName)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}