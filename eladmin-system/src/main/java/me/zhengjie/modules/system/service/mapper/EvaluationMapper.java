package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.system.domain.Evaluation;
import me.zhengjie.modules.system.domain.RepairAndOss;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaluationMapper extends CommonMapper<EvaluationMapper, Evaluation> {
}
