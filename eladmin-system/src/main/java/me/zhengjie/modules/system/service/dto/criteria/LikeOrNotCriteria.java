package me.zhengjie.modules.system.service.dto.criteria;

import lombok.Data;
import me.zhengjie.annotation.Query;

@Data
public class LikeOrNotCriteria {

    @Query
    private String type;

}
