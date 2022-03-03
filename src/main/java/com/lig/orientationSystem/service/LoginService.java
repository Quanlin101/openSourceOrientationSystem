package com.lig.orientationSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lig.orientationSystem.controller.dto.UserDTO;

public interface LoginService extends IService<UserDTO> {
    /***
     * 登陆接口
     * @param UserDTO 账号和密码
     * @return 返回token，用token获取资源
     */
}
