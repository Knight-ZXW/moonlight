package com.knightboost.moonlight.mapstruct;

import com.knightboost.moonlight.dto.AdminMenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.tangyh.lamp.authority.entity.auth.Menu;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */
@Mapper
public interface AdminMenuMap {
    AdminMenuMap mapper = Mappers.getMapper(AdminMenuMap.class);


    @Mapping(target = "menuName", source = "label")
    AdminMenuDTO menuToDto(Menu car);

    List<AdminMenuDTO> menusToDto(List<Menu> car);
}
