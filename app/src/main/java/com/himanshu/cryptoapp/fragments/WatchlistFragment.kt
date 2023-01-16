package com.himanshu.cryptoapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.himanshu.cryptoapp.R
import com.himanshu.cryptoapp.adpater.MarketAdapter
import com.himanshu.cryptoapp.apis.ApiInterface
import com.himanshu.cryptoapp.apis.ApiUtilities
import com.himanshu.cryptoapp.databinding.FragmentWatchlistBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class WatchlistFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    private lateinit var watchList : ArrayList<String>
    private lateinit var watchListItem : ArrayList<CryptoCurrencyListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchlistBinding.inflate(layoutInflater)

        readData()

        lifecycleScope.launch(Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null)
            {
                Log.d("HP", "list ${res.body()!!.data.cryptoCurrencyList?.get(0)?.symbol}")


                withContext(Dispatchers.Main){
                    watchListItem = ArrayList()
                    watchListItem.clear()

                    Log.d("sd",watchList.size.toString())

                    for(watchData in watchList)
                    {
                        for(item in res.body()!!.data.cryptoCurrencyList!!){
                            if (watchData== item.symbol){
                                watchListItem.add(item)
                            }
                        }
                    }
                    binding.spinKitView.visibility= GONE
                    binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext(),watchListItem,"Watch")
                }
            }
        }


        return binding.root
    }

    private fun readData() {

        Log.i("HPS", "JOD")
        val sharedPreferences = requireContext().getSharedPreferences("watchlisting" , Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlisting",ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type

        watchList = gson.fromJson(json,type)
    }

}