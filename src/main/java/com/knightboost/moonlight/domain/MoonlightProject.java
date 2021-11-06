package com.knightboost.moonlight.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * moonlight_project
 * @author 
 */
@Data
public class MoonlightProject implements Serializable {
    private Integer id;

    private String name;

    private Integer status;

    private String platform;

    private Integer organizationId;

    private Date createTime;

    private Date modifyTime;

    private String publicKey;

    private static final long serialVersionUID = 1L;
}