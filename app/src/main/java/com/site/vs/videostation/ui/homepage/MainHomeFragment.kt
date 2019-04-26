package com.site.vs.videostation.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.site.vs.videostation.R


import com.site.vs.videostation.base.BaseFragment
import com.site.vs.videostation.entity.DataBean
import com.site.vs.videostation.entity.HomePageEntity
import com.site.vs.videostation.entity.SlideListBean
import com.site.vs.videostation.http.LoadData
import com.site.vs.videostation.http.LoadingHelper
import com.site.vs.videostation.ui.detail.view.DetailActivity
import com.site.vs.videostation.widget.FrescoImageView
import com.zhusx.core.adapter.Lib_BaseAdapter
import com.zhusx.core.adapter.Lib_BasePagerAdapter
import com.zhusx.core.network.HttpRequest
import com.zhusx.core.network.HttpResult
import com.zhusx.core.utils._Lists
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 * Created by yangang on 2018/1/18.
 */

class MainHomeFragment : BaseFragment() {
    private var loadData: LoadData<HomePageEntity>? = null
    private var data: HomePageEntity? = null

    private var pagerListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            if (data != null)
                topMessageTv.text = data!!.slide_list[bannerViewPager.currentItem].name
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("content = $content")
        loadData = LoadData(LoadData.Api.Home, this)
        loadData!!._setOnLoadingListener(object : LoadingHelper<HomePageEntity>(content, loadData) {
            override fun __onComplete(httpRequest: HttpRequest<Any>, data: HttpResult<HomePageEntity>) {
                initData(data.data)
            }

        })
        loadData!!._loadData()
    }


    private fun initData(data: HomePageEntity) {
        content!!.visibility = View.VISIBLE
        this.data = data
        val lp = topLayout!!.layoutParams
        lp.height = _getFullScreenWidth() * 350 / 750
        lp.width = _getFullScreenWidth()
        topLayout!!.layoutParams = lp

        bannerViewPager.adapter = object : Lib_BasePagerAdapter<SlideListBean>(activity, data.slide_list) {
            override fun getView(layoutInflater: LayoutInflater, i: Int, slide: SlideListBean, view: View?, viewGroup: ViewGroup): View {
                var view = view
                if (view == null) {
                    view = FrescoImageView(layoutInflater.context)
                }
                (view as FrescoImageView).setImageURI(slide.pic)
                view.setOnClickListener(View.OnClickListener { v ->
                    val intent = Intent(v.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID, slide.id)
                    startActivity(intent)
                })
                return view
            }
        }

        if (!_Lists.isEmpty(data.slide_list)) {
            topMessageTv!!.text = data.slide_list[0].name
        }

        bannerViewPager.addOnPageChangeListener(pagerListener)

        indicatorView!!._setViewPager(bannerViewPager)

        gridView!!.adapter = object : Lib_BaseAdapter<DataBean>(data.move_list.data) {
            override fun getView(layoutInflater: LayoutInflater, tv: DataBean, i: Int, view: View?, viewGroup: ViewGroup): View {
                val holder = _getViewHolder(view, viewGroup, R.layout.list_item_movie)
                holder.setText(R.id.tv_message, if (TextUtils.isEmpty(tv.name)) "" else tv.name)
                holder.setText(R.id.tv_name, tv.name)
                setImageURI(holder.getView(R.id.iv_image), tv.pic)

                holder.rootView.setOnClickListener(View.OnClickListener { v ->
                    val intent = Intent(v.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID, tv.id)
                    startActivity(intent)
                })


                return holder.rootView
            }
        }

        gridView1!!.adapter = object : Lib_BaseAdapter<DataBean>(data.tv_list.data) {
            override fun getView(layoutInflater: LayoutInflater, tv: DataBean, i: Int, view: View?, viewGroup: ViewGroup): View {
                val holder = _getViewHolder(view, viewGroup, R.layout.list_item_movie)
                holder.setText(R.id.tv_message, if (TextUtils.isEmpty(tv.name)) "" else tv.name)
                holder.setText(R.id.tv_name, tv.name)
                setImageURI(holder.getView(R.id.iv_image), tv.pic)

                holder.rootView.setOnClickListener(View.OnClickListener { v ->
                    val intent = Intent(v.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID, tv.id)
                    startActivity(intent)
                })

                return holder.rootView
            }
        }

        gridView2!!.adapter = object : Lib_BaseAdapter<DataBean>(data.arts_list.data) {
            override fun getView(layoutInflater: LayoutInflater, tv: DataBean, i: Int, view: View?, viewGroup: ViewGroup): View {
                val holder = _getViewHolder(view, viewGroup, R.layout.list_item_movie)
                holder.setText(R.id.tv_message, if (TextUtils.isEmpty(tv.name)) "" else tv.name)
                holder.setText(R.id.tv_name, tv.name)
                setImageURI(holder.getView(R.id.iv_image), tv.pic)

                holder.rootView.setOnClickListener(View.OnClickListener { v ->
                    val intent = Intent(v.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID, tv.id)
                    startActivity(intent)
                })
                return holder.rootView
            }
        }
//
        gridView3!!.adapter = object : Lib_BaseAdapter<DataBean>(data.comic_list.data) {
            override fun getView(layoutInflater: LayoutInflater, tv: DataBean, i: Int, view: View?, viewGroup: ViewGroup): View {
                val holder = _getViewHolder(view, viewGroup, R.layout.list_item_movie)
                holder.setText(R.id.tv_message, if (TextUtils.isEmpty(tv.name)) "" else tv.name)
                holder.setText(R.id.tv_name, tv.name)
                setImageURI(holder.getView(R.id.iv_image), tv.pic)

                holder.rootView.setOnClickListener(View.OnClickListener { v ->
                    val intent = Intent(v.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID, tv.id)
                    startActivity(intent)
                })
                return holder.rootView
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerViewPager.removeOnPageChangeListener(pagerListener)
    }

}

