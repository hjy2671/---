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
package me.zhengjie.modules.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadInputException;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.service.VerifyService;
import me.zhengjie.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    @Value("${code.expiration}")
    private Long expiration;

    @Value("${sms.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${sms.signName}")
    private String signName;

    @Value("${sms.templateCode}")
    private String templateCode;

    @Value("${sms.domain}")
    String domain;

    private final RedisUtils redisUtils;


    @Transactional(rollbackFor = Exception.class)
    public void sendSms(String phone, String key) {

        if (!this.checkPhone(phone)) {
            throw new BadInputException("手机号码输入有误");
        }

        String redisKey = key + phone;
        String code = RandomUtil.randomNumbers(6);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);
        SendSmsResponse sms = null;

        try {
            Client client = this.createClient(accessKeyId, accessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam(JSONUtils.toJSONString(map));
            sms = client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            throw new BadRequestException("短信服务异常，请联系网站负责人");
        }


        if (sms == null || "OK".equals(sms.getBody().getCode())) {
            // 存入缓存
            if (!redisUtils.set(redisKey, code, expiration)) {
                throw new BadRequestException("服务异常，请联系网站负责人");
            }
        }

    }

    private Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = domain;
        return new Client(config);
    }

    public boolean checkPhone(String phone) {
        return Pattern.matches("^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$", phone);
    }

    @Override
    public void validated(String key, String code) {
        Object value = redisUtils.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new BadRequestException("无效验证码");
        } else {
            redisUtils.del(key);
        }
    }
}
