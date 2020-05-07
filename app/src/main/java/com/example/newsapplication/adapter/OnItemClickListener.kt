package com.example.newsapplication.adapter

import com.example.newsapplication.models.Article

interface OnItemClickListener {
    fun onClick(article: Article)
}