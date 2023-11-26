package com.himanshu.cryptoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.cryptoapp.apis.CryptoService
import com.himanshu.cryptoapp.models.MarketModel
import com.himanshu.cryptoapp.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository(CryptoService.getInstance())
    private var _list = MutableLiveData<MarketModel?>()
    val list: LiveData<MarketModel?> get() = _list

    fun getMarketData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getMarketData().body()
            _list.postValue(data)
        }
    }
}