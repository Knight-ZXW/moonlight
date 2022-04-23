package top.tangyh.lamp.authority.entity;

/*
 * Created by Knight-ZXW on 2022/4/21
 * email: nimdanoob@163.com
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("ml_application_user")
@ApiModel(description = "应用用户关联")
@AllArgsConstructor
public class MLApplicationUserRel {

    private Long appId;

    private Long userId;

}
