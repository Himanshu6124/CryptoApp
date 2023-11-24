package com.himanshu.cryptoapp.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himanshu.cryptoapp.R
import com.himanshu.cryptoapp.databinding.CurrencyItemLayoutBinding
import com.himanshu.cryptoapp.fragments.HomeFragmentDirections
import com.himanshu.cryptoapp.fragments.MarketFragmentDirections
import com.himanshu.cryptoapp.fragments.WatchlistFragmentDirections
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem

class MarketAdapter(var context: Context, var list: List<CryptoCurrencyListItem>, var fragmentType: String) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>()  {

    inner class MarketViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        var binding = CurrencyItemLayoutBinding.bind(view)

    }

    fun updateData(dataItem : List<CryptoCurrencyListItem>)
    {
        list = dataItem
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        holder.binding.currencyPriceTextView.text = "${String.format("$%.02f",item.quotes!![0].price)} "


        Glide.with(context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id +".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" +item.id+".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        if(item.quotes!![0].percentChange24h > 0){
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeTextView.text = "+ ${ String.format("%.02f",item.quotes[0].percentChange24h)} %"
        }
        else
        {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.currencyChangeTextView.text = "${ String.format("%.02f",item.quotes[0].percentChange24h)} %"
        }

        holder.itemView.setOnClickListener {

            if(fragmentType == "Home")
            {
                findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
            }
            else if(fragmentType == "Market")
            {
                findNavController(it).navigate(MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item))
            }

            else if(fragmentType == "Watch")
            {
                findNavController(it).navigate(WatchlistFragmentDirections.actionWatchlistFragmentToDetailsFragment2(item))


            }


        }


    }

    override fun getItemCount(): Int {
           return list.size
    }

}