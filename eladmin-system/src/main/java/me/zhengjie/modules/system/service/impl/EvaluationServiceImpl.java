package me.zhengjie.modules.system.service.impl;

import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.Evaluation;
import me.zhengjie.modules.system.service.EvaluationService;
import me.zhengjie.modules.system.service.mapper.EvaluationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EvaluationServiceImpl extends CommonServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {

}
