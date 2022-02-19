package com.lupa.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lupa.newsapp.Constant
import com.lupa.newsapp.R
import com.lupa.newsapp.model.Article
import kotlinx.android.synthetic.main.news_row.view.*

class NewsAdapter (

    private var listDataNews: MutableList<Article?>,
    private val context: Context?,
    private val listener: (Article) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == Constant.VIEW_TYPE_ITEM) {
                val v =
                    LayoutInflater.from(context).inflate(R.layout.news_row, parent, false)
                Item(v)
            } else {
                val v =
                    LayoutInflater.from(context).inflate(R.layout.layout_loading, parent, false)
                LoadingHolder(v)
            }
        }


        fun addData(listDataNews: MutableList<Article?>) {
            listDataNews.let { this.listDataNews.addAll(it) }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return listDataNews.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            context?.let {
                listDataNews[position]?.let { it1 ->
                    (holder as Item).bindItem(
                        it1,
                        listener, context
                    )
                }
            }
        }

        class Item(itemview: View) : RecyclerView.ViewHolder(itemview) {
            fun bindItem(
                dataNews: Article,
                listener: (Article) -> Unit,
                context: Context?
            ) {

                itemView.tv_title.text = dataNews.title
                itemView.tv_source.text = dataNews.source?.name
                itemView.tv_date.text= dataNews.publishedAt


                val requestOption = RequestOptions()
                requestOption.placeholder(R.color.teal_700)
                requestOption.error(R.color.teal_700)
                Glide.with(itemView.iv_image).setDefaultRequestOptions(requestOption)
                    .load(dataNews.urlToImage).into(itemView.iv_image)

                itemView.setOnClickListener {
                    listener(dataNews)
                }

            }
        }

        class LoadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}