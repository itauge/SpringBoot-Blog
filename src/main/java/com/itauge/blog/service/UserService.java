package com.itauge.blog.service;

import com.itauge.blog.entity.User;

public interface UserService {
    User checkUser(String username, String password);
    User checkByName(String username);
}
