package com.example.assignmentapplisted.home.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentapplisted.home.data.OpenInDAO
import com.example.assignmentapplisted.home.data.RvLinkModalClass
import com.example.assignmentapplisted.network.ApiResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel() {


    private val _state = MutableLiveData<Int>()
    val state : MutableLiveData<Int>
        get() = _state
    fun setState(i:Int){
       _state.value=i
        when(state.value){
            1->{
                setListDataRecent()
            }
            2->{
                setListDataTop()
            }
        }
    }

    private val _data = MutableLiveData<ApiResult<OpenInDAO>>()
    val data : MutableLiveData<ApiResult<OpenInDAO>>
        get() = _data
    fun get(){
        viewModelScope.launch {
           _data.value= repository.getData()
        }
    }
    private val _listData = MutableLiveData<List<RvLinkModalClass>>()
    val listData : MutableLiveData<List<RvLinkModalClass>>
        get() = _listData
    private fun setListDataRecent(){
        val list= data.value?.data as OpenInDAO
        val newList=ArrayList<RvLinkModalClass>()
        for (item in list.data?.recentLinks!!){
            newList.add(RvLinkModalClass(item.originalImage.toString(),item.title.toString(),item.createdAt.toString(),item.totalClicks.toString(),item.webLink.toString()))
        }
        _listData.value=newList
    }
     fun setListDataTop(){
        val list= data.value?.data as OpenInDAO
        val newList=ArrayList<RvLinkModalClass>()
        for (item in list.data?.topLinks!!){
            newList.add(RvLinkModalClass(item.originalImage.toString(),item.title.toString(),item.createdAt.toString(),item.totalClicks.toString(),item.webLink.toString()))
        }
        _listData.value=newList
    }
}