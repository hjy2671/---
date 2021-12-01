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
package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 验证码业务场景对应的 Redis 中的 key
 * </p>
 * @author Zheng Jie
 * @date 2020-05-02
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {

    PHONE_RESET_PWD_CODE("phone_reset_pwd_code_", "通过手机号码重置密码"),
    PHONE_REGISTER_CODE("phone_register_code_", "注册发送验证码"),
    PHONE_RESET_PHONE_CODE("phone_reset_phone_code_", "通过手机号重置手机号");

    private final String key;
    private final String description;
}
