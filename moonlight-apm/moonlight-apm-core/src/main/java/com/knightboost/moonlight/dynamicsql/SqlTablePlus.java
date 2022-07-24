package com.knightboost.moonlight.dynamicsql;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import top.tangyh.basic.utils.CamelCaseUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public class SqlTablePlus extends SqlTable {
    private final Map<String, SqlColumn> columnMap = new HashMap<>();

    private final Map<String, List<SqlColumn>> columnGroups = new HashMap<>();

    protected SqlTablePlus(String tableName) {
        super(tableName);
    }

    protected SqlTablePlus(Supplier<String> tableNameSupplier) {
        super(tableNameSupplier);
    }

    protected SqlTablePlus(Supplier<Optional<String>> schemaSupplier, String tableName) {
        super(schemaSupplier, tableName);
    }

    protected SqlTablePlus(Supplier<Optional<String>> catalogSupplier, Supplier<Optional<String>> schemaSupplier, String tableName) {
        super(catalogSupplier, schemaSupplier, tableName);
    }

    private void initColumnMap() {
        Field[] declaredFields = getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getType().equals(SqlColumn.class)) {
                try {
                    SqlColumn value = (SqlColumn) declaredField.get(this);
                    columnMap.put(value.name(), value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    private void initColumnGroup() {
        Field[] declaredFields = getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.getType().equals(SqlColumn.class)) {
                continue;
            }

            ColumnGroup columnGroup = declaredField.getDeclaredAnnotation(ColumnGroup.class);
            String[] groups = columnGroup.value();
            for (String group : groups) {
                List<SqlColumn> sqlColumns = columnGroups.get(group);
                if (sqlColumns == null) {
                    sqlColumns = new ArrayList<>();
                    columnGroups.put(group, sqlColumns);
                }
                try {
                    sqlColumns.add((SqlColumn) declaredField.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> SqlColumn<T> columnOf(String name) {
        if (columnMap.size() == 0) {
            initColumnMap();
        }

        return columnMap.get(CamelCaseUtil.camelCaseToUnderLine(name));
    }

    public Collection<BasicColumn> getColumnsOfGroup(String group) {
        if (columnGroups.size() == 0) {
            initColumnGroup();
        }
        return ((Collection) columnGroups.get(group));
    }
}
