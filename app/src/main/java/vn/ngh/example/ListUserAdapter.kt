package vn.ngh.example

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_sticky.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import vn.ngh.stickyitem.R
import vn.ngh.stickyitem.interfaces.StickyAdapter

/**
 * Created by Ngh
 */

class ListUserAdapter(mContext: Context, private val mUserList: List<User>?, currentUserIndex: Int = RecyclerView.NO_POSITION) : RecyclerView.Adapter<ListUserAdapter.ContentViewHolder>(), StickyAdapter<ListUserAdapter.StickyViewHolder> {

    private var currentUserIndex = RecyclerView.NO_POSITION
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var isUpdate = true

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class StickyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.currentUserIndex = currentUserIndex
        if (this.currentUserIndex == 0 && mUserList?.size == 1)
            this.currentUserIndex = RecyclerView.NO_POSITION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = mInflater.inflate(R.layout.item_user, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
        if (holder != null) {
            val stickyIndex = getStickyIndex()
            val pos = if (position >= stickyIndex && stickyIndex != RecyclerView.NO_POSITION)
                position + 1
            else
                position
            mUserList?.let {
                val user = it[pos]
                holder.itemView.textName.text = user.name
                holder.itemView.textEmail.text = user.email
                holder.itemView.textAvatar.text = position.toString()
            }
        }
    }

    override fun onCreateStickyViewHolder(parent: ViewGroup): StickyViewHolder {
        val view = mInflater.inflate(R.layout.item_sticky, parent, false)
        return StickyViewHolder(view)
    }

    override fun onBindStickyViewHolder(viewHolder: StickyViewHolder, position: Int) {
        if (currentUserIndex >= 0) {
            mUserList?.let {
                val user = it[currentUserIndex]
                viewHolder.itemView.textNameSticky.text = user.name
                viewHolder.itemView.textEmailSticky.text = user.email
                viewHolder.itemView.imageAvatar.setImageResource(user.idAvatar)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mUserList != null) {
            if (currentUserIndex >= 0 && mUserList.size > 1)
                mUserList.size - 1
            else
                mUserList.size
        } else
            0
    }

    override fun getStickyIndex(): Int {
        return currentUserIndex
    }

    override fun getDataCount(): Int {
        return itemCount
    }

    override fun getEnableUpdate(): Boolean {
        return isUpdate
    }

    override fun setEnableUpdate(enableUpdate: Boolean) {
        isUpdate = enableUpdate
    }
}
