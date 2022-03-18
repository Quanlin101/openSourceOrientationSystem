package com.lig.orientationSystem.until.handler;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.lig.orientationSystem.until.JWTUtils;
import com.lig.orientationSystem.until.error.AuthenException;
import com.lig.orientationSystem.until.token.JwtToken;
import com.lig.orientationSystem.until.token.PassToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

//JWT详解
//https://blog.csdn.net/weixin_34149796/article/details/89729573

/**
 * 验证授权拦截器
 */
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("authorization");
        //请求不到方法直接通过  ?
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //获取请求方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        System.out.println("handlerMethod:"+handlerMethod);
        Method method = handlerMethod.getMethod();
        System.out.println("method"+ method);

        //判断是否需要JWT验证(是否有PassToken注解)
        boolean isCheck = true;//默认为全局验证
        boolean option = false;
        if (method.isAnnotationPresent(PassToken.class)) {
            isCheck = false;
        } else if (method.isAnnotationPresent(JwtToken.class)) {
            JwtToken jwtToken = method.getAnnotation(JwtToken.class);
            isCheck = jwtToken.required();
            option = jwtToken.optional();
        }
        if (isCheck) {
            //执行验证
            if (token == null) {
                if (option)
                    return true;
                else {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = null;
                    JSONObject res = new JSONObject();
                    res.put("success", "false");
                    res.put("code", "4");
                    res.put("message", "未授权,请登录");
                    res.put("data", "null");
                    out = response.getWriter();
                    out.append(res.toString());
//                    throw new AuthenException("未授权,请登录");
                    return false;
                }
            }
//            System.out.println(token);
            Map<String, Claim> datas = JWTUtils.validateToken(token);
            if (datas == null){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                JSONObject res = new JSONObject();
                res.put("success", "false");
                res.put("code", "2");
                res.put("message", "token过期");
                res.put("data", "null");
                out = response.getWriter();
                out.append(res.toString());
                return false;
            }
//            System.out.println(datas);
            try {
                String UserId = datas.get("UserId").asString();
                request.setAttribute("UserId", UserId);
            } catch (Exception e) {
                e.printStackTrace();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                JSONObject res = new JSONObject();
                res.put("success", "false");
                res.put("code", "3");
                res.put("message", "token异常");
                res.put("data", "null");
                out = response.getWriter();
                out.append(res.toString());
                return false;
//                throw new AuthenException("Token异常");
            }
        }
        //not required
        return true;
    }
}
