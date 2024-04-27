package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.base.FileInfo;
import me.zhengjie.modules.system.domain.LikeOrNot;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper extends CommonMapper<FileMapper, FileInfo> {
}
