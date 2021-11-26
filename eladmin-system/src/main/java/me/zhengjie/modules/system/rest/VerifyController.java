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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.VerifyService;
import me.zhengjie.utils.enums.CodeBiEnum;
import me.zhengjie.utils.enums.CodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
@Api(tags = "系统：验证码管理")
public class VerifyController {

    private final VerifyService verificationCodeService;

    @PostMapping(value = "/resetPhone")
    @ApiOperation("重置手机号，发送验证码")
    public ResponseEntity<Object> resetEmail(@RequestParam String phone){
        verificationCodeService.sendSms(phone, CodeEnum.PHONE_RESET_PHONE_CODE.getKey());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/phone/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public ResponseEntity<Object> resetPass(@RequestParam String phone){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public ResponseEntity<Object> validated(@RequestParam String phone, @RequestParam String code, @RequestParam Integer codeBi){
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)){
            case ONE:
                verificationCodeService.validated(CodeEnum.PHONE_RESET_PHONE_CODE.getKey() + phone,code);
                break;
            case TWO:
                verificationCodeService.validated(CodeEnum.PHONE_RESET_PWD_CODE.getKey() + phone,code);
                break;
            default:
                break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
