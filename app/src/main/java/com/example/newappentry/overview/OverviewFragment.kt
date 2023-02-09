package com.example.newappentry.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentOverviewBinding
import com.example.newappentry.network.ButtonList
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.bottomsheet.BottomSheetDialog
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton


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
        binding.filterRecycleview.adapter = FilterAdapter(viewModel.fetchList(),{
            viewModel.currentSearch = it
            viewModel.getNewsArticle(it, viewModel.filterType, viewModel.getTodayDate())
        })

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


        binding.filterButton.setOnClickListener{
            println("hello")
            val dialog = BottomSheetDialog(this.requireContext())

            val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)

            val latestBT = view.findViewById<ThemedButton>(R.id.latestBT)
            val relBT = view.findViewById<ThemedButton>(R.id.relevancyBT)
            val popBT = view.findViewById<ThemedButton>(R.id.popularityBT)
            val saveBT = view.findViewById<Button>(R.id.saveBT)

            latestBT.setOnClickListener{
                viewModel.filterType = "publishedAt"
            }
            relBT.setOnClickListener{
                viewModel.filterType = "relevancy"
            }
            popBT.setOnClickListener{
                viewModel.filterType = "popularity"
            }
            saveBT.setOnClickListener{
                viewModel.getNewsArticle(viewModel.currentSearch, viewModel.filterType, viewModel.getTodayDate())
                dialog.dismiss()
            }

            dialog.setContentView(view)

            dialog.show()

        }


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
