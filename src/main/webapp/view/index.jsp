<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>

<html>
<head>
    <title>News Portal</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!--[if lte IE 8]>
    <script src="assets/js/ie/html5shiv.js"></script><![endif]-->

    <style type="text/css">
        <%@include file="/assets/css/main.css"%>
        <%@include file="/assets/css/ie9.css"%>
        <%@include file="/assets/css/ie8.css"%>

    </style>

</head>
<body>

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
                            ${pageContext.request.userPrincipal.name.toString()}
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

        <form method="POST" action="/deleteSetNews">

            <!-- Post -->
            <c:forEach var="news" items="${newsList}">

                <article class="post">

                    <header>

                        <div class="title">
                            <h2><a href="/continue/${news.id}">${news.newsTitle}</a></h2>


                        </div>
                        <div class="meta">
                            <time class="published" datetime="${news.currentDate}">${news.currentDate}</time>
                            <a href="#" class="author"><span class="name">${news.author.name}</span><img
                                    src="images/avatar.jpg"/></a>
                            <input type="hidden" value="${news.author}">
                        </div>

                    </header>
                    <a href="#" class="image featured"><img src="images/pic001.jpg" alt=""/></a>
                    <p> ${news.brief}</p>
                    <footer>
                        <ul class="actions">
                            <li><a href="/continue/${news.id}" class="button big"><spring:message
                                    code="label.continueReading"/></a></li>
                            <c:if test="${pageContext.request.userPrincipal.name==news.author.login}">
                                <li><a href="/edit/${news.id}" class="button big"><spring:message
                                        code="label.edit"/></a></li>
                            </c:if>
                        </ul>
                        <ul class="actions">
                            <c:if test="${pageContext.request.userPrincipal.name==news.author.login}">
                                <li><input type="checkbox" class="checkbox" id="checkbox${news.id}"
                                           name="listModelCheckbox" value="${news.id}"/>
                                    <label for="checkbox${news.id}"><spring:message code="label.check"/></label>
                                </li>
                            </c:if>
                        </ul>

                    </footer>

                </article>

            </c:forEach>

            <!-- Pagination -->
            <ul class="actions pagination">
                <sec:authorize access="hasAnyAuthority('AUTHOR', 'ADMIN')">
                    <input type="submit" value=<spring:message code="label.delete"/>>
                </sec:authorize>
            </ul>
        </form>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <a href="#" class="logo"><img src="images/logo.jpg" alt=""/></a>
            <header>
                <h2>News Portal</h2>

            </header>
        </section>

        <!-- Mini Posts -->
        <section>
            <div class="mini-posts">

                <!-- Mini Post -->
                <article class="mini-post">

                    <header>

                        <h3><a href="/newsListByAuthor"><spring:message code="label.myNewsList"/></a></h3>

                    </header>
                    <a href="/newsListByAuthor" class="image"><img src="images/pic04.jpg" alt=""/></a>
                </article>

                <!-- Mini Post -->
                <article class="mini-post">
                    <header>
                        <h3><a href="/addPage"><spring:message code="label.addNews"/></a></h3>

                    </header>
                    <a href="/addPage" class="image"><img src="images/pic05.jpg" alt=""/></a>
                </article>


            </div>
        </section>


        <!-- Footer -->
        <section id="footer">

            <p class="copyright">&copy; Untitled. Design: <a href="http://html5up.net">HTML5 UP</a>. Images: <a
                    href="http://unsplash.com">Unsplash</a>.</p>
        </section>

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