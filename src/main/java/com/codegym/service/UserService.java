package com.codegym.service;

import com.codegym.dao.UserDao;
import com.codegym.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("userService")
@Component
public class UserService {
    @Autowired
    UserDao userDao;

    public boolean isUsers(String username, String password) {
        Users users = userDao.findByUserName(username);
        return users != null && users.getPassword().equals(password);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
