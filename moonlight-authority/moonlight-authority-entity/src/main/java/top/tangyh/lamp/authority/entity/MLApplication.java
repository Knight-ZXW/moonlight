package top.tangyh.lamp.authority.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.BaseEntity;

import javax.validation.constraints.Size;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/*
 * Created by Knight-ZXW on 2022/4/21
 * email: nimdanoob@163.com
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ml_application")
@ApiModel(description = "组织")
@AllArgsConstructor
public class MLApplication extends BaseEntity<Long> {
//
//    @ApiModelProperty(value = "应用id")
//    @TableField(condition = EQUAL)
//    @Excel(name = "应用id")
//    private Long id;


    @ApiModelProperty(value = "应用名称")
    @Size(max = 255, message = "简称长度不能超过255")
    @TableField(condition = LIKE)
    @Excel(name = "应用名称")
    private String appName;

    @ApiModelProperty(value = "应用名称")
    @Size(max = 255, message = "简称长度不能超过255")
    @TableField(condition = LIKE)
    @Excel(name = "应用中文名称")
    private String appCnName;

    @ApiModelProperty(value = "应用appKey")
    @Size(max = 32)
    @Excel(name = "应用appKey")
    private String appKey;

    @ApiModelProperty(value = "appSecret")
    @Size(max = 32)
    @Excel(name = "appSecret")
    private String appSecret;

}
