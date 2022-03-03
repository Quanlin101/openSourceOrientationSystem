//package com.lig.orientationSystem.domain;
//
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
////登陆的实体
//@Data
//public class LoginUser implements UserDetails {
//    private String userName;
//    private String password;
//    private Integer id;
//
//    //权限数据
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    //获取用户名
//    @Override
//    public String getUsername() {
//        return userName;
//    }
//
//    //账号是否过期
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    //账号是否被锁定
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    //是否被禁用
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
