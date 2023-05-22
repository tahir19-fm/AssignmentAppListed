package com.example.assignmentapplisted.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapplisted.R
import com.example.assignmentapplisted.databinding.FragmentHomeBinding
import com.example.assignmentapplisted.home.data.OpenInDAO
import com.example.assignmentapplisted.home.util.HomeScreenAdapter
import com.example.assignmentapplisted.home.util.HomeViewModel
import com.example.assignmentapplisted.network.ApiResult

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel : HomeViewModel by activityViewModels()
    companion object{
        const val RECENT_LINKS=1
        const val TOP_LINKS=2
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpObserver()
    }


    private fun setupViews() {
    binding.rvLinksData.layoutManager=LinearLayoutManager(requireContext())
        binding.apply {
            bad.setBackgroundResource(R.drawable.card_border)
            tvRecentLinks.setOnClickListener{
                viewModel.setState(RECENT_LINKS)
            }
            tvTopLinks.setOnClickListener{
                viewModel.setState(TOP_LINKS)
            }
        }
    }

    private fun setUpObserver() {
    viewModel.data.observe(viewLifecycleOwner){
        when(it){
            ApiResult.Loading->{

            }
            is ApiResult.Success->{
               val data=it.data as OpenInDAO
                viewModel.setListDataTop()
            }
            is ApiResult.Error->{

                }
        }
    }
        viewModel.listData.observe(viewLifecycleOwner){
            binding.rvLinksData.adapter=HomeScreenAdapter(it)
        }
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                RECENT_LINKS->{
                    setRecentState()
                }
                TOP_LINKS->{
                    setTopState()
                }
            }
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setRecentState(){
        binding.apply {
            tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvRecentLinks.setBackgroundResource(R.drawable.link_btn_res)
            tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvTopLinks.setBackgroundResource(R.color.transparent_color)
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setTopState(){
        binding.apply {
            tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvTopLinks.setBackgroundResource(R.drawable.link_btn_res)
            tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvRecentLinks.setBackgroundResource(R.color.transparent_color)
        }
    }


}