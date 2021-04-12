package com.randhypi.consumerapp.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.randhypi.consumerapp.R
import com.randhypi.consumerapp.databinding.ItemRowFollowingInDetailBinding
import com.randhypi.consumerapp.model.User


class ListFollowingAdapter(): RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>() {

    private val mData = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =  LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_following_in_detail, viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(mData[position])


    }

    override fun getItemCount(): Int = mData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemRowFollowingInDetailBinding.bind(itemView)
        fun bind(userItem: User) {
            with(itemView){
                binding.tvItemName.text = userItem.name

                Glide.with(itemView.context)
                    .load(userItem.avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(binding.imgItemPhoto)
            }
        }
    }



}