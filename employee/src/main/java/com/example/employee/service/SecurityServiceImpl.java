package com.example.employee.service;

import com.example.employee.dao.EmployeeRepository;
import com.example.employee.model.EmployeeVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements  UserDetailsService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       // new org.springframework.security.core.userdetails.UserCache.removeUserFromCache(userName);
        EmployeeVO employeeVO = employeeRepository.findByUserName(userName);
        System.out.println("inside load by user name ");
        System.out.println(employeeVO.toString());
        if (employeeVO == null) throw new UsernameNotFoundException(userName);

       /* UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(employeeVO.getUserName(),employeeVO.getPassword());

        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        System.out.println(auth.toString());*/


        return new org.springframework.security.core.userdetails.User(employeeVO.getUserName(),employeeVO.getPassword(), AuthorityUtils.NO_AUTHORITIES);
    }

}