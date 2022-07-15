package generate;

import generate.CMenu;

public interface CMenuDao {
    int deleteByPrimaryKey(Long id);

    int insert(CMenu record);

    int insertSelective(CMenu record);

    CMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CMenu record);

    int updateByPrimaryKey(CMenu record);
}