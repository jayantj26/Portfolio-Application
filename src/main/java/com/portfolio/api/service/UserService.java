package com.portfolio.api.service;

import com.portfolio.api.dto.HoldingsDto;
import com.portfolio.api.entity.User;

import java.util.List;

public interface UserService {
    String saveUser(User user);
    List<User>getAllUser();

    User getUser(long userId);

    HoldingsDto getPortfolio(long userId);
}
