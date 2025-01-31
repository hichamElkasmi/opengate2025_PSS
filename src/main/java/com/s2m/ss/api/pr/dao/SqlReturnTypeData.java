package com.s2m.ss.api.pr.dao;
 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
 
import org.springframework.jdbc.core.SqlReturnType;
 
public class SqlReturnTypeData implements SqlReturnType {
 
    private Class<?> targetClass;
 
    private Map<String, Class<?>> auxiliaryTypes;
    public SqlReturnTypeData(Class<?> targetClass, Map<String, Class<?>> auxiliaryTypes) {
        this.targetClass = targetClass;
        this.auxiliaryTypes = auxiliaryTypes;
    }
 
    @Override
    public Object getTypeValue(CallableStatement cs, int paramIndex, int sqlType, String typeName) throws SQLException {
        Connection con = cs.getConnection();
        Map<String, Class<?>> typeMap = con.getTypeMap();
        typeMap.put(typeName, this.targetClass);
        typeMap.putAll(auxiliaryTypes);
        Object o = cs.getObject(paramIndex);
        return o;
    }
}