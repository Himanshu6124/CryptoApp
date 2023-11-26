package com.himanshu.cryptoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.himanshu.cryptoapp.adpater.MarketAdapter
import com.himanshu.cryptoapp.databinding.FragmentWatchlistBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem
import com.himanshu.cryptoapp.viewmodel.CryptoViewModel

class WatchlistFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    private lateinit var watchList: ArrayList<String>
    private lateinit var watchListItem: ArrayList<CryptoCurrencyListItem>
    private lateinit var viewModel: CryptoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchlistBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.getMarketData()
        readData()

        viewModel.list.observe(viewLifecycleOwner){
            val list = it?.data?.cryptoCurrencyList
            watchListItem = ArrayList()
            watchListItem.clear()

            for (watchData in watchList) {
                for (item in list!!) {
                    if (watchData == item.symbol) {
                        watchListItem.add(item)
                    }
                }
            }
            binding.spinKitView.visibility = GONE

            if(watchList.isEmpty()){
                binding.emptyTextView.visibility = View.VISIBLE
            }
            else{
                binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext(), watchListItem, "Watch")
            }
        }
        return binding.root
    }
    private fun readData() {
        val sharedPreferences = requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }
}