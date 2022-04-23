package top.tangyh.lamp.authority.entity;

/*
 * Created by Knight-ZXW on 2022/4/21
 * email: nimdanoob@163.com
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.BaseEntity;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("ml_role")
@ApiModel(description = "角色")
@AllArgsConstructor
public class MlRole extends BaseEntity<Long> {

    private Long name;

    private Long code;

    private String remark;

    private boolean state;

    private boolean readOnly;

}
