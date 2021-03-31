package com.randhypi.githubuserapp.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.model.User
import com.randhypi.githubuserapp.databinding.ItemRowGithubBinding


class ListUserAdapter(): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val mData = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =  LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_github, viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(mData[position])


    }

    override fun getItemCount(): Int = mData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemRowGithubBinding.bind(itemView)
        fun bind(userItem: User) {
            with(itemView){
                binding.tvItemName.text = userItem.name

                Glide.with(itemView.context)
                    .load(userItem.avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(binding.imgItemPhoto)

                itemView.setOnClickListener { onItemClickCallback.onItemClicked(userItem) }
            }
        }
    }



}