package com.himanshu.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.himanshu.cryptoapp.adpater.MarketAdapter
import com.himanshu.cryptoapp.databinding.FragmentTopLossGainBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem
import com.himanshu.cryptoapp.viewmodel.CryptoViewModel
import java.util.Collections

class TopLossGainFragment(private val isGain : Boolean) : Fragment() {

    private lateinit var binding: FragmentTopLossGainBinding
    private lateinit var viewModel: CryptoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopLossGainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.getMarketData()
        getTopGainers()

        return binding.root
    }

    private fun getTopGainers() {

        viewModel.list.observe(viewLifecycleOwner) {
            val dataItem = it?.data?.cryptoCurrencyList

            if (dataItem != null) {
                Collections.sort(dataItem)
                { o1, o2 ->
                    (o1.quotes!![0].percentChange24h.toInt())
                        .compareTo(o2.quotes!![0].percentChange24h.toInt())
                }

                binding.spinKitView.visibility = GONE

                val list = ArrayList<CryptoCurrencyListItem>()

                if(isGain){
                    for (i in 0..20) {
                        list.add(dataItem[dataItem.size - 1 - i])
                    }
                }
                else{
                    for (i in 0..20) {
                        list.add(dataItem[i])
                    }
                }
                binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                    requireContext(),
                    list,
                    "Home"
                )
            }
        }
    }
}