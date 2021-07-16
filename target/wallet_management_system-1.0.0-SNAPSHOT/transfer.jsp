<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<html>
<head>
 <title>Account Management</title>
</head>
<body>
<div class="main">
   <form action="transfer" method="post">
        <table border="1" cellpadding="5">
          <tr>
                <th>Debit from: </th>
                <td>
                 <select name="debitAccount">
                 <option value="">-</option>
		            <c:forEach var="account" items="${accounts}">
		                <option value="${account.id}">${account.accountName} | ${account.currency}</option>
		            </c:forEach>
		        </select>
                </td>
            </tr> 
          <tr>
                <th>Credit to: </th>
                <td>
		        <select name="creditAccount">
		        <option value="">-</option>
		            <c:forEach var="account" items="${accounts}">
		                <option value="${account.id}">${account.accountName} | ${account.currency}</option>
		            </c:forEach>
		        </select>
                </td>
            </tr>
              
            <tr>
                <th>Amount to be transfer: </th>
                <td>
                 <input type="text" name="amount" size="45" />
                </td>
            </tr>
            <tr>
             <td colspan="2" align="center">
              <input type="submit" value="Save" />
             </td>
            </tr>
        </table>
        </form>
        </div>
</body>
</html>