package com.knightboost.moonlight.service;

import com.knightboost.moonlight.domain.Project;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface ProjectService extends IService<Project> {

    void createProject(long organization, String name, String platform);
}
