package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String drive = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	// DB입력
		private void gcn() {

			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(drive);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);

				System.out.println("[접속성공]");

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}




private void close() {
	// 5. 자원정리
	try {
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	} catch (SQLException e) {
		System.out.println("error:" + e);
	}
}

public int insert(UserVo userVo) {
	
	int count = 0;
	
	gcn();
	
	
	try {
		// 3. SQL문 준비 / 바인딩 / 실행
		
		/*INSERT INTO users
		VALUES (SEQ_NO.nextval, 'id', 'password', '이름', '성별' );*/
		
		String query = "";
		query += " INSERT INTO users ";
		query += " VALUES (SEQ_NO.nextval, ?, ?, ?, ?) ";

		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, userVo.getId());
		pstmt.setString(2, userVo.getPassword());
		pstmt.setString(3, userVo.getName());
		pstmt.setString(4, userVo.getGender());

		count = pstmt.executeUpdate(); //쿼리문 실행
		

		// 4.결과처리
		
		System.out.println(count + "건 저장되었습니다.");
	} catch (SQLException e) {
		System.out.println("error:" + e);
	}
	
	close();
	return count;
	
}

public UserVo getUser(String id, String password) {
	
	UserVo uservo = null;
	gcn();
	
	try {
		/*SELECT no,
       id,
       password,
       name,
       gender
       FROM users
       where id = 'aaa'
       and password = '1234';*/
		// 3. SQL문 준비 / 바인딩 / 실행
		
		String query = "";
		query += " SELECT no, ";
		query += "        name ";
		query += "        FROM users ";
		query += " where id = ? ";
		query += " and password = ? ";
		
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, password);

		rs = pstmt.executeQuery();

		// 4.결과처리
		while(rs.next()) {
			int no = rs.getInt("no");
			String name = rs.getString("name");
			
			uservo = new UserVo(no, name);
		}
	} catch (SQLException e) {
		System.out.println("error:" + e);
	}
	
	close();
	
	return uservo;
	
} public int update(UserVo uvo) {
	gcn();
	int count = 0;
	
	try {/*
		UPDATE users
		SET id = 'aaa', 
		password = '1234',
		name = '감귤',
		gender = 'male'
		WHERE no = 1 ;
		*/
		
		// 3. SQL문 준비 / 바인딩 / 실행
		String query = "";
		query += " UPDATE users ";
		query += " SET password = ?, ";
		query += "     name = ?, ";
		query += "     gender = ? ";
		query += " WHERE no = ? ";
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, uvo.getPassword());
		pstmt.setString(2, uvo.getName());
		pstmt.setString(3, uvo.getGender());
		pstmt.setInt(4, uvo.getNo());
		
		count = pstmt.executeUpdate();

		// 4.결과처리
		System.out.println(count + "건 수정되었습니다.");

	} catch (SQLException e) {
		System.out.println("error:" + e);
	}
	// 자원정리
	close();

	return count;
}
		
//사람 1명 가져오기

public UserVo getUser(int no) {
	UserVo uvo = null;
	gcn();
	
	try {
		/*
		SELECT id,
	           password,
	           name,
	           gender
	    FROM users
	    where no = 1;
	    */

		// 3. SQL문 준비 / 바인딩 / 실행
		String query = "";
		query += " SELECT no, ";
		query += "        id, ";
		query += "        password, ";
		query += "        name, ";
		query += "        gender ";
		query += " FROM users ";
		query += " where no = ? ";
		
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, no);
		
		rs=pstmt.executeQuery();
		
		// 4.결과처리
		
		while(rs.next()) {
			int NO = rs.getInt("no");
			String id = rs.getString("id");
			String password = rs.getString("password");
			String name = rs.getString("name");
			String gender = rs.getString("gender");
			
			uvo = new UserVo(NO, id, password, name, gender);
			
		}
	} catch (SQLException e) {
		System.out.println("error:" + e);
	}
	// 자원정리
	close();
	return uvo;
}

}