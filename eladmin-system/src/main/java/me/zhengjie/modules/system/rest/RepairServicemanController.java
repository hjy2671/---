package me.zhengjie.modules.system.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
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

    @Log("查询报修信息与维修人员关系列表")
    @ApiOperation(value = "查询报修信息与维修人员关系信息列表")
    @GetMapping
    public ResponseEntity<PageInfo<RepairServiceman>> queryAll(RepairServicemanCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(repairServicemanService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("修改保修信息与维修人员关系")
    @ApiOperation(value = "修改报修信息与维修人员关系")
    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.updateById(resource), HttpStatus.OK);
    }

    @Log("新增报修信息与维修人员关系")
    @ApiOperation(value = "新增报修信息与维修人员关系")
    @PostMapping
    public ResponseEntity<Boolean> add(@RequestBody RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.save(resource), HttpStatus.OK);
    }

    @Log("删除报修信息与维修人员关系")
    @ApiOperation(value = "删除故报修信息与维修人员关系")
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody Set<Long> ids){
        return new ResponseEntity<>(repairServicemanService.removeByIds(ids), HttpStatus.OK);
    }

    @Log("统计维修任务")
    @ApiOperation(value = "统计维修任务")
    @GetMapping("/statisticsTask")
    public ResponseEntity<Object> statistics(){
        return new ResponseEntity<>(repairServicemanService.statisticsTask(SecurityUtils.getCurrentUserId()), HttpStatus.OK);
    }

    @Log("接受任务")
    @ApiOperation(value = "接受任务")
    @PostMapping("/accept")
    public ResponseEntity<Object> accept(@RequestBody RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.accept(resource), HttpStatus.OK);
    }

    @Log("拒绝任务")
    @ApiOperation(value = "拒绝任务")
    @PostMapping("/refuse")
    public ResponseEntity<Object> refuse(@RequestBody RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.refuse(resource), HttpStatus.OK);
    }

    @Log("完成任务")
    @ApiOperation(value = "完成任务")
    @PostMapping("/finish")
    public ResponseEntity<Object> finish(@RequestBody RepairServiceman resource){
        return new ResponseEntity<>(repairServicemanService.finish(resource), HttpStatus.OK);
    }

    @Log("查看指派给我的为序任务")
    @ApiOperation(value = "查看指派给我的维修任务")
    @GetMapping("/findAssignToMe")
    public ResponseEntity<Object> findAssignToMe(){
        return new ResponseEntity<>(repairServicemanService.findAssignToMe(SecurityUtils.getCurrentUserId()), HttpStatus.OK);
    }

}
