package com.codegym.dao;

import com.codegym.model.Users;

public interface UserDao {
    public Users findByUserName(String username);
}
