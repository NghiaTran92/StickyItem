package vn.ngh.example;

import java.util.ArrayList;
import java.util.List;

import vn.ngh.stickyitem.R;

/**
 * Created by Ngh on 7/15/2017.
 */

public class StoreUserList {
    private static StoreUserList instane = null;
    private List<User> userList;

    protected StoreUserList() {
        // Ex: Get from database
        userList = new ArrayList<>();
        userList.add(new User("Xuan Dung", R.drawable.image_xuan_dung));
        userList.add(new User("Nghia", "thanhnghiaglhn92@gmail.com", R.drawable.image_nghia));
        userList.add(new User("Cuong", R.drawable.image_cuong));
        userList.add(new User("Dai", R.drawable.image_dai));
        userList.add(new User("Dieu Linh", R.drawable.image_dieu_linh));
        userList.add(new User("Hung", R.drawable.image_hung));
        userList.add(new User("Kien", R.drawable.image_kien));
        userList.add(new User("Lu", R.drawable.image_lu));
        userList.add(new User("Nam Son", R.drawable.image_nam_son));
        userList.add(new User("Quy Hai", R.drawable.image_quy_hai));
        userList.add(new User("Thang", R.drawable.image_thang));
        userList.add(new User("Thanh", R.drawable.image_thanh));
        userList.add(new User("Trinh", R.drawable.image_trinh));
        userList.add(new User("Truc", R.drawable.image_truc));
        userList.add(new User("Tuan", R.drawable.image_tuan));
        userList.add(new User("Viet Anh", R.drawable.image_viet_anh));
        userList.add(new User("Xuan Duc", R.drawable.image_xuan_duc));
    }

    public static StoreUserList getInstane() {
        if (instane == null) {
            instane = new StoreUserList();
        }
        return instane;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
