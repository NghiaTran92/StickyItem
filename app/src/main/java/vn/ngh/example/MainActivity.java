package vn.ngh.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.ngh.stickyitem.R;
import vn.ngh.stickyitem.decoration.StickyDecoration;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mListUserRecylerView;

    private ListUserAdapter mAdapter;
    private List<User> mListUser;

    private StickyDecoration mStickyDecoration;
    private int userStickyIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    protected void init() {
        mListUserRecylerView = (RecyclerView) findViewById(R.id.list_user);
        StoreUserList storeUserList = StoreUserList.getInstane();
        mListUser = storeUserList.getUserList();

        mAdapter = new ListUserAdapter(this, mListUser, userStickyIndex);
        mStickyDecoration = new StickyDecoration(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListUserRecylerView.setLayoutManager(linearLayoutManager);
        mListUserRecylerView.setHasFixedSize(true);
        mListUserRecylerView.setAdapter(mAdapter);
        mListUserRecylerView.addItemDecoration(mStickyDecoration);


    }

}
