package com.himanshu.cryptoapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.himanshu.cryptoapp.adpater.MarketAdapter
import com.himanshu.cryptoapp.databinding.FragmentMarketBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem
import com.himanshu.cryptoapp.viewmodel.CryptoViewModel
import java.util.Locale

class MarketFragment : Fragment() {
    private lateinit var binding: FragmentMarketBinding
    private lateinit var list: List<CryptoCurrencyListItem>
    private lateinit var viewModel: CryptoViewModel
    private lateinit var adapter: MarketAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.getMarketData()


        viewModel.list.observe(viewLifecycleOwner) {
            list = it?.data?.cryptoCurrencyList!!
            adapter = MarketAdapter(requireActivity(), list, "Market")
            binding.currencyRecyclerView.adapter = adapter
            binding.spinKitView.visibility = GONE
            searchCoin()
        }

        return binding.root
    }

    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().lowercase(Locale.getDefault())
                updateRecyclerView(searchText)
            }
        })
    }
    private fun updateRecyclerView(searchText: String) {
        val filteredList = list.filter { item ->
            item.name.lowercase(Locale.getDefault()).contains(searchText) ||
                    item.symbol.lowercase(Locale.getDefault()).contains(searchText)
        }
        adapter.updateData(ArrayList(filteredList))
    }
}