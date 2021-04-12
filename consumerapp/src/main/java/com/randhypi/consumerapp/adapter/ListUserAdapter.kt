package com.randhypi.consumerapp.adapter



import android.annotation.SuppressLint
import android.content.ContentResolver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.randhypi.consumerapp.R
import com.randhypi.consumerapp.databinding.ItemRowGithubBinding
import com.randhypi.consumerapp.model.User


class ListUserAdapter(): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    companion object{
         var TAG = ListUserAdapter::class.java.simpleName
    }
    private lateinit var contentResolver: ContentResolver
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
        @SuppressLint("ShowToast")
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