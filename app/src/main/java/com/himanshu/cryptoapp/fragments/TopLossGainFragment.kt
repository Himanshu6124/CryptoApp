package com.himanshu.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.himanshu.cryptoapp.adpater.MarketAdapter
import com.himanshu.cryptoapp.apis.ApiInterface
import com.himanshu.cryptoapp.apis.ApiUtilities
import com.himanshu.cryptoapp.databinding.FragmentTopLossGainBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class TopLossGainFragment : Fragment() {

    lateinit var binding : FragmentTopLossGainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")

//        binding.spinKitView.visibility = VISIBLE

        lifecycleScope.launch(Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null)
            {
                withContext(Dispatchers.Main){
                    val dataItem = res.body()!!.data.cryptoCurrencyList

                    if (dataItem != null) {
                        Collections.sort(dataItem){

                                o1,o2 ->(o1.quotes!![0].percentChange24h.toInt())
                            .compareTo(o2.quotes!![0].percentChange24h.toInt())

                        }

                        binding.spinKitView.visibility = GONE

                        val list = ArrayList<CryptoCurrencyListItem>()

                        if(position == 1)
                        {
                            list.clear()
                            for(i in 0..20)
                            {
                                list.add(dataItem[i])
                            }
                            binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                                requireContext(),
                                list,
                                "Home"
                            )
                        }
                        else
                        {
                            list.clear()
                            for(i in 0..20)
                            {
                                list.add(dataItem[dataItem.size-1-i])
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

        }
    }


}