package top.tangyh.lamp.authority.dto.application;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/*
 * Created by Knight-ZXW on 2022/4/23
 * email: nimdanoob@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CreateApplicationDTO", description = "创建应用")
public class CreateApplicationDTO {

    private String appName;

    private String appCnName;
}
