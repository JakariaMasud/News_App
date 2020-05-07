package com.example.newsapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.example.newsapplication.App

import com.example.newsapplication.R
import com.example.newsapplication.adapter.NewsAdapter
import com.example.newsapplication.di.NewsViewModelFactory
import com.example.newsapplication.models.Article
import com.example.newsapplication.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment() {
    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel= ViewModelProvider(this,newsViewModelFactory).get(NewsViewModel::class.java)
        val article= args.article
        web_view.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener{
         newsViewModel.insertArticle(article)
        }


    }

}
