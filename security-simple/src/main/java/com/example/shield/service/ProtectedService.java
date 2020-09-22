package com.example.shield.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@Service
public class ProtectedService {

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_STAFF"})
    public String secured() {
        //超级管理员与普通管理员都可以调用
        return "administrator,staff";
    }

    @PreAuthorize("hasAuthority('READ_AUTHORITY') AND hasAuthority('WRITE_AUTHORITY')")
    public String preAuthorize() {
        //在方法执行前进行权限验证
        return "hey u.";
    }

    @PostAuthorize("hasRole('ADMINISTRATOR') OR hasRole('STAFF')")
    public String postAuthorize() {
        //在方法执行后进行权限验证
        return "Oh,you got it.";
    }

    @DenyAll
    public String deny() {
        //所有访客都不可调用
        return "No entry.";
    }

    @PermitAll
    public String permitAll() {
        //所有访客都可以调用
        return "Be my guest.";
    }

    @RolesAllowed({"ADMINISTRATOR", "STAFF"})
    public String rolesAllowed() {
        //超级管理员与普通管理员都可以调用
        return "Top Secret.";
    }

}
