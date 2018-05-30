package vn.ngh.stickyitem.interfaces

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Ngh
 */

interface StickyAdapter<VH : RecyclerView.ViewHolder> {
    fun getStickyIndex(): Int

    fun getDataCount(): Int

    fun setEnableUpdate(enableUpdate: Boolean)
    fun getEnableUpdate(): Boolean

    fun onCreateStickyViewHolder(parent: ViewGroup): VH

    fun onBindStickyViewHolder(viewHolder: VH, position: Int)
}
