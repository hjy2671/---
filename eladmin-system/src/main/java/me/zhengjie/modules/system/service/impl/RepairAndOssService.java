package me.zhengjie.modules.system.service.impl;

import lombok.AllArgsConstructor;
import me.zhengjie.base.FileInfo;
import me.zhengjie.modules.system.domain.RepairAndOss;
import me.zhengjie.modules.system.service.mapper.RepairAndOssMapper;
import me.zhengjie.utils.enums.RepairAndOssEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hjy
 * @date 2024/4/6 16:57
 */
@Service
@AllArgsConstructor
public class RepairAndOssService {

    private final RepairAndOssMapper repairAndOssMapper;

    public boolean save(List<FileInfo> fileInfos, Long repairId) {
        List<RepairAndOss> list = fileInfos.stream().map(f -> {
            RepairAndOss repairAndOss = new RepairAndOss();
            repairAndOss.setRepairApplicationId(repairId);
            repairAndOss.setOssId(f.getOssId());
            repairAndOss.setType(RepairAndOssEnum.RECEIPT.code);
            return repairAndOss;
        }).collect(Collectors.toList());

        return repairAndOssMapper.insertBatch(list);
    }


}
