import java.util.*;
import java.sql.*;

import javax.swing.*;

public class database {
	static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
	static String userName = "sa"; // 默认用户名
	static String userPwd = "123456"; // 密码
	public static Connection conn = null;

	public static void openDB() {
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			if (conn.isClosed()) {
				JOptionPane.showMessageDialog(null, "Database is closed!", "Error", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//插入
	public static void insert(int s1, String s2, String s3, String s4, int s5) throws SQLException {
		openDB();
		try {
			String sql = "insert into student values('" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "')";
			Statement statement = conn.createStatement();
			int a = statement.executeUpdate(sql);
			if (a == 1) {
				JOptionPane.showMessageDialog(null, "插入成功", "成功", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "插入失败", "失败", JOptionPane.INFORMATION_MESSAGE);
			closeDB();
		}
	}

	public static void update(int a1, String a2,int a3) throws SQLException {
		openDB();
		CallableStatement callableStatement = conn.prepareCall("{call update_student (?,?,?)}");
		callableStatement.setInt(1, a1);
		callableStatement.setString(2, a2);
		callableStatement.setInt(3, a3);
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.registerOutParameter(2, Types.INTEGER);
		callableStatement.registerOutParameter(3, Types.INTEGER);
		int a = callableStatement.executeUpdate();
		if (a == 1) {
			JOptionPane.showMessageDialog(null, "更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "更新失败", "失败", JOptionPane.INFORMATION_MESSAGE);
		}
		closeDB();
	}
//删除
	public static void delete(int id) throws SQLException {
		openDB();
		Statement stmt = conn.createStatement();
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			int a =stmt.executeUpdate("delete from score where student_id='" + id + "'");
			int b =stmt.executeUpdate("delete from student where id='" + id + "'");
			conn.commit();// 提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			if (a!=0&&b!=0) //删除学生和成绩
			{
				JOptionPane.showMessageDialog(null, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
			} 
			else//若学生不存在，删除失败
			{
				JOptionPane.showMessageDialog(null, "删除失败", "失败", JOptionPane.INFORMATION_MESSAGE);
			}
			stmt.close();
		} catch (Exception exc) {
			//若SQL语句执行异常，删除失败
			JOptionPane.showMessageDialog(null, "删除失败", "失败", JOptionPane.INFORMATION_MESSAGE);
			conn.rollback();// 回滚JDBC事务
			exc.printStackTrace();
			stmt.close();
		}
	}

	public static void closeDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	}
}

