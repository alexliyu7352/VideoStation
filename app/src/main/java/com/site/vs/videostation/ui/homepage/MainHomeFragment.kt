package com.site.vs.videostation.ui.homepage


import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.site.vs.videostation.R
import com.site.vs.videostation.base.BaseFragment
import com.site.vs.videostation.entity.DataBean
import com.site.vs.videostation.entity.HomePageEntity
import com.site.vs.videostation.entity.SlideListBean
import com.site.vs.videostation.http.LoadData
import com.site.vs.videostation.http.LoadingHelper
import com.site.vs.videostation.ui.category.CategoryActivity
import com.site.vs.videostation.ui.detail.view.DetailActivity
import com.site.vs.videostation.ui.search.SearchActivity
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

        ButterKnife.bind(this, view)
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

            var contentListener: View.OnClickListener = View.OnClickListener {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.ID, data.slide_list[bannerViewPager.currentItem].id)
                startActivity(intent)
            }


            override fun getView(layoutInflater: LayoutInflater, i: Int, slide: SlideListBean, view: View?, viewGroup: ViewGroup): View {
                var content = view
//                Logger.e("content " +  content)
                if (content == null) {
                    content = layoutInflater.inflate(R.layout.list_item_banner, viewGroup, false)
                }
                (content as FrescoImageView).setImageURI(slide.pic)



                content.setOnClickListener(contentListener)
                return content
            }
        }



        if (!_Lists.isEmpty(data.slide_list)) {
            topMessageTv.text = data.slide_list[0].name
        }

        bannerViewPager.addOnPageChangeListener(pagerListener)

        indicatorView!!._setViewPager(bannerViewPager)

        movieGridView.adapter = object : Lib_BaseAdapter<DataBean>(data.move_list.data) {
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

        teleplayGridView.adapter = object : Lib_BaseAdapter<DataBean>(data.tv_list.data) {
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

        varietyGridView.adapter = object : Lib_BaseAdapter<DataBean>(data.arts_list.data) {
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
        cartoonGridView.adapter = object : Lib_BaseAdapter<DataBean>(data.comic_list.data) {
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

    @OnClick(R.id.iv_search, R.id.iv_history)
    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_search -> startActivity(Intent(activity, SearchActivity::class.java))

        }
    }

    @OnClick(R.id.tv_movie, R.id.tv_arts, R.id.tv_tv, R.id.tv_comic, R.id.tv_more_movie, R.id.tv_more_tv, R.id.tv_more_comic, R.id.tv_more_arts)
    fun onTopTabClick(v: View) {
        if (data == null) {
            return
        }


        val intent = Intent(v.context, CategoryActivity::class.java)
        when (v.id) {


            R.id.tv_movie, R.id.tv_more_movie ->
                data?.let {
                    val moveId = it.category.move_id
                    intent.putExtra(CategoryActivity.EXTRA_STRING_ID, "$moveId")
                }

            R.id.tv_tv, R.id.tv_more_tv ->
                data?.let{
                    val tvId = it.category.tv_id
                    intent.putExtra(CategoryActivity.EXTRA_STRING_ID, "$tvId");
                }

            R.id.tv_comic, R.id.tv_more_comic ->
                data?.let {
                    val comicId =  it.category.comic_id
                    intent.putExtra(CategoryActivity.EXTRA_STRING_ID, "$comicId")
                }

            R.id.tv_arts, R.id.tv_more_arts ->
                data?.let {
                    val artsId = it.category.arts_id
                    intent.putExtra(CategoryActivity.EXTRA_STRING_ID, "$artsId")
                }

        }
        startActivity(intent)
    }


}

