package vn.ngh.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import vn.ngh.stickyitem.R
import vn.ngh.stickyitem.decoration.StickyDecoration

class MainActivity : AppCompatActivity() {
    private var mAdapter: ListUserAdapter? = null
    private val mListUser = mutableListOf<User>()

    private var mStickyDecoration: StickyDecoration<ListUserAdapter.StickyViewHolder>? = null
    private var userStickyIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {

        for (i in 0..100) {
            mListUser.add(User("user " + i))
        }
        val stickyUser = User("Tran Thanh Nghia", "thanhnghiaglhn92@gmail.com", R.drawable.image_nghia)
        userStickyIndex = 30
        mListUser[userStickyIndex] = stickyUser
        mAdapter = ListUserAdapter(this, mListUser, userStickyIndex)
        mAdapter?.let {
            mStickyDecoration = StickyDecoration(it)
            mStickyDecoration?.setOnPostitionListener(object : StickyDecoration.OnPositionListener {
                override fun onSticky(pos: StickyDecoration.Position) {
                    Log.i("StickyItem", "pos: " + pos.toString())
                }
            })
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            listRecyclerView.layoutManager = linearLayoutManager
            listRecyclerView.setHasFixedSize(true)
            listRecyclerView.adapter = mAdapter
            listRecyclerView.addItemDecoration(mStickyDecoration)
        }

    }

}
