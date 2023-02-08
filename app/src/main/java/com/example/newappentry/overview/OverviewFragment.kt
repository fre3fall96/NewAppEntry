package com.example.newappentry.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentOverviewBinding
import com.example.newappentry.network.ButtonList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(){

    //used to initialise the viewmodel
    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //this binds the code to the view
        val binding = FragmentOverviewBinding.inflate(inflater)

        //used for observing lifedata with data binding
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.newsLayout.adapter = NewsAdapter{
                article, position ->
            //Log.i("SSS", " "+position + " " + article.title)
            // create a new NewsDetail Fragment
            val newsDetail = NewsDetailFragment()
            //add arguments to the bundle
            newsDetail.arguments = viewModel.prepareBundle(article)
            //omg this took so long to understand
            //this replace whatever that is inside overviewFragment with the new fragment
            this.activity?.supportFragmentManager?.beginTransaction()?.
            replace(R.id.overviewFragment, newsDetail)?.addToBackStack(null)?.commit()

        }

        binding.filterRecycleview.layoutManager = LinearLayoutManager(this.activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.filterRecycleview.adapter = FilterAdapter(requireActivity()!!.applicationContext, viewModel.fetchList())

        binding.searchBar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchArticle(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchArticle(query)
                return false
            }

        })

/*
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.searchArticle(s)
            }
        })

 */

        return binding.root
    }

}
