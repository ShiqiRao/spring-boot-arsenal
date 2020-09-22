package com.example.shield.controller;

import com.example.shield.domain.R;
import com.example.shield.service.ProtectedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global")
public class GlobalMethodSecurityController {

    private final ProtectedService protectedService;

    @GetMapping("/secured")
    public R<String> secured() {
        return R.ok(protectedService.secured());
    }

    @GetMapping("/pre-authorize")
    public R<String> preAuthorize() {
        return R.ok(protectedService.preAuthorize());
    }

    @GetMapping("/post-authorize")
    public R<String> postAuthorize() {
        return R.ok(protectedService.postAuthorize());
    }

    @GetMapping("/deny-all")
    public R<String> deny() {
        return R.ok(protectedService.deny());
    }

    @GetMapping("/permit-all")
    public R<String> permitAll() {
        return R.ok(protectedService.permitAll());
    }

    @GetMapping("/roles-allowed")
    public R<String> rolesAllowed() {
        return R.ok(protectedService.rolesAllowed());
    }

}
