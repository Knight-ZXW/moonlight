package com.knightboost.moonlight.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 
 * @TableName apm_log
 */
@TableName(value ="apm_log")
@Data
public class ApmLog implements Serializable {
    /**
     * 
     */
    private String eventId;

    /**
     * 
     */
    private Integer projectId;

    /**
     * 
     */
    private Integer groupId;

    /**
     * 
     */
    private Date timestamp;

    /**
     * 
     */
    private Integer deleted;

    /**
     * 
     */
    private Integer retentionDays;

    /**
     * 
     */
    private String platform;

    /**
     * 
     */
    private String message;

    /**
     * 
     */
    private String primaryHash;

    /**
     * 
     */
    private List<String> hierarchicalHashes;

    /**
     * 
     */
    private Date received;

    /**
     * 
     */
    private String searchMessage;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String ipAddress;

    /**
     * 
     */
    private String geoCountryCode;

    /**
     * 
     */
    private String geoRegion;

    /**
     * 
     */
    private String geoCity;

    /**
     * 
     */
    private String sdkName;

    /**
     * 
     */
    private String sdkVersion;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String version;

    /**
     * 
     */
    private Date messageTimestamp;

    /**
     * 
     */
    private String osBuild;

    /**
     * 
     */
    private String osKernelVersion;

    /**
     * 
     */
    private String deviceName;

    /**
     * 
     */
    private String deviceBrand;

    /**
     * 
     */
    private String deviceLocale;

    /**
     * 
     */
    private String deviceUuid;

    /**
     * 
     */
    private String deviceModelId;

    /**
     * 
     */
    private String deviceArch;

    /**
     * 
     */
    private Double deviceBatteryLevel;

    /**
     * 
     */
    private String deviceOrientation;

    /**
     * 
     */
    private Boolean deviceSimulator;

    /**
     * 
     */
    private Boolean deviceOnline;

    /**
     * 
     */
    private Boolean deviceCharging;

    /**
     * 
     */
    private String level;

    /**
     * 
     */
    private String logger;

    /**
     * 
     */
    private String serverName;

    /**
     * 
     */
    private String transaction;

    /**
     * 
     */
    private String environment;

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private String appDevice;

    /**
     * 
     */
    private String device;

    /**
     * 
     */
    private String deviceFamily;

    /**
     * 
     */
    private String runtime;

    /**
     * 
     */
    private String runtimeName;

    /**
     * 
     */
    private String browser;

    /**
     * 
     */
    private String browserName;

    /**
     * 
     */
    private String os;

    /**
     * 
     */
    private String osName;

    /**
     * 
     */
    private Boolean osRooted;

    /**
     * 
     */
    private Set<String> contextsKey;

    /**
     * 
     */
    private Set<String> contextsValue;

    /**
     * 
     */
    private String httpMethod;

    /**
     * 
     */
    private String httpReferer;

    /**
     * 
     */
    private List<String> exceptionStacksType;

    /**
     * 
     */
    private List<String> exceptionStacksValue;

    /**
     * 
     */
    private List<String> exceptionStacksMechanismType;

    /**
     * 
     */
    private List<Boolean> exceptionStacksMechanismHandled;

    /**
     * 
     */
    private List<String> exceptionFramesAbsPath;

    /**
     * 
     */
    private List<String> exceptionFramesFilename;

    /**
     * 
     */
    private List<String> exceptionFramesPackage;

    /**
     * 
     */
    private List<String> exceptionFramesModule;

    /**
     * 
     */
    private List<String> exceptionFramesFunction;

    /**
     * 
     */
    private List<Boolean> exceptionFramesInApp;

    /**
     * 
     */
    private List<Integer> exceptionFramesColno;

    /**
     * 
     */
    private List<Integer>  exceptionFramesLineno;

    /**
     * 
     */
    private List<Integer> exceptionFramesStackLevel;

    /**
     * 
     */
    private String culprit;

}