package top.tangyh.lamp.authority.controller.app;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.tangyh.basic.base.R;
import top.tangyh.lamp.authority.dto.application.CreateApplicationDTO;
import top.tangyh.lamp.authority.entity.MLApplication;
import top.tangyh.lamp.authority.service.ApplicationService;
import top.tangyh.lamp.authority.service.common.ApplicationServiceInterface;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/4/23
 * email: nimdanoob@163.com
 */
@RestController()
@RequestMapping("/app")
@Api(value = "ApplicationController", tags = "New Application")
public class ApplicationController {
    @Autowired
    ApplicationServiceInterface applicationService;

    @PostMapping("/create")
    public R<MLApplication> createApplication(@RequestBody CreateApplicationDTO createApplicationDTO){
        return applicationService.createApplication(createApplicationDTO);
    }

    @GetMapping("/myApplications")
    public R<List<MLApplication>> createApplication(){
        return applicationService.myApplications();
    }
}
