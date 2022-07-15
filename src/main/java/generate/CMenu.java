package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * c_menu
 * @author 
 */
@Data
public class CMenu implements Serializable {
    /**
     * ID
     */
    private Long id;

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

    /**
     * 创建人id
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}