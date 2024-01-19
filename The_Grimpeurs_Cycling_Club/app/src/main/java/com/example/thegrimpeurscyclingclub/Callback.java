package com.example.thegrimpeurscyclingclub;

import com.example.thegrimpeurscyclingclub.data.users.User;

public interface Callback {
    void onFetched(Object obj);
    void onError(Exception e);
}