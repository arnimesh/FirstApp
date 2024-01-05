package com.example.github_assignment.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.github_assignment.databinding.FragmentLoginBinding
import com.example.github_assignment.retrofit.Repo_Impl
import com.example.github_assignment.retrofit.RetrofitHelper
import com.example.github_assignment.viewModel.ProfileViewModel
import com.example.github_assignment.viewModel.ProfileViewModelFactory
import kotlinx.coroutines.*


class LoginFragment : Fragment() {

    var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: ProfileViewModel
    lateinit var viewModelFactory: ProfileViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        val repository = Repo_Impl(RetrofitHelper.getInstance())
        viewModelFactory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(activity as AppCompatActivity, viewModelFactory)
            .get(ProfileViewModel::class.java)

//        viewModel.userNotFound.observe(viewLifecycleOwner) {
//            Log.d("check", it.toString())
//            if (it==true) {
//                val action =
//                    LoginFragmentDirections.actionLoginFragmentToBlankFragment()
//                view.findNavController().navigate(action)
//            } else {
//                if (it == false) {
//                    binding.progressBar.visibility=View.GONE
//                    val action =
//                        LoginFragmentDirections.actionLoginFragmentToProfileFragment()
//                    view.findNavController().navigate(action)
//                }
//            }
//
//            }
            binding.btnSubmit.setOnClickListener() {
//                binding.progressBar.visibility=View.VISIBLE
//                viewModel.getDataFromApi(binding.username.text.toString())
                val action =
                        LoginFragmentDirections.actionLoginFragmentToProfileFragment(binding.username.text.toString())
                    view.findNavController().navigate(action)

            }


        return view
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

}


