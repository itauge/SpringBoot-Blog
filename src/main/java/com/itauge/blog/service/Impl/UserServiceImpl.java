package com.itauge.blog.service.Impl;

import com.itauge.blog.dao.UserRepository;
import com.itauge.blog.entity.User;
import com.itauge.blog.service.UserService;
import com.itauge.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.getMd5(password));
        return user;
    }

    @Override
    public User checkByName(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
