package com.ajinkya.weatherappkotlin.ui.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajinkya.weatherappkotlin.R
import com.ajinkya.weatherappkotlin.common.TimeAgo
import com.ajinkya.weatherappkotlin.interfaces.IRecyclerItemClickListener
import com.ajinkya.weatherappkotlin.persistence.model.City
import kotlinx.android.synthetic.main.item_city_list.view.*
import java.util.*

class CityAdapter(
    private val context: Context,
    private val listener: IRecyclerItemClickListener<City>
) : ListAdapter<City, CityAdapter.ViewHolder>(CityDiffCallback()) {

    private lateinit var mUnFilteredList: List<City>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city: City = getItem(position)

        holder.mItemView.textViewCityTitle.text = city.title
        holder.mItemView.textViewCityState.text = city.state
        holder.mItemView.textViewCityCountry.text = city.country
        val timeAgo: String? = TimeAgo.getTimeAgo(context, city.dateTime!!.toLong())
        holder.mItemView.textViewCityAddedDate.text = timeAgo
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mItemView = itemView
        private val mImageViewDeleteCity: ImageView =
            itemView.findViewById(R.id.imageViewDeleteCity)

        init {
            itemView.setOnClickListener {
                listener.onClickCity(it, getItem(adapterPosition))
            }
            mImageViewDeleteCity.setOnClickListener {
                listener.onClickDelete(getItem(adapterPosition))
            }
        }
    }

    fun modifyList(list: List<City>) {
        mUnFilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<City>()

        // perform the data filtering
        if (!query.isNullOrEmpty()) {
            list.addAll(mUnFilteredList.filter {
                it.title!!.toLowerCase(Locale.getDefault())
                    .contains(query.toString().toLowerCase(Locale.getDefault()))
            })
        } else {
            list.addAll(mUnFilteredList)
        }

        submitList(list)
    }

    class CityDiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

}
