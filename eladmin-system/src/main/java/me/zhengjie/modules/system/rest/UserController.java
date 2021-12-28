/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.rest;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.config.RsaProperties;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.UserMyBatis;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.domain.vo.UserResetVo;
import me.zhengjie.modules.system.domain.vo.UserVo;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.dto.criteria.UserQueryCriteria;
import me.zhengjie.utils.*;
import me.zhengjie.utils.enums.CodeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserServiceMyBatis userServiceMyBatis;
    private final RoleService roleService;
    private final VerifyService verificationCodeService;
    private final RepairApplicationService repairApplicationService;
    private final RepairServicemanService repairServicemanService;

    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('user:list')")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> query(UserQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody User resources){
        checkLevel(resources);
        // 默认密码 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        userService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> update(@Validated(User.Update.class) @RequestBody User resources) throws Exception {
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(User.Update.class) @RequestBody User resources){
        if(!resources.getId().equals(SecurityUtils.getCurrentUserId())){
            throw new BadRequestException("不能修改他人资料");
        }
        userService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改用户：前台页面")
    @ApiOperation("修改用户：前台页面")
    @PutMapping(value = "/updateInfo")
    public ResponseEntity<Object> updateInfo(@RequestBody UserMyBatis resources){
        if (StringUtils.isEmpty(resources.getNickName()) || resources.getNickName().length() > 8 ) {
            throw new RuntimeException("昵称输入有误");
        }
        resources.setUserId(SecurityUtils.getCurrentUserId());
        userServiceMyBatis.updateById(resources);
        userService.delCaches(resources.getUserId(), SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel =  Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("角色权限不足，不能删除：" + userService.findById(id).getUsername());
            }
        }
        userService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updatePass(@RequestBody UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,passVo.getNewPass());
        UserDto user = userService.findByName(SecurityUtils.getCurrentUsername());
        if(!passwordEncoder.matches(oldPass, user.getPassword())){
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if(passwordEncoder.matches(newPass, user.getPassword())){
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(user.getUsername(),passwordEncoder.encode(newPass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("重置密码")
    @PostMapping(value = "/reset")
    @AnonymousAccess
    public ResponseEntity<Object> resetPass(@Validated @RequestBody UserResetVo userResetVo) {
        verificationCodeService.validated(CodeEnum.PHONE_RESET_PWD_CODE.getKey() + userResetVo.getPhone(), userResetVo.getCode());
        userService.updatePassByPhone(userResetVo.getPhone(), passwordEncoder.encode(userResetVo.getPassword()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam("avatar") MultipartFile avatar){
        return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    }

    @Log("修改手机号")
    @ApiOperation("修改手机号")
    @PostMapping(value = "/updatePhone/{code}")
    public ResponseEntity<Object> updatePhone(@PathVariable String code,@RequestBody User user) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,user.getPassword());
        UserDto userDto = userService.findByName(SecurityUtils.getCurrentUsername());
        if(!passwordEncoder.matches(password, userDto.getPassword())){
            throw new BadRequestException("密码错误");
        }
        if (userServiceMyBatis.list(new QueryWrapper<UserMyBatis>().eq("phone", user.getPhone())).size() != 0) {
            throw new RuntimeException("手机号已被绑定");
        }
        verificationCodeService.validated(CodeEnum.PHONE_RESET_PHONE_CODE.getKey() + user.getPhone(), code);
        userService.updatePhone(userDto.getUsername(), user.getPhone());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param resources /
     */
    private void checkLevel(User resources) {
        Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }

    @Log("注册用户")
    @ApiOperation("注册用户")
    @PostMapping("/register")
    @AnonymousAccess
    public ResponseEntity<Object> register(@Validated @RequestBody UserVo resources){
        if (!resources.getPassword().equals(resources.getRePassword())){
            throw new RuntimeException("两次密码不相同");
        }
        resources.setPassword(passwordEncoder.encode(resources.getPassword()));
        verificationCodeService.validated(CodeEnum.PHONE_REGISTER_CODE.getKey() + resources.getPhone(), resources.getCode());

        final User user = BeanUtil.copyProperties(resources, User.class);
        user.setRoles(new HashSet<Role>(){{add(new Role(){{setId(2L);}});}});
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("统计用户报修相关信息的统计")
    @ApiOperation("统计用户报修相关信息的统计")
    @GetMapping("/statistic")
    public ResponseEntity<Object> statistic(){
        return new ResponseEntity<>(repairApplicationService.statistics(), HttpStatus.OK);
    }

    @Log("查询维修人员")
    @ApiOperation("查询维修人员")
    @GetMapping("/findServiceman")
    public ResponseEntity<Object> findServiceman(){
        return new ResponseEntity<>(repairServicemanService.findUserByRole(4), HttpStatus.OK);
    }

}
