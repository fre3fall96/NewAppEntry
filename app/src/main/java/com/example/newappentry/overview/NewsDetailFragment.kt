package com.example.newappentry.overview

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import coil.load
import com.example.newappentry.Constant
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentNewsDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class NewsDetailFragment: Fragment() {
    //private lateinit var binding :FragmentNewsDetailsBinding
    private var binding : FragmentNewsDetailsBinding? = null

    fun shareContent(){
        val formattedText = "News: "+binding?.TVNewsContent?.text+" By : "+binding?.TVAuthorName?.text
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, formattedText)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    fun createHyperlink(
        text: String,
        phrase: String,
        phraseColor: Int,
        listener: View.OnClickListener
    ): SpannableString {
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = phraseColor      // you can use custom color
            }
            override fun onClick(view: View) {
                listener.onClick(view)
            }
        }
        val start = text.indexOf(phrase)
        val end = start + phrase.length
        spannableString.setSpan(
            clickableSpan,
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        //loading of bundle
        val newsTitle:TextView = fragmentBinding.TVTitle
        newsTitle.setText(this.arguments?.getString((Constant.TITLE)))
        var newsContentString:String? = this.arguments?.getString((Constant.CONTENT))
        val newsContent:TextView = fragmentBinding.TVNewsContent
        if(newsContentString?.length!!>200){
            newsContentString = newsContentString.substring(0,200) + " See more"
            val spannableContent = createHyperlink(newsContentString,"See more", Color.BLUE, View.OnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this.arguments?.getString(Constant.URL_TO_NEWS)))
                startActivity(browserIntent)})
            newsContent.movementMethod = LinkMovementMethod.getInstance()
            newsContent.setText(spannableContent, TextView.BufferType.SPANNABLE)
        }else{
            newsContentString = newsContentString + " See more"
            newsContent.setText(newsContentString)
        }
        val newsImage:ImageView = fragmentBinding.IVNewsImage
        val imgUrl = this.arguments?.getString((Constant.URL))?.toUri()
        if (imgUrl!=null){
            newsImage.load(imgUrl)
        }else{
            newsImage.setImageResource(R.drawable.ic_broken_image)
        }
        val newsAuthor:TextView = fragmentBinding.TVAuthorName
        if (this.arguments?.getString(Constant.AUTHOR) != null){
            newsAuthor.setText("Published by: "+this.arguments?.getString((Constant.AUTHOR)))
        }else{
            fragmentBinding.TVAuthorName.visibility = View.GONE
        }
        val dateTimeFormatting = this.arguments?.getString(Constant.PUBLISH)
        val newsPublishedAt:TextView = fragmentBinding.TVNewsDate
        newsPublishedAt.setText(dateTimeFormatting)

        val fab : FloatingActionButton = fragmentBinding.FABShareNews
        fab.imageTintList = ColorStateList.valueOf(Color.WHITE)

        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //updating textview
        binding?.dbNewsDetailFragment = this
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}