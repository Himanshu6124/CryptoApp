package com.himanshu.cryptoapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.himanshu.cryptoapp.adpater.MarketAdapter
import com.himanshu.cryptoapp.apis.ApiInterface
import com.himanshu.cryptoapp.apis.ApiUtilities
import com.himanshu.cryptoapp.databinding.FragmentMarketBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MarketFragment : Fragment() {
    private lateinit var binding : FragmentMarketBinding
    private lateinit var list : List<CryptoCurrencyListItem>
    private lateinit var adapter : MarketAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarketBinding.inflate(layoutInflater)

        list= listOf()

        adapter = MarketAdapter(requireContext(),list,"Market")
        binding.currencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null)
            {
                withContext(Dispatchers.Main){

                    list = res.body()!!.data.cryptoCurrencyList!!

                    adapter.updateData(list)
                    binding.spinKitView.visibility =GONE

                }

            }

            }
            searchCoin()




            return binding.root
    }
    lateinit var searchText : String

    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = s.toString().lowercase(Locale.getDefault())

                Log.i("HP",searchText)

                updateRecyclerView()
            }
        })

    }

    private fun updateRecyclerView() {
        val data = ArrayList<CryptoCurrencyListItem>()

        for(item in list)
        {
            val coinName = item.name.lowercase(Locale.getDefault())
            val coinSymbol = item.symbol.lowercase(Locale.getDefault())

            if(coinName.contains(searchText) || coinSymbol.contains(searchText))
            {
                data.add(item)
            }

        }
        adapter.updateData(data)
    }

}