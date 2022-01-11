package com.sion.jwtlogintest.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sion.jwtlogintest.auth.PrincipalDetails;
import com.sion.jwtlogintest.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// TODO 하드코딩 값들은 전부 properties로 분리
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter { // AuthenticationManager를 파라미터로 받아 로그인 로직 실행
    private final AuthenticationManager authenticationManager;

    // Authentication 객체를 만들어서 리턴 -> 의존 : AuthenticationManager
    // login 요청이 오면, 로그인 시도를 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);
//            }

            // request에 있는 username과 password를 파싱해서 자바 Object로 받기
            user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
            // 로그인 시도를 위한 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincapalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨
            // db에 있는 username과 password가 일치한다.
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // -> 로그인 성공 의미
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername());

           return authentication;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 완료되면 아래 함수가 실행된다.
    // 이 함수에서 jwt 토큰을 만들어서, 요청한 사용자에게 응답
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 이 정보를 가지고 jwt 토큰을 만든다.
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("jwt")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
        .withClaim("id", principalDetails.getUser().getId())
        .withClaim("username", principalDetails.getUser().getUsername())
        .sign(Algorithm.HMAC512("jwt"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
