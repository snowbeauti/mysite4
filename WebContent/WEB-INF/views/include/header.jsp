<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div id="header">
	<h1>
		<a href="/mysite4/main/index">MySite</a>
	</h1>
	<c:choose>
		<c:when test="${authUser == null}">
			<ul>
				<!-- 로그인 전 -->
				<li><a href="/mysite4/user/loginform">로그인</a></li>
				<li><a href="/mysite4/user/joinform">회원가입</a></li>
			</ul>
		</c:when>
		<c:otherwise>
			<ul>
				<!-- 로그인 후 -->
				<li>${authUser.name}님 안녕하세요^^</li>
				<li><a href="/mysite4/user/logout">로그아웃</a></li>
				<li><a href="/mysite4/user/modifyform">회원정보수정</a></li>
			</ul>
		</c:otherwise>
	</c:choose>
</div>
<!-- //header -->

<div id="nav">
	<ul>
		<li><a href="/mysite4/gbook/addlist">방명록</a></li>
		<li><a href="">갤러리</a></li>
		<li><a href="/mysite4/board?action=list">게시판</a></li>
		<li><a href="">입사지원서</a></li>
	</ul>
	<div class="clear"></div>
</div>
<!-- //nav -->