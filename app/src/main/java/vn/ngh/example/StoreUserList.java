package vn.ngh.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ngh on 7/15/2017.
 */

public class StoreUserList {
    private static StoreUserList instane = null;
    public static final int MAX_SIZE = 200;
    private List<User> userList;

    protected StoreUserList() {
        // Ex: Get from database
        userList = new ArrayList<>();
        for (int i=0;i<MAX_SIZE;i++){
            userList.add(new User("name "+i));
        }
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
