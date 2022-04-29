package com.zerdasoftware.instagramclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zerdasoftware.instagramclone.databinding.ActivityUploadBinding
import com.zerdasoftware.instagramclone.databinding.RecyclerRowBinding
import com.zerdasoftware.instagramclone.model.Post

class FeedRecyclerAdapter(private val postlist: ArrayList<Post>): RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding =  RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
       holder.binding.textViewUserEmail.text = postlist.get(position).email
       holder.binding.textViewComment.text = postlist.get(position).comment
        Picasso.get().load(postlist.get(position).downloadUrl).into(holder.binding.imageViewUserPost);
       //holder.binding.textViewPostDate.text = postlist.get(position).date.seconds.toString()
    }

    override fun getItemCount(): Int {
        return postlist.size
    }
}