package com.example.newsapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.models.Article
import kotlinx.android.synthetic.main.single_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    lateinit var listener:OnItemClickListener

    class ArticleViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
       var view=LayoutInflater.from(parent.context).inflate(R.layout.single_news,parent,false)
        var holder=ArticleViewHolder(view)
        view.setOnClickListener {
            var adapterPosition=holder.adapterPosition
            if(adapterPosition != RecyclerView.NO_POSITION) {
                listener.onClick(diff.currentList[adapterPosition])
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
       return diff.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=diff.currentList[position]
        holder.itemView.apply {
        Glide.with(this).load(article.urlToImage).into(articleIV)
            titleTV.text = article.title
            descriptionTV.text = article.description


        }

    }
    private val diffUtilCallBack= object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
     val diff=AsyncListDiffer(this,diffUtilCallBack)

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener=listener
    }
}