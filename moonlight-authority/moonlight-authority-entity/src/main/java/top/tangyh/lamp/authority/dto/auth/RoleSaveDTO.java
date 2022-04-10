package top.tangyh.lamp.authority.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 角色
 * </p>
 *
 * @author zuihou
 * @since 2020-11-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "RoleSaveDTO", description = "角色")
public class RoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Size(max = 30, message = "名称长度不能超过30")
    private String name;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    @Size(max = 20, message = "编码长度不能超过20")
    private String code;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Size(max = 100, message = "描述长度不能超过100")
    private String describe;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Boolean state;
    /**
     * 角色类别;[10-功能角色 20-桌面角色 30-数据角色]
     */
    @ApiModelProperty(value = "角色类别")
    @Size(max = 2, message = "角色类别长度不能超过{max}")
    private String category;
    /**
     * 关联的组织
     */
    @ApiModelProperty(value = "关联的组织")
    private List<Long> orgList;
}
