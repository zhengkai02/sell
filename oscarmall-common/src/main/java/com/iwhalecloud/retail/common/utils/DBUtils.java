package com.iwhalecloud.retail.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class DBUtils {

    private static String driver = "com.mysql.jdbc.Driver";

    private DBUtils() {
    }

    static {
        try {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) {
            log.error("DBUtils forName exception = [{}]", e.getException());
        }
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeResource(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                log.error("DBUtils closeConnection exception = [{}]", e.getErrorCode());
            }
        }
        conn = null;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            }
            catch (SQLException e) {
                log.error("DBUtils closeStatement exception = [{}]", e.getErrorCode());
            }
        }
        st = null;
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                log.error("DBUtils closeResultSet exception = [{}]", e.getErrorCode());
            }
        }
        rs = null;
    }

    public static List query(String sql, String url, String user, String password) throws Exception {
        Connection conn = getConnection(url, user, password);
        // String sql = "select * from t_install_serv_provider_area ";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        List list = new ArrayList();

        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        while (rs.next()) {
            Map rowData = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i) == null ? "null" : rs.getObject(i));
            }
            list.add(rowData);
        }
        closeResource(conn, st, rs);
        return list;
    }

    public static Integer update(String sql, String url, String user, String password) throws Exception {
        Connection conn = getConnection(url, user, password);
        // String sql = "select * from t_install_serv_provider_area ";
        PreparedStatement st = conn.prepareStatement(sql);
        int rows = st.executeUpdate();
        closeResource(conn, st, null);
        return rows;
    }
}