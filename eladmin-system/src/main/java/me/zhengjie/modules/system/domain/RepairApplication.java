package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_repair_application")
public class RepairApplication extends CommonEntity<RepairApplication> implements Serializable {

    @TableId(type= IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID", hidden = true)
    private String id;

    @ApiModelProperty(value = "故障详情")
    private String faultDetails;

    @ApiModelProperty(value = "故障位置")
    private String faultLocation;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "紧急程度")
    private String emergencyDegree;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "发现时间")
    private Date foundTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "提供者id")
    private Long providerId;

    @ApiModelProperty(value = "星级")
    private String grade = "0";

    @ApiModelProperty(value = "评价")
    private String evaluation;

    public boolean isNull(){
        return faultDetails == null && faultLocation == null;
    }

    @TableField(exist = false)
    private static final StringBuilder builder = new StringBuilder();

    public void setPicture(String picture, @Value("${server.port}") String port) throws UnknownHostException {
        if (picture != null){
            picture = builder.append("http://")
                    .append(InetAddress.getLocalHost()
                    .getHostAddress())
                    .append(":").append(port)
                    .append("/").append(picture).toString();
            builder.setLength(0);
        }
        this.picture = picture;
    }
}
