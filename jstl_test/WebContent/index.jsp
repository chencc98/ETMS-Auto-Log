<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
  action paras : <c:out value="${param.action }"></c:out>
  <c:out value="test direct out"></c:out><br>
  
  <c:if test="${ param.action == 'good' }">
  	<p> this is good if</p>
  
  </c:if>
  <c:if test="${ param.action == 'bad' }">
  	<p> this is bad if</p>
  
  </c:if><br>
  
  <%
	request.setAttribute("localeList", Locale.getAvailableLocales());  
  %>
  <table><tr><th>Locale</th><th>Language</th><th>Data</th><th>Number</th><th>Currency</th></tr>
  
  	<jsp:useBean id="date" class="java.util.Date"></jsp:useBean>
  	<c:forEach var="locale" items="${localeList}">
  		<fmt:setLocale value="${ locale }"/>
  		<tr>
  			<td>${ locale.displayName } </td>
  			<td>${ locale.displayLanguage } </td>
  			<td><fmt:formatDate value="${ date }" type="both"/></td>
  			<td><fmt:formatNumber value="10000.5"></fmt:formatNumber></td>
  			<td><fmt:formatNumber value="10000.5" type="currency"></fmt:formatNumber></td>
  			</tr>
  	</c:forEach>
  
  
  </table>
</body>
</html>