package top.tangyh.lamp.authority.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.SuperEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 用户
 * </p>
 *
 * @author zuihou
 * @since 2019-11-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "UserUpdatePasswordDTO", description = "用户")
public class UserUpdatePasswordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 密码
     */
    @ApiModelProperty(value = "当前密码")
//    @NotEmpty(message = "当前密码不能为空")
    @Size(max = 64, message = "当前密码长度不能超过64")
    private String oldPassword;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度不能小于6且超过64个字符")
    private String password;

    /**
     * 密码
     */
    @ApiModelProperty(value = "确认密码")
    @NotEmpty(message = "确认密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度不能小于6且超过64个字符")
    private String confirmPassword;

}
