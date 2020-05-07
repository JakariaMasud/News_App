package com.example.newsapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.newsapplication.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_saved.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SavedFragment : Fragment(), OnItemClickListener {
    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).applicationComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=view.findNavController()
        newsViewModel= ViewModelProvider(this,newsViewModelFactory).get(NewsViewModel::class.java)
        newsAdapter= NewsAdapter()
        newsAdapter.setOnItemClickListener(this)
        savedListRV.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=newsAdapter
        }
    newsViewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
        newsAdapter.diff.submitList(it)

    })

    }

    override fun onClick(article: Article) {
        val action = SavedFragmentDirections.actionSavedFragmentToArticleFragment(article)
        navController.navigate(action)

    }

}
