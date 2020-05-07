package com.example.newsapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.App

import com.example.newsapplication.R
import com.example.newsapplication.adapter.NewsAdapter
import com.example.newsapplication.adapter.OnItemClickListener
import com.example.newsapplication.di.NewsViewModelFactory
import com.example.newsapplication.models.Article
import com.example.newsapplication.utils.Constants.Companion.SEARCH_INTERVAL
import com.example.newsapplication.utils.Resource
import com.example.newsapplication.view.HomeFragmentDirections.actionHomeFragmentToArticleFragment
import com.example.newsapplication.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), OnItemClickListener {
    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=view.findNavController()
        newsAdapter = NewsAdapter()
        newsAdapter.setOnItemClickListener(this)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)
        search_resultRV.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=newsAdapter
        }
        var job: Job? = null
        searchET.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_INTERVAL)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        newsViewModel.searchNewsByKeyword(editable.toString())
                    }
                }
            }

        }
        newsViewModel.SearchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        newsAdapter.diff.submitList(it.articles)
                        Log.e("msg"," search result retrived")
                    }
                }
                is Resource.Failure->{
                    response.msg?.let {
                        Log.e("msg",it)
                    }
                }

                is Resource.Loading->{
                    Log.e("msg"," result loading for data")

                }
            }
        })


    }

    override fun onClick(article: Article) {
        val action = SearchFragmentDirections.actionSearchFragmentToArticleFragment(article)
        navController.navigate(action)
    }


}
