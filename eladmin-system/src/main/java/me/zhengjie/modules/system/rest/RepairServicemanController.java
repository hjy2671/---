package me.zhengjie.modules.system.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.service.RepairServicemanService;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.modules.system.service.dto.criteria.RepairServicemanCriteria;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/api/repairServiceman")
@RequiredArgsConstructor
@Api(tags = "报修信息与维修人员关系管理")
public class RepairServicemanController {

    private final RepairServicemanService repairServicemanService;

    @ApiOperation(value = "查询报修信息与维修人员关系信息列表")
    @GetMapping
    public ResponseEntity<PageInfo<RepairServiceman>> queryAll(RepairServicemanCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(repairServicemanService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "修改报修信息与维修人员关系")
    @PutMapping
    public ResponseEntity<Boolean> update(RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.updateById(resource), HttpStatus.OK);
    }

    @ApiOperation(value = "新增报修信息与维修人员关系")
    @PostMapping
    public ResponseEntity<Boolean> add(RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.save(resource), HttpStatus.OK);
    }

    @ApiOperation(value = "删除故报修信息与维修人员关系")
    @DeleteMapping
    public ResponseEntity<Boolean> delete(Set<Long> ids){
        return new ResponseEntity<>(repairServicemanService.removeByIds(ids), HttpStatus.OK);
    }

    @ApiOperation(value = "统计维修任务")
    @GetMapping("/statisticsTask")
    public ResponseEntity<Object> statistics(){
        return new ResponseEntity<>(repairServicemanService.statisticsTask(SecurityUtils.getCurrentUserId()), HttpStatus.OK);
    }

    @ApiOperation(value = "接受任务")
    @GetMapping("/accept")
    public ResponseEntity<Object> accept(RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.accept(resource), HttpStatus.OK);
    }

    @ApiOperation(value = "拒绝任务")
    @GetMapping("/refuse")
    public ResponseEntity<Object> refuse(RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.refuse(resource), HttpStatus.OK);
    }

    @ApiOperation(value = "完成任务")
    @GetMapping("/finish")
    public ResponseEntity<Object> finish(RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.finish(resource), HttpStatus.OK);
    }

}
