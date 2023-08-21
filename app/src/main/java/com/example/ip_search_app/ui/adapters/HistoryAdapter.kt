package com.example.ip_search_app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ip_search_app.databinding.SearchListItemBinding
import com.example.ip_search_app.models.SearchIpModel

class HistoryAdapter(): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private lateinit var context: Context
    class HistoryViewHolder(private val binding: SearchListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchIpModel) {
            binding.apply {
                ivScreenMap.clipToOutline = true
                tvIp.text = item.ip
                tvCountry.text = item.country
                tvCity.text = item.city
            }
        }
    }

    private val callback = object : DiffUtil.ItemCallback<SearchIpModel>() {
        override fun areItemsTheSame(oldItem: SearchIpModel, newItem: SearchIpModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchIpModel, newItem: SearchIpModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, callback)

    fun submitList(list: List<SearchIpModel>) = differ.submitList(list)
    fun getItem(position: Int) = differ.currentList[position]
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        context = parent.context
        return HistoryViewHolder(
            SearchListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun getItemCount(): Int = differ.currentList.size
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }
}