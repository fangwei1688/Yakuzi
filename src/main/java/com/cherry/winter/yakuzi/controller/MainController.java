package com.cherry.winter.yakuzi.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import com.cherry.winter.yakuzi.dao.UserRepository;
import com.cherry.winter.yakuzi.model.User;
import com.cherry.winter.yakuzi.utils.ApplicationPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping(path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name
        , @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @RequestMapping(path = "/all")
    public @ResponseBody
    Page<User> getAllUsers(@Valid ApplicationPage applicationPage) {
        PageRequest pageRequest = applicationPage.toPageRequest();
        Page<User> userPage = userRepository.findAll(pageRequest);
        return userPage;
    }

    @RequestMapping(path = "/getUser")
    public @ResponseBody
    User getUser(@RequestParam String name) {
        return userRepository.findUserByName(name);
    }
}
