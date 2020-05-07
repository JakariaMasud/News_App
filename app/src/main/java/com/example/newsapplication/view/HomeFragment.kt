package com.example.newsapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.App
import com.example.newsapplication.adapter.NewsAdapter
import com.example.newsapplication.R
import com.example.newsapplication.adapter.OnItemClickListener
import com.example.newsapplication.di.NewsViewModelFactory
import com.example.newsapplication.models.Article
import com.example.newsapplication.utils.Resource
import com.example.newsapplication.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(),OnItemClickListener {
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("msg","home fragment is called")

        navController=view.findNavController()
        newsViewModel=ViewModelProvider(this,newsViewModelFactory).get(NewsViewModel::class.java)
        newsAdapter= NewsAdapter()
        newsAdapter.setOnItemClickListener(this)
        breaking_newsRV.apply {
            layoutManager=LinearLayoutManager(activity)
            adapter=newsAdapter
        }
        newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        newsAdapter.diff.submitList(it.articles)
                        Log.e("msg"," data has been retrived")
                    }
                }

                is Resource.Failure->{
                    response.msg?.let {
                        Log.e("msg",it)
                    }
                }

                is Resource.Loading->{
                    Log.e("msg"," it is loading for data")

                }
            }
        })
        }
    override fun onClick(article: Article) {
       val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
        navController.navigate(action)

    }


}


