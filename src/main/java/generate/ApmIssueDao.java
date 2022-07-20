package generate;

import generate.ApmIssue;

public interface ApmIssueDao {
    int deleteByPrimaryKey(Long id);

    int insert(ApmIssue record);

    int insertSelective(ApmIssue record);

    ApmIssue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApmIssue record);

    int updateByPrimaryKey(ApmIssue record);
}