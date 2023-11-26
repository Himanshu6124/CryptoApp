package com.himanshu.cryptoapp.adpater

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.himanshu.cryptoapp.fragments.TopLossGainFragment

class TopGainLossPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        return if(position ==0)
            TopLossGainFragment(true)
        else{
            TopLossGainFragment(false)
        }
    }
}