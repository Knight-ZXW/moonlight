package com.knightboost.moonlight.controller;

import com.knightboost.moonlight.common.ApiResult;
import com.knightboost.moonlight.dto.request.CreateProjectRequest;
import com.knightboost.moonlight.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @PostMapping("create")
    public ApiResult createProject(@RequestBody CreateProjectRequest createProjectRequest){
         projectService.createProject(createProjectRequest.getOrigination(),
                createProjectRequest.getProjectName(),
                createProjectRequest.getPlatform());
         return ApiResult.success();
    }
}
