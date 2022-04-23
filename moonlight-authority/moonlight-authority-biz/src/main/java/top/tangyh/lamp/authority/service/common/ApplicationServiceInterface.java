package top.tangyh.lamp.authority.service.common;

import top.tangyh.basic.base.R;
import top.tangyh.lamp.authority.dto.application.CreateApplicationDTO;
import top.tangyh.lamp.authority.dto.application.ListApplicationsDTO;
import top.tangyh.lamp.authority.entity.MLApplication;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/4/23
 * email: nimdanoob@163.com
 */
public interface ApplicationServiceInterface {

    public R<MLApplication> createApplication(CreateApplicationDTO createApplicationDTO);

    R<List<MLApplication>> myApplications();

    R<List<MLApplication>> listApplications(ListApplicationsDTO listApplicationsDTO);
}
