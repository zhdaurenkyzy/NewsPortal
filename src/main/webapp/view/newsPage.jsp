<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>

<html>
<head>
    <title>EditPage - News Portal</title>
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
                    <li><a href="/signInPage"><spring:message code="label.auth"/></a></li>
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

        <!-- Post -->
        <article class="post">
            <c:if test="${empty news.newsTitle}">
                <c:url value="/add" var="var"/>
            </c:if>
            <c:if test="${not empty news.newsTitle}">
                <c:url value="/edit" var="var"/>
            </c:if>
            <form:form method="POST" action="${var}" modelAttribute="news">

                <header>
                    <h1><spring:message code="label.editPage"/></h1>
                    <div class="meta">
                        <c:if test="${not empty news.newsTitle}">
                            <form:input type="hidden" path="id"/><br>
                            <form:label path="currentDate">${news.currentDate}</form:label>
                            <a href="#" class="author"> <form:label path="author">${news.author.name}</form:label><img
                                    src="/images/avatar.jpg"/></a>

                            <%--                        <a href="#" class="author"><span class="name">${news.author.name}</span><img src="images/avatar.jpg" /></a>--%>
                        </c:if>

                    </div>
                    <div class="title">
                        <form:label path="newsTitle"><spring:message code="label.newsTitle"/>:</form:label>
                        <form:input type="text" path="newsTitle"/><br>
                        <form:errors path="newsTitle" style="color:#ff0000"/>


                        <form:label path="brief"><spring:message code="label.newsBrief"/>:</form:label>
                        <form:textarea path="brief"/>
                        <form:errors path="brief" style="color:#ff0000"/>
                    </div>
                </header>
                <span class="image featured"><img src="/images/pic01.jpg" alt=""/></span>
                <form:label path="context"><spring:message code="label.context"/>:</form:label>
                <form:textarea path="context" rows="10" cols="200"></form:textarea><br>

                <ul class="actions">
                    <c:if test="${empty news.newsTitle}">
                        <li><input type="submit" value=<spring:message code="label.addNews"/>></li>
                    </c:if>
                    <c:if test="${not empty news.newsTitle}">
                        <li><input type="submit" value=<spring:message code="label.save"/>></li>
                        <li><a href="/delete/${news.id}" class="button big next"><spring:message
                                code="label.delete"/></a></li>
                    </c:if>

                </ul>

                <footer>
                    <ul class="stats">


                    </ul>
                </footer>
            </form:form>
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