package com.himanshu.cryptoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.himanshu.cryptoapp.adpater.TopGainLossPagerAdapter
import com.himanshu.cryptoapp.adpater.TopMarketAdapter
import com.himanshu.cryptoapp.databinding.FragmentHomeBinding
import com.himanshu.cryptoapp.viewmodel.CryptoViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CryptoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.getMarketData()
        getTopCurrencyList()
        setTabLayout()

        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopGainLossPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        // to hide and show the tab upper part
        binding.contentViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {

                if (position == 0) {
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                } else {
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        // to change title of tab
        TabLayoutMediator(binding.tabLayout, binding.contentViewPager) { tab, position ->
            tab.text = if (position == 0) "TOP GAINERS" else "TOP LOSERS"
        }.attach()

    }

    private fun getTopCurrencyList() {
        viewModel.list.observe(viewLifecycleOwner) {
            binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(
                requireContext(),
                it?.data?.cryptoCurrencyList!!
            )
        }
    }
}