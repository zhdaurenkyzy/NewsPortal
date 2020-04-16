<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>

<html>
<head>
    <title>Single - News Portal</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!--[if lte IE 8]>
    <script src="/assets/js/ie/html5shiv.js"></script><![endif]-->

    <style type="text/css">
        <%@include file="/assets/css/main.css"%>
        <%@include file="/assets/css/ie9.css"%>
        <%@include file="/assets/css/ie8.css"%>


    </style>
</head>
<body class="single">

<!-- Wrapper -->
<div id="wrapper">

    <!-- Header -->
    <header id="header">
        <h1><a href="/"><spring:message code="label.newsPortal"/></a></h1>
        <nav class="links">
            <ul>
                <li><a href="/"><spring:message code="label.main"/></a></li>
                <sec:authorize access="hasAuthority('ADMIN')">
                    <li><a href="/admin"><spring:message code="label.admin"/></a></li>
                </sec:authorize>
                <li><a href="?lang=en" style="color:#800000"><spring:message code="label.english"/></a></li>
                <li><a href="?lang=ru" style="color:#800000"><spring:message code="label.russian"/></a></li>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="/editUserPage"><spring:message code="label.profile"/></a></li>
                </sec:authorize>

                <sec:authorize access="!isAuthenticated()">
                    <li><a href="/getSignInPage"><spring:message code="label.auth"/></a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <spring:message code="label.welcomeBack"/>
                            ${pageContext.request.userPrincipal.name}
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="/logout"><spring:message code="label.logout"/></a></li>
                </sec:authorize>
            </ul>
        </nav>

    </header>

    <!-- Main -->
    <div id="main">
        <form:form method="POST" action="/editComment" modelAttribute="comment">
        <!-- Post -->
        <article class="post">

            <div class="row uniform">


                <form:label path="textOfComment"><spring:message code="label.yourComment"/>:</form:label>
                <form:input type="text" path="textOfComment" placeholder="Text"/><br>
                <form:errors path="textOfComment" style="color:#ff0000"/>

                <form:input path="id" type="hidden"/>



                <label path="author">Author:</label>
                <form:label path="authorOfComment"></form:label>

                <div class="12u$">
                    <ul class="actions">
                        <li><input type="submit" value=<spring:message code="label.save"/>></li>

                    </ul>
                </div>
            </div>
            </form:form>
            <table>
                <thead>
                <tr>
                    <th><spring:message code="label.comments"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach var="comment" items="${comments}">
                    <tbody>
                    <tr>
                        <td>${comment.authorOfComment.name}</td>
                        <td>${comment.textOfComment}</td>
                        <td>${comment.localDate}</td>
                    </tr>

                    </tbody>
                </c:forEach>

            </table>
        </article>

    </div>

    <!-- Footer -->
    <section id="footer">

        <p class="copyright">&copy; Untitled. Design: <a href="http://html5up.net">HTML5 UP</a>. Images: <a
                href="http://unsplash.com">Unsplash</a>.</p>
    </section>

</div>

<!-- Scripts -->
<script src="/assets/js/jquery.min.js"></script>
<script src="/assets/js/skel.min.js"></script>
<script src="/assets/js/util.js"></script>
<!--[if lte IE 8]>
<script src="/assets/js/ie/respond.min.js"></script><![endif]-->
<script src="/assets/js/main.js"></script>

</body>
</html>