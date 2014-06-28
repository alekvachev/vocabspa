<%--
  Created by IntelliJ IDEA.
  User: Alek
  Date: 6/8/2014
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="dictStats">
<%
    if (request.getParameter("name").equals("")) {
%>
    <div>We are currently unable to retrieve your dictionary</div>
<%
    } else {
%>
    <div>Current dictionary: <%=request.getParameter("name")%></div>
    <div>Word Count: <%=request.getParameter("wordCount")%></div>
    <div>You are on a <%=request.getParameter("currentStreak")%> day streak</div>
<%
        int currentStreak = Integer.parseInt(request.getParameter("currentStreak"));
        int longestStreak = Integer.parseInt(request.getParameter("longestStreak"));
        if (currentStreak >= longestStreak && currentStreak > 0) {
%>
    <div>This is your longest streak so far. Keep up the good work!</div>
<%
        } else {
            int diff = longestStreak - currentStreak;
%>
    <div>Keep up the good work for <%=diff%> more <%=diff > 1 ? "days" : "day"%> and you'll set a longest streak record. </div>
<%
        }
    }
%>
</div>
