package com.example.newappentry.overview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

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


        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.searchArticle(s)
            }
        })

        return binding.root
    }

}
