package vn.ngh.stickyitem.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Ngh on 7/13/2017.
 */

public interface StickyAdapter<VH extends RecyclerView.ViewHolder>  {
    int getStickyIndex();

    int getDataCount();

    boolean getEnableUpdate();

    void setEnableUpdate(boolean enableUpdate);

    VH onCreateStickyViewHolder(ViewGroup parent);

    void onBindStickyViewHolder(VH viewHolder, int position);
}
