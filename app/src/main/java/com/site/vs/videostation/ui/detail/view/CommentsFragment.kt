package com.site.vs.videostation.ui.detail.view


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.site.vs.videostation.R
import com.site.vs.videostation.adapter.base.BaseAdapterHelper
import com.site.vs.videostation.adapter.base.CommRecyclerAdapter
import com.site.vs.videostation.base.BaseFragment
import com.site.vs.videostation.base.RecyclerViewHelper
import com.site.vs.videostation.entity.DetailEntity
import kotlinx.android.synthetic.main.fragment_comments.*
import java.util.*


/**
 * @author dxplay120
 * @date 2016/12/19
 */
class CommentsFragment : BaseFragment(), RecyclerViewHelper.LoadingAndRetryAdapter {
    internal var helper = RecyclerViewHelper<DetailEntity.Comment>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper.initViews(activity, this)
        gainRecyclerView()!!.layoutManager = LinearLayoutManager(context)


        val entity = arguments!!.getSerializable("data") as DetailEntity
        if (entity.comment_list != null && entity.comment_list!!.size > 0) {
            helper.initDataSuccess(entity.comment_list)
        } else {
            helper.initDataSuccess(ArrayList<DetailEntity.Comment>())
        }
    }

    override fun createAdapter(): CommRecyclerAdapter<*> {
        return object : CommRecyclerAdapter<DetailEntity.Comment>(context, R.layout.list_item_comment, ArrayList()) {
            override fun onUpdate(helper: BaseAdapterHelper, item: DetailEntity.Comment, position: Int) {
                helper.setText(R.id.tv_name, item.username)
                helper.setText(R.id.tv_date, item.creat_at)
                helper.setText(R.id.tv_content, item.content)
            }
        }
    }

    override fun gainRecyclerView(): RecyclerView? {
        return commentsRecyclerView
    }

    override fun onRefresh() {

    }
}
