package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.LikeOrNot;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.service.LikeOrNotService;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.LikeOrNotCriteria;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/api/like")
@RequiredArgsConstructor
@Api(tags = "点赞信息管理")
public class LikeOrNotController {

    private final LikeOrNotService likeOrNotService;

    @ApiOperation(value = "查询点赞信息列表")
    @GetMapping
    public ResponseEntity<PageInfo<LikeOrNot>> queryAll(LikeOrNotCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(likeOrNotService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "修改点赞信息")
    @PutMapping
    public ResponseEntity<Boolean> update(LikeOrNot resource){
        return new ResponseEntity<>(likeOrNotService.updateById(resource), HttpStatus.OK);
    }

    @ApiOperation(value = "新增点赞信息")
    @PostMapping
    public ResponseEntity<Boolean> add(LikeOrNot resource){
        return new ResponseEntity<>(likeOrNotService.save(resource), HttpStatus.OK);
    }

    @ApiOperation(value = "删除点赞信息")
    @DeleteMapping
    public ResponseEntity<Boolean> delete(Set<Long> ids){
        return new ResponseEntity<>(likeOrNotService.removeByIds(ids), HttpStatus.OK);
    }

}
