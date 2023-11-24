package com.himanshu.cryptoapp.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himanshu.cryptoapp.R
import com.himanshu.cryptoapp.databinding.TopCurrencyLayoutBinding
import com.himanshu.cryptoapp.fragments.HomeFragmentDirections
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem

class TopMarketAdapter(var context: Context,var list :List<CryptoCurrencyListItem>) : RecyclerView.Adapter<TopMarketAdapter.TopMarketviewhHolder>() {

    inner class TopMarketviewhHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding = TopCurrencyLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketviewhHolder {
        return TopMarketviewhHolder(LayoutInflater.from(context).inflate(R.layout.top_currency_layout,parent,false))
    }

    override fun onBindViewHolder(holder: TopMarketviewhHolder, position: Int) {
        val item = list[position]

        holder.binding.topCurrencyNameTextView.text = item.name
        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id +".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.topCurrencyImageView)

        if(item.quotes!![0].percentChange24h > 0){
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.topCurrencyChangeTextView.text = "+ ${ String.format("%.02f",item.quotes[0].percentChange24h)} %"
        }
        else
        {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.topCurrencyChangeTextView.text = "${ String.format("%.02f",item.quotes[0].percentChange24h)} %"
        }
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}