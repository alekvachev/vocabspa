<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ page import="vocabspa.dictionary.DictionaryService" %>
<%@ page import="vocabspa.user.UsersService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.TimeZone" %>

<html>
<head>
    <script type = "text/javascript" src = "js/libraries/jquery-2.1.1.js"></script>
    <script type = "text/javascript" src = "js/libraries/jquery.cookie.js"></script>

    <link type="text/css" rel="stylesheet" href="DataTables-1.10.4/media/css/jquery.dataTables.css"/>
    <link type="text/css" rel="stylesheet" href="DataTables-1.10.4/media/css/jquery.dataTables_themeroller.css"/>
    <script type = "text/javascript" src = "DataTables-1.10.4/media/js/jquery.dataTables.js"></script>

    <script src="http://www.google.com/jsapi" type="text/javascript"></script>

    <link type="text/css" rel="stylesheet" href="css/animate.css"/>
    <link type="text/css" rel="stylesheet" href="css/main.css"/>
    <script type = "text/javascript" src = "js/vocapp.js"></script>
    <script type = "text/javascript">
        $(window).load(function () {
            Vocapp.init();
        });
    </script>
</head>

<body>

<%
    Log logger = LogFactory.getLog("welcome.jsp");

    //Determine UTC offset
    int offset = 1;
    for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("timezoneoffset")) {
            offset = Integer.parseInt(cookie.getValue());
        }
    }

    //Determine if user has already been authenticated
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    //If user not logged in, offer Sign in link
    if (user == null) {
%>
    <p>Welcome to Vocab Spa. Use your Google Account to log in or register.</p>
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
<%
    //If user logged in, handle user
    } else {
        pageContext.setAttribute("user", user.getNickname());
%>
    <p>Hello, <span id="userNickname">${fn:escapeXml(user)}</span>. <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></p>
    <span id="timezoneOffset" style="display: none"><%= offset %></span>
<%
        //Determine if user has already registered
        Entity userEntity = UsersService.getUserEntity(user.getEmail());

        //If user hasn't registered, initialize user in DB
        if (userEntity == null) {
            try {
                userEntity = new Entity("user", user.getEmail());
                userEntity.setProperty("userObject", user);
                userEntity.setProperty("dateCreated", new Date());
                userEntity.setProperty("lastLogin", new Date());
                UsersService.updateUser(userEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Display successful registration message
%>
    <p>It looks like this is your first time on Vocab Spa. You are now fully registered.</p>
<%
        //User is already registered
        } else {
            //save current login to DB
            userEntity.setProperty("lastLogin", new Date());
            UsersService.updateUser(userEntity);

            if (offset != 1) {
                SimpleDateFormat sdfMonthYear = new SimpleDateFormat("MMMM, yyyy");
                SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm aaa");
                TimeZone tz = TimeZone.getTimeZone("UTC");
                tz.setRawOffset(-offset*60*1000);
                sdfMonthYear.setTimeZone(tz);
                sdfDate.setTimeZone(tz);

                String userSince = sdfMonthYear.format((Date) userEntity.getProperty("dateCreated"));
                String lastLogin = sdfDate.format((Date) userEntity.getProperty("lastLogin"));
%>
    <div class="userDetails">
        <p>Member since <%=userSince%></p>
        <p>Last login at <%=lastLogin%></p>
    </div>
<%
                //if user created new dictionary, initialize it in the datastore
                if (request.getParameter("newDictionaryName") != null) {
                    DictionaryService.addDictionaryForUser(request.getParameter("newDictionaryName"), userEntity.getKey());
                }

                //retrieve user dictionaries
                List<Entity> dictionaries = DictionaryService.getUserDictionaries(userEntity.getKey());

                //if user hasn't created any dictionaries yet
                if (dictionaries.size() == 0) {
%>
    <span>Create your first dictionary by giving it a name below:</span>
    <jsp:include page="createDictionary.jsp" flush="true"/>
<%
                //user has dictionaries
                } else {
                    //if user loaded a dictionary, display it's page
                    if (request.getParameter("dict") != null) {
                        Entity dictionary = null;
                        for (Entity dictEntity : dictionaries) {
                            if (dictEntity.getProperty("name").equals(request.getParameter("dict"))) {
                                dictionary = dictEntity;
                                break;
                            }
                        }
%>
    <jsp:include page="dictStats.jsp">
        <jsp:param name="name" value='<%=dictionary != null ? dictionary.getProperty("name") : ""%>'/>
        <jsp:param name="wordCount" value='<%=dictionary != null ? dictionary.getProperty("wordCount") : ""%>'/>
        <jsp:param name="currentStreak" value='<%=dictionary != null ? dictionary.getProperty("currentStreak") : ""%>'/>
        <jsp:param name="longestStreak" value='<%=dictionary != null ? dictionary.getProperty("longestStreak") : ""%>'/>
    </jsp:include>
    <div class="takeAction">
        <input type="button" value="Build up my vocabulary" onclick="Vocapp.displayAdd()">
    </div>
<%
                    //user has not selected a dictionary yet
                    } else {
%>
    <div class="selectDictionary">
        <form action="welcome.jsp" method="GET">
            <span>Load any of your dictionaries by selecting it from the list</span>
            <select name="dict">
<%
                    for (Entity dictEntity : dictionaries) {
%>
                <jsp:element name="option">
                    <jsp:attribute name="value"><%=dictEntity.getProperty("name")%></jsp:attribute>
                    <jsp:body><%=dictEntity.getProperty("name")%></jsp:body>
                </jsp:element>
<%
                    }
%>
            </select>
            <input type="submit" value="Load">
        </form>
    </div>
    <span>Create a new dictionary by giving it a name</span>
    <jsp:include page="createDictionary.jsp" flush="true"/>
<%
                    }
                }
            }
        }
    }
%>

</body>
</html>