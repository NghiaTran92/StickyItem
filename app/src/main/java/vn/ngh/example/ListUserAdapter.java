package vn.ngh.example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.ngh.stickyitem.R;
import vn.ngh.stickyitem.interfaces.StickyAdapter;

/**
 * Created by Ngh on 7/13/2017.
 */

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ContentViewHolder>
        implements StickyAdapter<ListUserAdapter.StickyViewHolder> {

    private int mCurrentUserIndex = RecyclerView.NO_POSITION;
    private List<User> mUserList;
    private LayoutInflater mInflater;
    private Context mContext;
    private boolean isUpdate = true;

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        private TextView mTextEmail;
        private ImageView mImageAvatar;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextEmail = (TextView) itemView.findViewById(R.id.text_email);
            mImageAvatar = (ImageView) itemView.findViewById(R.id.image_avatar);
        }
    }

    static class StickyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        private TextView mTextEmail;
        private ImageView mImageAvatar;

        public StickyViewHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextEmail = (TextView) itemView.findViewById(R.id.text_email);
            mImageAvatar = (ImageView) itemView.findViewById(R.id.image_avatar);
        }
    }

    public ListUserAdapter(Context context, List<User> userList) {
        this(context, userList, RecyclerView.NO_POSITION);
    }

    public ListUserAdapter(Context context, List<User> userList, int currentUserIndex) {
        this.mUserList = userList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mCurrentUserIndex = currentUserIndex;
        if (mCurrentUserIndex == 0 && mUserList.size() == 1)
            this.mCurrentUserIndex = RecyclerView.NO_POSITION;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user, parent, false);
        ContentViewHolder viewHolder = new ContentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        if (holder != null) {
            int stickyIndex = getStickyIndex();
            int pos = position >= stickyIndex && stickyIndex != RecyclerView.NO_POSITION
                    ? position + 1 : position;
            User user = mUserList.get(pos);
            holder.mTextName.setText(user.getName());
            holder.mTextEmail.setText(user.getEmail());
            holder.mImageAvatar.setImageResource(user.getIdAvatar());
        }
    }

    @Override
    public StickyViewHolder onCreateStickyViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_user, parent, false);
        StickyViewHolder viewHolder = new StickyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindStickyViewHolder(StickyViewHolder viewHolder, int position) {
        if (mCurrentUserIndex >= 0 && viewHolder != null) {
            User user = mUserList.get(mCurrentUserIndex);
            viewHolder.mTextName.setText(user.getName());
            viewHolder.mTextEmail.setText(user.getEmail());
            viewHolder.mImageAvatar.setImageResource(user.getIdAvatar());
        }
    }

    @Override
    public int getItemCount() {
        if (mUserList != null) {
            if (mCurrentUserIndex >= 0 && mUserList.size() > 1) return mUserList.size() - 1;
            else return mUserList.size();
        } else return 0;
    }

    @Override
    public int getStickyIndex() {
        return mCurrentUserIndex;
    }

    @Override
    public int getDataCount() {
        return getItemCount();
    }

    @Override
    public boolean getEnableUpdate() {
        return isUpdate;
    }

    @Override
    public void setEnableUpdate(boolean enableUpdate) {
        isUpdate = enableUpdate;
    }

    public int getCurrentUserIndex() {
        return mCurrentUserIndex;
    }

    public void setCurrentUserIndex(int mCurrentUserIndex) {
        this.mCurrentUserIndex = mCurrentUserIndex;
    }
}
