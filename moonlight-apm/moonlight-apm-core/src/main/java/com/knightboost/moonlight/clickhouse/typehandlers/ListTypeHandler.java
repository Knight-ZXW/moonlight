package com.knightboost.moonlight.clickhouse.typehandlers;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */

import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.ARRAY)
public class ListTypeHandler implements TypeHandler<List<?>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<?> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.ARRAY.TYPE_CODE);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
                        + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
                        + "Cause: " + e, e);
            }
        } else {
            try {
                ps.setArray(i, ps.getConnection().createArrayOf(jdbcType.name(), parameter.toArray()));
            } catch (Exception e) {
                throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType
                        + " . "
                        + "Try setting a different JdbcType for this parameter or a different configuration property. "
                        + "Cause: " + e, e);
            }
        }

    }

    @Override
    public List<?> getResult(ResultSet rs, String columnName) throws SQLException {
        List<?> result;
        try {
            Array array = rs.getArray(columnName);
            result = parse(array);
        } catch (Exception e) {
            throw new ResultMapException(
                    "Error attempting to get column '" + columnName + "' from result list.  Cause: " + e, e);
        }
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public List<?> getResult(ResultSet rs, int columnIndex) throws SQLException {
        List<?> result;
        try {
            Array array = rs.getArray(columnIndex);
            result = parse(array);
        } catch (Exception e) {
            throw new ResultMapException(
                    "Error attempting to get column #" + columnIndex + " from result list.  Cause: " + e, e);
        }
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public List<?> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        List<?> result;
        try {
            Array array = cs.getArray(columnIndex);
            result = parse(array);
        } catch (Exception e) {
            throw new ResultMapException(
                    "Error attempting to get column #" + columnIndex + " from callable statement.  Cause: " + e, e);
        }
        if (cs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }


    private List<?> parse(Array array) throws SQLException {

        List<?> result;

        final int baseType = array.getBaseType();
        if (baseType == Types.INTEGER) {
            result = Arrays.stream((long[]) array.getArray()).boxed().collect(Collectors.toList());
        } else if (baseType == Types.BIGINT) {
            result = Arrays.stream((long[]) array.getArray()).boxed().collect(Collectors.toList());
        } else if (baseType == Types.VARCHAR) {
            result = new ArrayList<>(Arrays.asList((String[]) array.getArray()));
        } else if (baseType == Types.DOUBLE) {
            result = Arrays.stream((double[]) array.getArray()).boxed().collect(Collectors.toList());
        } else if (baseType == Types.FLOAT) {
            result = Arrays.stream((double[]) array.getArray()).boxed().collect(Collectors.toList());
        } else {
            result = Collections.singletonList((Object[]) array.getArray());
        }
        return result;
    }

}

