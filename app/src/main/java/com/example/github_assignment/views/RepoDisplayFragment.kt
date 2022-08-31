package com.example.github_assignment.views

import android.opengl.Visibility
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
import com.example.github_assignment.adapter.RecyclerViewAdapter
import com.example.github_assignment.viewModel.ProfileViewModel
import com.example.github_assignment.databinding.FragmentRepoDisplayBinding
import com.example.github_assignment.retrofit.Repo_Impl
import com.example.github_assignment.retrofit.RetrofitHelper
import com.example.github_assignment.viewModel.ProfileViewModelFactory


class RepoDisplayFragment : Fragment() {
    var _binding: FragmentRepoDisplayBinding? = null
    val binding get() = _binding!!

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: ProfileViewModel
    lateinit var viewModelFactory: ProfileViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentRepoDisplayBinding.inflate(inflater, container, false)
        val view = binding.root

        val repository= Repo_Impl(RetrofitHelper.getInstance())
        viewModelFactory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(activity as AppCompatActivity,viewModelFactory)
            .get(ProfileViewModel::class.java)

        binding.recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = linearLayoutManager

        viewModel.repoList.observe(viewLifecycleOwner, Observer { repoList ->
            recyclerViewAdapter = activity?.let { fragmentActivity ->
                RecyclerViewAdapter(fragmentActivity.baseContext,repoList!!)
            }!!
            binding.recyclerView.adapter = recyclerViewAdapter

            recyclerViewAdapter.setOnItemClickListener(object: RecyclerViewAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                // viewModel.getContributorFromApi(repoList?.get(position)!!.name, repoList[position].owner.login)

                    val action = ProfileFragmentDirections
                        .actionProfileFragmentToContributorFragment(repoList.get(position).name, repoList[position].owner.login)
                    view.findNavController().navigate(action)
                }
            })

        })





        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}