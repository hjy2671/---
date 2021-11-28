package me.zhengjie.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.PageInfo;

public class ConvertUtil {

    public static <T>  PageInfo<T> convertPage(IPage<T> page){
        return new PageInfo<T>(){{setContent(page.getRecords());setTotalElements(page.getTotal());}};
    }


}
