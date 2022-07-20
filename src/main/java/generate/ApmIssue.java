package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * apm_issue
 * @author 
 */
@Data
public class ApmIssue implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 应用appKey
     */
    private String appKey;

    /**
     * 问题所属平台
     */
    private String platform;

    /**
     * 问题类型
     */
    private String issueType;

    /**
     * 子问题的类型
     */
    private String subIssueType;

    /**
     * 问题的详细描述
     */
    private String message;

    /**
     * 详细数据
     */
    private String data;

    /**
     * 聚合的hash值
     */
    private String hash;

    /**
     * 问题状态
     */
    private Integer state;

    /**
     * issue level
     */
    private Integer level;

    /**
     * 所属功能域Id
     */
    private Integer functionId;

    private Long assigneeUserId;

    /**
     * 问题解决时间
     */
    private Date resolvedAt;

    /**
     * 问题首次发生的App版本
     */
    private String firstSeenAppVersion;

    /**
     * 问题最后发生的App版本
     */
    private String lastSeenAppVersion;

    /**
     * 问题的首次发生时间
     */
    private Date firstSeen;

    /**
     * 问题的最后发生时间
     */
    private Date lastSeen;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 问题标题
     */
    private String title;

    private static final long serialVersionUID = 1L;
}