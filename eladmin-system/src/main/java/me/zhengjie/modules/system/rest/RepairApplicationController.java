package me.zhengjie.modules.system.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/api/repair")
@RequiredArgsConstructor
@Api(tags = "故障报修信息管理")
public class RepairApplicationController {

    private final RepairApplicationService repairApplicationService;

    @Log("查询故障报修信息列表")
    @ApiOperation(value = "查询故障报修信息列表")
    @GetMapping
    @PreAuthorize("@el.check('repair:list')")
    public ResponseEntity<PageInfo<RepairApplicationDetailsDto>> queryAll(RepairApplicationCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(repairApplicationService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("修改故障报修信")
    @ApiOperation(value = "修改故障报修信")
    @PutMapping
    @PreAuthorize("@el.check('repair:edit')")
    public ResponseEntity<Boolean> update(RepairApplication resource){
        return new ResponseEntity<>(repairApplicationService.updateById(resource), HttpStatus.OK);
    }

    @Log("新增故障报修信")
    @ApiOperation(value = "新增故障报修信")
    @PostMapping
    @PreAuthorize("@el.check('repair:add')")
    public ResponseEntity<Boolean> add(RepairApplication resource){
        return new ResponseEntity<>(repairApplicationService.save(resource), HttpStatus.OK);
    }

    @Log("删除故障报修信")
    @ApiOperation(value = "删除故障报修信")
    @DeleteMapping
    @PreAuthorize("@el.check('repair:del')")
    public ResponseEntity<Boolean> delete(Set<Long> ids){
        return new ResponseEntity<>(repairApplicationService.removeByIds(ids), HttpStatus.OK);
    }

    @Log("点赞或点踩")
    @ApiOperation(value = "点赞或点踩")
    @GetMapping("/like")
    public ResponseEntity<Object> like(Long repairId, String type){
        repairApplicationService.like(repairId, type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("提交故障报修信")
    @ApiOperation(value = "提交故障报修信")
    @PostMapping("/commit")
    public ResponseEntity<Object> commit(RepairApplication resource){
        return new ResponseEntity<>(repairApplicationService.commit(resource), HttpStatus.OK);
    }

    @Log("撤回故障报修信")
    @ApiOperation(value = "撤回故障报修信")
    @GetMapping("/revoke")
    public ResponseEntity<Boolean> revoke(Long repairId){
        return new ResponseEntity<>(repairApplicationService.revoke(repairId), HttpStatus.OK);
    }

    @Log("设置评价")
    @ApiOperation(value = "设置评价")
    @GetMapping("/setEvaluation")
    public ResponseEntity<Boolean> setEvaluation(Long repairId, String grade){
        final RepairApplication application = new RepairApplication();
        application.setId(repairId);
        application.setGrade(grade);
        return new ResponseEntity<>(repairApplicationService.updateById(application), HttpStatus.OK);
    }

    @Log("指派任务")
    @ApiOperation(value = "指派任务")
    @GetMapping("/assign")
    public ResponseEntity<Boolean> assign(RepairServiceman resource){
        return new ResponseEntity<>(repairApplicationService.assign(resource), HttpStatus.OK);
    }


}
