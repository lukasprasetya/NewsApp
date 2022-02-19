package com.lupa.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_collapse.*
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_collapse)

        (this as AppCompatActivity).supportActionBar


        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z")
        val output: String = formatter.format(parser.parse(intent.getStringExtra(getString(R.string.publishedAt))))

        val requestOption = RequestOptions()
        requestOption.placeholder(R.color.teal_700)
        requestOption.error(R.color.teal_700)
        Glide.with(iv_news_detail).setDefaultRequestOptions(requestOption)
            .load(intent.getStringExtra(getString(R.string.image))).into(iv_news_detail)

        tv_title!!.text = intent.getStringExtra(getString(R.string.intent_title))
        tv_source_detail!!.text = intent.getStringExtra(getString(R.string.source))
        tv_date!!.text = output
        tv_expand.text ="Description"
        expandable_text!!.text = intent.getStringExtra(getString(R.string.description))


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}