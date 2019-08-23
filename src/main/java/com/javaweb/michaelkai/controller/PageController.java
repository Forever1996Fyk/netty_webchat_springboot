package com.javaweb.michaelkai.controller;

import com.javaweb.michaelkai.common.vo.Result;
import com.javaweb.michaelkai.service.MyFriendsService;
import com.javaweb.michaelkai.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PageController
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/22 22:03
 * @Version 1.0
 **/
@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private MyFriendsService myFriendsService;
    @Autowired
    private UsersService usersService;
    @RequestMapping("/{userId}")
    public String index(Model model, @PathVariable("userId") String userId) {
        List<Map<String, Object>> list = getMaps(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping("/snapshot/{userId}")
    public String snapshot(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("userId", userId);
        //model.addAttribute("list", getMaps(userId));
        return "snapshot";
    }


    @GetMapping("/getIndex/{userId}")
    @ResponseBody
    public Result getIndex(@PathVariable("userId") String userId) {

        List<Map<String, Object>> list = getMaps(userId);

        return new Result(true, 200, "成功", list);
    }

    private List<Map<String, Object>> getMaps(@PathVariable("userId") String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("myUserId", userId);
        List<Map<String, Object>> myFriends = myFriendsService.getMyFriendss(map);

        List<Map<String, Object>> list = new ArrayList<>();

        for (Map<String, Object> myFriend : myFriends) {
            Map<String, Object> myUserId = usersService.getUsersById(myFriend.get("myFriendUserId").toString());
            list.add(myUserId);
        }

        return list;
    }
}
