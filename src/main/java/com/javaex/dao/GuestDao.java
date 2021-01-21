package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String drive = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// DB입력
	private void getConnection() {

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
	
	// 저장
	public int insert(GuestVo guestVo) {
		int count = 0;

		getConnection();

		try {// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_no.nextval, ?, ?, ?, sysdate) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 저장되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}

	// 수정
	public int update(GuestVo guestVo) {

		getConnection();
		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update guestbook ";
			query += " set name = ?, ";
			query += "     password = ?, ";
			query += "     content = ?, ";
			query += "     reg_date = sysdate ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			pstmt.setInt(4, guestVo.getNo());

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

	// 사람 1명 가져오기
	public GuestVo getGuest(int No) {
		GuestVo guestVo = null;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no, ";
			query += "        name,";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " FROM guestbook ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, No);

			
			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				guestVo = new GuestVo(no, name, password, content, regDate);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();
		return guestVo;
	}

	// 삭제
	public int delete(GuestVo guestVo) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete FROM guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestVo.getNo());
			pstmt.setString(2, guestVo.getPassword());

			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	// 조회
	public List<GuestVo> GList() {
		
		List<GuestVo> guestList = new ArrayList<GuestVo>();

		getConnection();

		try {// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no, ";
			query += "        name,";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " FROM guestbook ";
			query += " order by reg_date desc ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestVo vo = new GuestVo(no, name, password, content, regDate);
				guestList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return guestList;

	}

}
