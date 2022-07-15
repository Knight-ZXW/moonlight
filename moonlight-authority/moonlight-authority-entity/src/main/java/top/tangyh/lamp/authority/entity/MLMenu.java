package top.tangyh.lamp.authority.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.TreeEntity;

/*
 * Created by Knight-ZXW on 2022/4/24
 * email: nimdanoob@163.com
 */
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ml_menu")
@ApiModel(description = "菜单")
@AllArgsConstructor
@Getter
@Setter
public class MLMenu extends TreeEntity<MLMenu,Long> {

    /**
     * 名称
     */
    private String label;

    /**
     * [20-菜单 60-数据]@Echo(api = DICTIONARY_ITEM_FEIGN_CLASS,dictType = DictionaryType.Tenant.RESOURCE_TYPE)
     */
    private String resourceType;

    /**
     * 树层级
     */
    private Integer treeGrade;

    /**
     * 树路径
     */
    private String treePath;

    /**
     * 描述
     */
    private String describe;

    /**
     * 通用菜单
     True表示无需分配所有人就可以访问的
     */
    private Boolean isGeneral;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 状态
     */
    private Boolean state;

    /**
     * 排序
     */
    private Integer sortValue;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 分组
     */
    private String group;

    /**
     * 数据范围;[01-全部 02-本单位及子级 03-本单位 04-本部门 05-本部门及子级 06-个人 07-自定义]
     */
    private String dataScope;

    /**
     * 实现类
     */
    private String customClass;

    /**
     * 是否默认
     */
    private Boolean isDef;

    /**
     * 父级菜单ID
     */
    private Long parentId;

    /**
     * 内置
     */
    private Boolean readonly;


}
