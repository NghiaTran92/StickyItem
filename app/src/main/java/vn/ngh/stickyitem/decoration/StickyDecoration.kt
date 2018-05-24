package vn.ngh.stickyitem.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup

import vn.ngh.stickyitem.interfaces.StickyAdapter

/**
 * Created by Ngh
 */

open class StickyDecoration<T : ViewHolder>(private val adapter: StickyAdapter<T>) : RecyclerView.ItemDecoration() {
    private var viewHolder: ViewHolder? = null
    private val stickyRect: Rect
    private var mOnPositionListener: OnPositionListener? = null

    private var mCurrentPosition = Position.BOTTOM

    init {
        viewHolder = null
        stickyRect = Rect()
    }

    fun setOnPostitionListener(event: OnPositionListener) {
        this.mOnPositionListener = event
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(view)
        var headerHeight = 0
        if (position != RecyclerView.NO_POSITION && adapter.getStickyIndex() >= 0) {
            val header = getSticky(parent, position)?.itemView
            header?.let {
                headerHeight = it.height
                if (position == adapter.getStickyIndex()) {
                    outRect.set(0, headerHeight, 0, 0)
                    return
                }
                if (position >= adapter.getDataCount() - 1 && mCurrentPosition == Position.BOTTOM) {
                    outRect.set(0, 0, 0, headerHeight)
                    return
                }
            }
        }
        outRect.set(0, 0, 0, 0)
    }

    private fun getSticky(parent: RecyclerView, position: Int): RecyclerView.ViewHolder? {
        val key = adapter.getStickyIndex()

        if (key >= 0) {
            if (!adapter.getEnableUpdate()) {
                return viewHolder
            } else {
                val holder = adapter.onCreateStickyViewHolder(parent)
                val sticky = holder.itemView

                adapter.onBindStickyViewHolder(holder, position)
                val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
                val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

                val childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                        parent.paddingLeft + parent.paddingRight, sticky.layoutParams.width)
                val childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                        parent.paddingTop + parent.paddingBottom, sticky.layoutParams.height)

                sticky.measure(childWidth, childHeight)
                sticky.layout(0, 0, sticky.measuredWidth, sticky.measuredHeight)

                // save cache
                viewHolder = holder

                // update done so don't need create view any more
                adapter.setEnableUpdate(false)

                return holder
            }
        }
        return null
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val count = parent.childCount

        val lastVisibleItemPosition = (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        val firstVisiblePosition = (parent.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        val paddingTop = parent.paddingTop
        val paddingBottom = parent.paddingBottom

        val indexSticky = adapter.getStickyIndex()

        if (indexSticky >= 0) {
            for (layoutPos in 0 until count) {
                val child = parent.getChildAt(layoutPos)

                val adapterPos = parent.getChildAdapterPosition(child)

                if (adapterPos != RecyclerView.NO_POSITION) {
                    val header = getSticky(parent, adapterPos)?.itemView
                    c.save()
                    val left = child.left
                    var top = 0
                    if (indexSticky in firstVisiblePosition..lastVisibleItemPosition) {
                        if (adapterPos == indexSticky) {
                            top = getStickyTop(parent, child)
                            setPosition(Position.IN_LIST)
                        } else {
                            continue
                        }
                    } else if (firstVisiblePosition > indexSticky) {
                        top = paddingTop
                        setPosition(Position.TOP)
                    } else {
                        setPosition(Position.BOTTOM)
                        if (header != null) {
                            top = parent.height - header.height - paddingBottom
                        }
                    }
                    c.translate(left.toFloat(), top.toFloat())
                    header?.draw(c)
                    c.restore()
                }
            }
        }
    }

    private fun setPosition(position: Position) {
        mCurrentPosition = position
        mOnPositionListener?.onSticky(position)
    }

    private fun getStickyTop(parent: RecyclerView, child: View): Int {
        val layoutManager = parent.layoutManager

        val paddingTop = parent.paddingTop
        val paddingBottom = parent.paddingBottom
        val maxBottom = parent.height - child.height - paddingBottom - paddingTop
        val pos = layoutManager.getDecoratedTop(child)
        var result = 0

        if (pos < 0) {
            result = 0
        } else if (pos > maxBottom) {
            result = maxBottom
        } else {
            result = pos
        }
        return result
    }

    fun clearStickyCache() {
        viewHolder = null
    }

    interface OnPositionListener {
        fun onSticky(pos: Position)
    }

    enum class Position {
        TOP, BOTTOM, IN_LIST
    }
}
