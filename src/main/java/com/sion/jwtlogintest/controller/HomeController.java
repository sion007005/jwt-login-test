package com.sion.jwtlogintest.controller;

import com.sion.jwtlogintest.auth.PrincipalDetails;
import com.sion.jwtlogintest.model.User;
import com.sion.jwtlogintest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "hello, this is our main entrance.";
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @GetMapping("user")
    public String getUserTestPage(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());

        return "<h1>user</h1>";
    }

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("manager")
    public String getManagerTestPage() {
        return "manager";
    }

    // 어드민이 접근 가능
    @GetMapping("admin")
    public String getAdminTestPage(){
        return "admin";
    }

}
