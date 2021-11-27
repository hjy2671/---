package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.service.RepairApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/repair")
@RequiredArgsConstructor
@Api(tags = "故障报修信息管理")
public class RepairApplicationController {

    private final RepairApplicationService repairApplicationService;

    @ApiOperation(value = "查询故障报修信息列表")
    @GetMapping
    public ResponseEntity<List<RepairApplication>> list(){
        return new ResponseEntity<>(repairApplicationService.list(), HttpStatus.OK);
    }

}
