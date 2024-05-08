package me.zhengjie.modules.system.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.base.FileInfo;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.Evaluation;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.bo.RepairAssignBo;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.RepairSolvedVo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
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

    @Log("查询已发布的报修信息列表")
    @ApiOperation(value = "查询已发布的报修信息列表")
    @GetMapping("/published")
    public ResponseEntity<PageInfo<RepairApplicationVo>> getPublish(Pageable page){
        return new ResponseEntity<>(repairApplicationService.getPublish(page), HttpStatus.OK);
    }

    @Log("查询由我的报修")
    @ApiOperation(value = "查询由我的报修")
    @GetMapping("/provideByMe")
    public ResponseEntity<PageInfo<RepairApplicationVo>> getProvideByMe(Pageable pageable){
        return new ResponseEntity<>(repairApplicationService.getProvideByMe(SecurityUtils.getCurrentUserId(), pageable), HttpStatus.OK);
    }

    @Log("查询由我解决的报修任务")
    @ApiOperation(value = "查询由我解决的报修任务")
    @GetMapping("/resolveByMe")
    public ResponseEntity<PageInfo<RepairSolvedVo>> getResolveByMe(Pageable pageable){
        return new ResponseEntity<>(repairApplicationService.getResolveByMe(SecurityUtils.getCurrentUserId(), pageable), HttpStatus.OK);
    }

    @Log("查询现场照片")
    @ApiOperation(value = "查询现场照片")
    @GetMapping("/site-photos")
    public ResponseEntity<List<FileInfo>> getSitePhotos(Long repairId){
        return new ResponseEntity<>(repairApplicationService.getSitePhotos(repairId), HttpStatus.OK);
    }

    @Log("查询回执照片")
    @ApiOperation(value = "查询回执照片")
    @GetMapping("/result-photos")
    public ResponseEntity<List<FileInfo>> getResultPhotos(Long repairId){
        return new ResponseEntity<>(repairApplicationService.getResultPhotos(repairId), HttpStatus.OK);
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
    public ResponseEntity<Boolean> delete(@RequestBody Set<Long> ids){
        return new ResponseEntity<>(repairApplicationService.deleteAll(ids), HttpStatus.OK);
    }

    @Log("查询待处理故障报修信息列表")
    @ApiOperation(value = "查询待处理故障报修信息列表")
    @GetMapping("/pending")
    public ResponseEntity<PageInfo<RepairApplicationVo>> getPending(Pageable page){
        return new ResponseEntity<>(repairApplicationService.pendingList(SecurityUtils.getCurrentUserId(), page), HttpStatus.OK);
    }

    @Log("审核回退故障报修信息")
    @ApiOperation(value = "审核回退故障报修信息")
    @GetMapping("/rollback")
    public ResponseEntity<Void> rollback(Long id, String status){
        RepairApplication application = new RepairApplication();
        application.setId(id);
        application.setStatus(status);
        return repairApplicationService.rollback(application) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
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


    @Log("设置评价")
    @ApiOperation(value = "设置评价")
    @PostMapping("/comment")
    public ResponseEntity<Void> setComment(@RequestBody Evaluation evaluation){

        repairApplicationService.setComment(evaluation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("撤回故障报修信")
    @ApiOperation(value = "撤回故障报修信")
    @GetMapping("/revoke")
    public ResponseEntity<Boolean> revoke(String repairId){
        return new ResponseEntity<>(repairApplicationService.revoke(repairId), HttpStatus.OK);
    }

    @Log("指派任务")
    @ApiOperation(value = "指派任务")
    @PostMapping("/assign")
    public ResponseEntity<Boolean> assign(@RequestBody RepairAssignBo bo){
        return new ResponseEntity<>(repairApplicationService.assign(bo), HttpStatus.OK);
    }

    @Log("发布任务")
    @ApiOperation(value = "发布任务")
    @GetMapping("/publish")
    public ResponseEntity<Boolean> assign(Long repairId){
        RepairApplication application = new RepairApplication();
        application.setId(repairId);
        application.setStatus(RepairApplicationStatusEnum.PENDING.code);
        return new ResponseEntity<>(repairApplicationService.publish(application), HttpStatus.OK);
    }

    @Log("查询由我指派的任务")
    @ApiOperation(value = "查询由我指派的任务")
    @GetMapping("/assignByMe")
    public ResponseEntity<List<RepairApplicationVo>> findAssignByMe(){
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
