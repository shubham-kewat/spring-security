package com.kewat.security.service;

import com.kewat.security.repository.UserRepository;
import com.kewat.security.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository  userRepository;

    //I used this when permissions were not ther
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
//        return User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .authorities(user.getRole())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        //Add role
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        //Addpermission
        if(user.getPermissions()!=null&&
            !user.getPermissions().isBlank()){
            Arrays.stream(user.getPermissions().split(","))
                    .forEach(permission->authorities.add(new SimpleGrantedAuthority(permission)));
        }
        System.out.println(authorities);
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
