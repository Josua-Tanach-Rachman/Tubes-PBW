package com.example.tubes_pbw.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tubes_pbw.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthorizationAspect {

    @Autowired
    HttpSession session;

    
    @Before("@annotation(requiredRole)")
    public void checkAuthorization(JoinPoint joinPoint, RequiredRole requiredRole) throws Throwable{
        String[] roles = requiredRole.value();
        
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if(username == null){
            throw new RuntimeException("Pengguna belum login");
        }

        if (Arrays.asList(roles).contains("*")) {
            return;
        }

        if(!Arrays.asList(roles).contains(role)){
            throw new SecurityException("user is not in the role" + Arrays.toString(roles));
        }
    }
}
