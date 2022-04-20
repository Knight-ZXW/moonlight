package top.tangyh.lamp.authority.entity.auth;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.Entity;


/**
 * 角色权限关联表
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_role_permission")
@ApiModel(value = "Role", description = "角色")
@AllArgsConstructor
public class RolePermission extends Entity<Long> {
    private int permissionId;
    private int roleId;

}
