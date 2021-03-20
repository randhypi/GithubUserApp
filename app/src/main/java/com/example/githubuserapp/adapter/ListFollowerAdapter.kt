package com.example.githubuserapp.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ItemRowFollowingInDetailBinding
import com.example.githubuserapp.model.User
import com.example.githubuserapp.databinding.ItemRowGithubBinding
import com.example.githubuserapp.ui.HomeFragment


class ListFollowerAdapter(): RecyclerView.Adapter<ListFollowerAdapter.ListViewHolder>() {

    private val mData = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =  LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_follower_in_detail, viewGroup, false)
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