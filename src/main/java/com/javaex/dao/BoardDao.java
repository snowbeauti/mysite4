package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

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

	// 저장
	public int insert(BoardVo bvo) {
		int count = 0;

		gcn();

		try {// 3. SQL문 준비 / 바인딩 / 실행

			/*
			 * INSERT INTO board VALUES (seq_board_no.nextval, '제목', '내용', 0, sysdate, 1);
			 */
			String query = "";
			query += " INSERT INTO board ";
			query += " VALUES (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bvo.getTitle());
			pstmt.setString(2, bvo.getContent());
			pstmt.setInt(3, bvo.getUser_no());

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

	public int update(BoardVo bvo) {

		gcn();
		int count = 0;

		try {// 3. SQL문 준비 / 바인딩 / 실행

			/*
			 * UPDATE board SET title = '배송문의', content = '배송언제되나요?', reg_date = sysdate
			 * WHERE no = 1 ;
			 */

			String query = "";
			query += " UPDATE board ";
			query += " SET title = ?, ";
			query += " content = ?, ";
			query += " reg_date = sysdate ";
			query += " WHERE no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bvo.getTitle());
			pstmt.setString(2, bvo.getContent());
			pstmt.setInt(3, bvo.getNo());

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

	// 삭제
	public int delete(BoardVo bvo) {
		int count = 0;
		gcn();

		try {// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * DELETE FROM board WHERE no = 1 ;
			 */
			String query = "";
			query += " DELETE FROM board ";
			query += " WHERE no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bvo.getNo());

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
	public List<BoardVo> BList() {

		List<BoardVo> boardList = new ArrayList<BoardVo>();

		gcn();

		try {// 3. SQL문 준비 / 바인딩 / 실행

			/*
			 * SELECT board.no, board.title, board.content, board.hit, board.reg_date,
			 * board.user_no, users.id, users.name FROM board, users where users.no =
			 * board.user_no;
			 */
			String query = "";
			query += " SELECT board.no, ";
			query += "        board.title, ";
			query += "        board.content, ";
			query += "        board.hit, ";
			query += "        board.reg_date, ";
			query += "        board.user_no, ";
			query += "        users.id, ";
			query += "        users.name ";
			query += " FROM board, users ";
			query += " where users.no = board.user_no ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String id = rs.getString("id");
				String name = rs.getString("name");

				BoardVo bvo = new BoardVo(no, title, content, hit, reg_date, user_no, id, name);
				boardList.add(bvo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardList;

	}

	// 조회
	public BoardVo getone(int no) {

		BoardVo bvo = null;
		gcn();

		try {// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";
			query += " SELECT board.no, ";
			query += "        board.title, ";
			query += "        board.content, ";
			query += "        board.hit, ";
			query += "        board.reg_date, ";
			query += "        board.user_no, ";
			query += "        users.id, ";
			query += "        users.name ";
			query += " FROM board, users ";
			query += " where users.no = board.user_no ";
			query += " and board.no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int No = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String id = rs.getString("id");
				String name = rs.getString("name");

				bvo = new BoardVo(No, title, content, hit, reg_date, user_no, id, name);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return bvo;

	}

	// 조회수 +1
	public void hit(int no) {

		gcn();

		try {// 3. SQL문 준비 / 바인딩 / 실행
				// update board set hit = hit+1 where no=5;
			String query = "";
			query += " update board set hit = hit+1 ";
			query += " WHERE no = ? ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 조회되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

	}

	// 검색
	public void boardsearch(String search) {
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		gcn();

		try {

			/*
			 * SELECT board.title, board.content, users.id, users.name FROM board, users
			 * where board.title like ? or board.content like ? or users.id like ? or
			 * users.name like ? and users.no = board.user_no;
			 */

			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";
			query += " SELECT board.title, ";
			query += "        board.content, ";
			query += "        users.id, ";
			query += "        users.name ";
			query += " FROM board, users ";
			query += " where board.title like ? ";
			query += " or board.content like ? ";
			query += " or users.id like ? ";
			query += " or users.name like ? ";
			query += " and users.no = board.user_no ";

			pstmt = conn.prepareStatement(query);

			String word = "%" + search + "%";
			pstmt.setString(1, word);
			pstmt.setString(2, word);
			pstmt.setString(3, word);
			pstmt.setString(4, word);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String name = rs.getString("name");

				System.out.println(word);
			}

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

}
