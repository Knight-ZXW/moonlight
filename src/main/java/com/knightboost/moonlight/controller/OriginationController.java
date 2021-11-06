package com.knightboost.moonlight.controller;


import com.knightboost.moonlight.common.ApiResult;
import com.knightboost.moonlight.dto.request.CreateOrganizationRequest;
import com.knightboost.moonlight.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/organization")
public class OriginationController {

    @Autowired
    OrganizationService organizationService;


    @SuppressWarnings("rawtypes")
    @PostMapping("create")
    public ApiResult createOrigination(@RequestBody CreateOrganizationRequest createOriginationVO){
        organizationService.createOrganization(createOriginationVO.getName());
        return ApiResult.success();
    }

}
