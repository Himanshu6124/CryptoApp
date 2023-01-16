package com.himanshu.cryptoapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.himanshu.cryptoapp.R
import com.himanshu.cryptoapp.databinding.FragmentDetailsBinding
import com.himanshu.cryptoapp.models.CryptoCurrencyListItem


class DetailsFragment : Fragment() {

    private lateinit var binding :FragmentDetailsBinding
    private val item : DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)



        val data : CryptoCurrencyListItem = item.data!!

        setUpDetails(data)
        loadChart(data)
        setButtonOnClick(data)

        addToWatchList(data)


        return binding.root
    }



    var watchList : ArrayList<String>? = null
    var watchListIsChecked = false

    private fun addToWatchList(data: CryptoCurrencyListItem) {
        readData()
        watchListIsChecked = if(watchList!!.contains(data.symbol)){
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
            true
        }
        else
        {
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
            false

        }
        binding.addWatchlistButton.setOnClickListener {

            watchListIsChecked =
                if(!watchListIsChecked){
                    if(!watchList!!.contains(data.symbol)){

                        Toast.makeText(requireContext(),"Added to watch list",Toast.LENGTH_SHORT).show()
                        watchList!!.add((data.symbol))
                    }
                    storeData()
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
                    true
                }
            else
                {
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
                    Toast.makeText(requireContext(),"Removed from watch list",Toast.LENGTH_SHORT).show()
                    watchList!!.remove(data.symbol)
                    storeData()
                    false
                }
        }

    }

    private fun readData() {
        val sharedPreferences = requireContext().getSharedPreferences("watchlisting" ,Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlisting",ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type

        watchList = gson.fromJson(json,type)
    }

    private fun storeData()
    {
        val sharedPreferences = requireContext().getSharedPreferences("watchlisting" ,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson =Gson()
        val json = gson.toJson(watchList)
        editor.putString("watchlisting",json)
        editor.apply()

        Log.d("HP", watchList?.size.toString())


    }


    private fun setButtonOnClick(item: CryptoCurrencyListItem) {
        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fifteenMinute = binding.button5

        Log.i("FF","FSF")

        val clickListener = View.OnClickListener {
            when(it.id){
                fifteenMinute.id -> loadChartData(it,"15",item,oneDay,oneMonth,oneWeek,fourHour,oneHour)
                oneHour.id -> loadChartData(it,"1H",item,oneDay,oneMonth,oneWeek,fourHour,fifteenMinute)
                fourHour.id -> loadChartData(it,"4H",item,oneDay,oneMonth,oneWeek,oneHour,fifteenMinute)
                oneDay.id -> loadChartData(it,"1D",item,fifteenMinute,oneMonth,oneWeek,fourHour,oneHour)
                oneWeek.id -> loadChartData(it,"W",item,oneDay,oneMonth,fifteenMinute,fourHour,oneHour)
                oneMonth.id -> loadChartData(it,"M",item,oneDay,fifteenMinute,oneWeek,fourHour,oneHour)
            }
        }

        fifteenMinute.setOnClickListener ( clickListener )
        oneHour.setOnClickListener ( clickListener )
        fourHour.setOnClickListener  (clickListener )
        oneDay.setOnClickListener ( clickListener )
        oneMonth.setOnClickListener ( clickListener )
        oneWeek.setOnClickListener ( clickListener )

    }

    private fun loadChartData(
        it: View?,
        interval: String,
        item: CryptoCurrencyListItem,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {

//        Toast.makeText(requireContext(),"clicked",Toast.LENGTH_SHORT).show()

        it?.setBackgroundResource(R.drawable.top_gainers_bg)
        disableButtons(oneDay,oneMonth,oneWeek,fourHour,oneHour) // to change background of other buttons

        Log.i("JOD","FDS")


        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +item.symbol
                .toString() + "USD&interval="+interval+"&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )



    }

    private fun disableButtons(oneDay: AppCompatButton, oneMonth: AppCompatButton, oneWeek: AppCompatButton, fourHour: AppCompatButton, oneHour: AppCompatButton) {

        oneDay.background = null
        oneMonth.background = null
        oneWeek.background = null
        fourHour.background = null
        oneHour.background = null


    }

    private fun loadChart(item: CryptoCurrencyListItem) {
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +item.symbol
                .toString() + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )

    }

    private fun setUpDetails(data: CryptoCurrencyListItem) {

        binding.detailSymbolTextView.text = data.symbol

        Glide.with(requireContext())
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" +data.id+".png")
            .thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
            .into(binding.detailImageView)

        binding.detailPriceTextView.text = "${String.format("$%.4f",data.quotes!![0].price)} "

        if(data.quotes!![0].percentChange24h > 0){
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            binding.detailChangeTextView.text = "+ ${ String.format("%.02f",data.quotes[0].percentChange24h)} %"
        }
        else
        {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.red))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            binding.detailChangeTextView.text = "${ String.format("%.02f",data.quotes[0].percentChange24h)} %"
        }


    }

}