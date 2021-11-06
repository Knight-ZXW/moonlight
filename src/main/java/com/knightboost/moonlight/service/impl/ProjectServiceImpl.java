package com.knightboost.moonlight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knightboost.moonlight.domain.Project;
import com.knightboost.moonlight.service.ProjectService;
import com.knightboost.moonlight.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService{


    @Override
    public void createProject(long organization, String name, String platform){
        Project project = new Project();
        project.setOrganizationId(organization);
        project.setName(name);
        project.setPlatform(platform);
        project.setPublicKey(UUID.randomUUID().toString().replaceAll("-",""));
        getBaseMapper().insertSelective(project);
    }
}




