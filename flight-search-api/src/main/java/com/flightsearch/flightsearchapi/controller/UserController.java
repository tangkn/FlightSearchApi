package com.flightsearch.flightsearchapi.controller;

import com.flightsearch.flightsearchapi.common.RestResponse;
import com.flightsearch.flightsearchapi.common.RestResponseUtils;
import com.flightsearch.flightsearchapi.entity.UserInfo;
import com.flightsearch.flightsearchapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/portal/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Create new user", description = "Returns created user info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public RestResponse<UserInfo> create(@RequestBody UserInfo userInfo) {
        return RestResponseUtils.success(userService.createUser(userInfo));
    }
}
