package top.tangyh.lamp.authority.entity;

/*
 * Created by Knight-ZXW on 2022/4/23
 * email: nimdanoob@163.com
 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("ml_permission")
@ApiModel(description = "权限")
@AllArgsConstructor
public class MLPermission {
    private Long id;

    private Long name;

    private Long code;

    private String remark;

    private boolean state;

    private boolean readOnly;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    protected Long createdBy;
}
