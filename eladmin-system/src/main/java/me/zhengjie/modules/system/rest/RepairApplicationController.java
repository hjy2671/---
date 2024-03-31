package me.zhengjie.modules.system.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
    public ResponseEntity<PageInfo<RepairApplicationDetailsDto>> queryAll(RepairApplicationCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(repairApplicationService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询由我的报修")
    @ApiOperation(value = "查询由我的报修")
    @GetMapping("/provideByMe")
    public ResponseEntity<PageInfo<RepairApplicationVo>> getProvideByMe(Pageable pageable){
        return new ResponseEntity<>(repairApplicationService.getProvideByMe(SecurityUtils.getCurrentUserId(), pageable), HttpStatus.OK);
    }

    @Log("修改故障报修信")
    @ApiOperation(value = "修改故障报修信")
    @PutMapping
    @PreAuthorize("@el.check('repair:edit')")
    public ResponseEntity<Boolean> update(@RequestBody RepairApplication resource){
        return new ResponseEntity<>(repairApplicationService.updateById(resource), HttpStatus.OK);
    }

    @Log("新增故障报修信")
    @ApiOperation(value = "新增故障报修信")
    @PostMapping
    @PreAuthorize("@el.check('repair:add')")
    public ResponseEntity<Boolean> add(@RequestBody RepairApplication resource){
        return new ResponseEntity<>(repairApplicationService.save(resource), HttpStatus.OK);
    }

    @Log("删除故障报修信")
    @ApiOperation(value = "删除故障报修信")
    @DeleteMapping
    @PreAuthorize("@el.check('repair:del')")
    public ResponseEntity<Boolean> delete(@RequestBody Set<String> ids){
        return new ResponseEntity<>(repairApplicationService.deleteAll(ids), HttpStatus.OK);
    }

    @Log("查询待处理故障报修信息列表")
    @ApiOperation(value = "查询待处理故障报修信息列表")
    @GetMapping("/pending")
    public ResponseEntity<Object> getPending(){
        return new ResponseEntity<>(repairApplicationService.list(new QueryWrapper<RepairApplication>().eq("status", "0")), HttpStatus.OK);
    }

    @Log("报修信息统计")
    @ApiOperation(value = "报修信息统计")
    @GetMapping("/repairStatistics")
    public ResponseEntity<Object> getRepairStatistics(){
        return new ResponseEntity<>(repairApplicationService.getRepairStatistics(), HttpStatus.OK);
    }

    @Log("报修评价信息统计")
    @ApiOperation(value = "报修评价信息统计")
    @GetMapping("/evaluationStatistics")
    public ResponseEntity<Object> getEvaluationStatistics(){
        return new ResponseEntity<>(repairApplicationService.getEvaluationStatistics(), HttpStatus.OK);
    }


    @Log("点赞或点踩")
    @ApiOperation(value = "点赞或点踩")
    @GetMapping("/like")
    public ResponseEntity<Object> like(String repairId, String type){
        if (repairId == null || type == null){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        repairApplicationService.like(repairId, type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("撤回故障报修信")
    @ApiOperation(value = "撤回故障报修信")
    @GetMapping("/revoke")
    public ResponseEntity<Boolean> revoke(String repairId){
        return new ResponseEntity<>(repairApplicationService.revoke(repairId), HttpStatus.OK);
    }

    @Log("设置评价")
    @ApiOperation(value = "设置评价")
    @GetMapping("/setEvaluation")
    public ResponseEntity<Boolean> setEvaluation(Long repairId, String grade, String comment){
        final RepairApplication application = new RepairApplication();
        application.setId(repairId);
        return new ResponseEntity<>(repairApplicationService.updateById(application), HttpStatus.OK);
    }

    @Log("指派任务")
    @ApiOperation(value = "指派任务")
    @GetMapping("/assign")
    public ResponseEntity<Boolean> assign(RepairServiceman resource){
        return new ResponseEntity<>(repairApplicationService.assign(resource), HttpStatus.OK);
    }

    @Log("查询由我指派的任务")
    @ApiOperation(value = "查询由我指派的任务")
    @GetMapping("/assignByMe")
    public ResponseEntity<Object> findAssignByMe(){
        return new ResponseEntity<>(repairApplicationService.findAssignByMe(), HttpStatus.OK);
    }

    @Log("上传图片")
    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    @AnonymousAccess
    public ResponseEntity<Object> upload(@RequestParam("files") MultipartFile[] files) throws IOException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("提交故障报修信")
    @ApiOperation(value = "提交故障报修信")
    @PostMapping("/commit")
    public ResponseEntity<Object> commit(@RequestParam("files") MultipartFile[] files, RepairApplication info){
        return new ResponseEntity<>(repairApplicationService.commit(files, info), HttpStatus.OK);
    }
}
