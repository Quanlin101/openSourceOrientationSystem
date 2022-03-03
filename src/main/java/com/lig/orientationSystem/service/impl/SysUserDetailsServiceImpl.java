//package com.lig.orientationSystem.service.impl;
//
//import com.lig.orientationSystem.dao.InterviewerMapper;
//import com.lig.orientationSystem.dao.UserMapper;
//import com.lig.orientationSystem.domain.LoginUser;
//import com.lig.orientationSystem.domain.MethodPassWrapper;
//import com.lig.orientationSystem.service.LoginService;
//import com.lig.orientationSystem.until.token.TokenUtil;
//import com.lig.orientationSystem.vo.LoginVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SysUserDetailsServiceImpl implements LoginService {
//    //返回值包裹实体
//    MethodPassWrapper methodPassWrapper;
//
//    @Autowired
//    InterviewerMapper interviewerMapper;
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private TokenUtil tokenUtil;
//
//    @Value("${jwt.tokenHead}")
//    private String tokenHead;
//
//
//    /***
//     * 登陆逻辑实现
//     * @param loginVo 账号和密码
//     * @return
//     */
//    public UserDetails login(LoginVo loginVo){
//        LoginUser user = userMapper.getUserByUsername(loginVo.getUsername());
//        //如果用户查不到，返回null，由security的provider抛异常
//        if (user == null || !passwordEncoder.matches(loginVo.getPassword(), user.getPassword())){
//            return null;
//        }
//        UserDetails userDetails;
////        //设置token
////        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
////        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////        //jwt令牌生成token
////        String token = tokenUtil.generateToken(userDetails);
////        Map<String, String> map = new HashMap<>(2);
////        map.put("tokenHead", tokenHead);
////        map.put("token", token);
////        methodPassWrapper.setData(map);
//        userDetails = User.withUsername(loginVo.getUsername()).password(loginVo.getPassword()).roles("administrator").build();
//        return userDetails;
//    }
//}
