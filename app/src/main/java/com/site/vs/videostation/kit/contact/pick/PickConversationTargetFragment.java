package com.site.vs.videostation.kit.contact.pick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.site.vs.videostation.kit.contact.model.GroupValue;
import com.site.vs.videostation.kit.contact.pick.viewholder.PickGroupViewHolder;
import com.site.vs.videostation.kit.group.GroupListActivity;
import cn.wildfirechat.model.GroupInfo;

public class PickConversationTargetFragment extends PickContactFragment {
    private final static int REQUEST_CODE_PICK_GROUP = 100;
    private boolean pickGroupForResult;
    private boolean multiGroupMode;
    private OnGroupPickListener groupPickListener;

    /**
     * @param pickGroupForResult 为true时，点击group item不直接启动群会话；否则直接启动群会话
     * @param multiGroupMode     {@code pickGroupForResult} 为{@code true}时，才生效，表示群多选
     * @return
     */
    public static PickConversationTargetFragment newInstance(boolean pickGroupForResult, boolean multiGroupMode) {
        PickConversationTargetFragment fragment = new PickConversationTargetFragment();
        Bundle args = new Bundle();
        args.putBoolean("pickGroupForResult", pickGroupForResult);
        args.putBoolean("multiGroupMode", multiGroupMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            pickGroupForResult = args.getBoolean("pickGroupForResult", false);
            multiGroupMode = args.getBoolean("multiGroupMode", false);
        }
    }

    public void setOnGroupPickListener(OnGroupPickListener listener) {
        this.groupPickListener = listener;
    }

    @Override
    public void initHeaderViewHolders() {
        // 选择一个群
        addHeaderViewHolder(PickGroupViewHolder.class, new GroupValue());
    }

    @Override
    public void onHeaderClick(int index) {
        // 选择一个群
        Intent intent = new Intent(getActivity(), GroupListActivity.class);
        if (pickGroupForResult) {
            intent.putExtra(GroupListActivity.INTENT_FOR_RESULT, true);
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_GROUP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_GROUP && resultCode == Activity.RESULT_OK) {
            // TODO 多选
            ArrayList<GroupInfo> groupInfos = data.getParcelableArrayListExtra("groupInfos");
            if (groupPickListener != null) {
                groupPickListener.onGroupPicked(groupInfos);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public interface OnGroupPickListener {
        void onGroupPicked(List<GroupInfo> groupInfos);
    }
}
