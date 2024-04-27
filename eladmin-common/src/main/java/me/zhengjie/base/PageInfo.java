package me.zhengjie.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jinjin on 2020-09-22.
 */
@Data
@Builder
@Accessors(chain = true)
public class PageInfo<T> implements Serializable{
    @ApiModelProperty("总数量")
    private long totalElements;

    @ApiModelProperty("内容")
    private List<T> content;

    public PageInfo(long totalElements, List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public PageInfo() {
    }

    public static <T> PageInfo<T> of(IPage<T> page) {
        return new PageInfo<>(page.getTotal(), page.getRecords());
    }
}
