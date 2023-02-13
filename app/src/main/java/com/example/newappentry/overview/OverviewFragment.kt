package com.example.newappentry.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappentry.Constant
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.bottomsheet.BottomSheetDialog
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton


@AndroidEntryPoint
class OverviewFragment : Fragment() {

    fun initBottomSheet(view: View, dialog: BottomSheetDialog, binding: FragmentOverviewBinding) {
        val latestBT = view.findViewById<ThemedButton>(R.id.BT_sortbyLatest)
        val relBT = view.findViewById<ThemedButton>(R.id.BT_sortbyRelevancy)
        val popBT = view.findViewById<ThemedButton>(R.id.BT_sortbyPopularity)
        val saveBT = view.findViewById<Button>(R.id.BT_saveSelection)
        val resetBT = view.findViewById<Button>(R.id.BT_resetSelection)


        latestBT.setOnClickListener {
            viewModel.filterType = Constant.LATEST
        }
        relBT.setOnClickListener {
            viewModel.filterType = Constant.RELEVANCY
        }
        popBT.setOnClickListener {
            viewModel.filterType = Constant.POPULARITY
        }
        saveBT.setOnClickListener {
            viewModel.getNewsArticle(
                viewModel.currentSearch,
                viewModel.filterType,
                viewModel.getTodayDate()
            )
            binding.RVNewsListing.smoothScrollToPosition(0)
            dialog.dismiss()
            binding.BTFilter.setBackground(resources.getDrawable(R.drawable.button_design))

        }

        resetBT.setOnClickListener {
            viewModel.filterType = Constant.LATEST
            viewModel.getNewsArticle(
                viewModel.currentSearch,
                viewModel.filterType,
                viewModel.getTodayDate()
            )
            binding.RVNewsListing.smoothScrollToPosition(0)
            dialog.dismiss()
            binding.BTFilter.setBackground(resources.getDrawable(R.drawable.button_design))

        }
    }

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

        binding.dbOverviewViewModel = viewModel
        binding.RVNewsListing.adapter = NewsAdapter { article, position ->
            // create a new NewsDetail Fragment
            val newsDetail = NewsDetailFragment()
            //add arguments to the bundle
            newsDetail.arguments = viewModel.prepareBundle(article)
            //omg this took so long to understand
            //this replace whatever that is inside overviewFragment with the new fragment
            this.activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.overviewFragment, newsDetail)?.addToBackStack(null)?.commit()

        }

        binding.RVFilterButtonOptions.layoutManager = LinearLayoutManager(
            this.activity?.applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.RVFilterButtonOptions.adapter = FilterAdapter(viewModel.fetchList(), {
            viewModel.currentSearch = it
            viewModel.getNewsArticle(it, viewModel.filterType, viewModel.getTodayDate())
        })

        binding.SVSearchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchArticle(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchArticle(query)
                return false
            }

        })

        binding.BTFilter.setOnClickListener {
            binding.BTFilter.setBackground(resources.getDrawable(R.drawable.button_design_red))
            val dialog = BottomSheetDialog(this.requireContext())

            val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)

            initBottomSheet(view, dialog, binding)

            dialog.setContentView(view)

            dialog.show()

        }

        return binding.root
    }

}
