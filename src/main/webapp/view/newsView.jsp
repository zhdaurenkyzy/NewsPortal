<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>

<html>
<head>
    <title>Single - News Portal</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!--[if lte IE 8]>
    <script src=/"assets/js/ie/html5shiv.js"></script><![endif]-->

    <style type="text/css">
        <%@include file="/assets/css/main.css"%>
        <%@include file="/assets/css/ie9.css"%>
        <%@include file="/assets/css/ie8.css"%>
<%--        <%@include file="/assets/css/font-awesome.min.css"%>--%>
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
        <form:form method="POST" action="/addComment" modelAttribute="comment">
        <!-- Post -->
        <article class="post">
            <header>
                <div class="title">
                    <h2>${news.newsTitle}</h2>
                    <p>${news.brief}</p>
                </div>
                <div class="meta">
                    <time class="published" datetime="${news.currentDate}">${news.currentDate}</time>
                    <a href="#" class="author"><span class="name">${news.author.name}</span>
                        <img src="/images/avatar.jpg"/></a>
                </div>
            </header>
            <span class="image featured"><img src="/images/pic01.jpg" alt=""/></span>
            <p>${news.context}</p>
            <footer>

            </footer>
            <div class="row uniform">
                <input type="hidden" name="newsId" value="${news.id}"/><br>
                <form:label path="textOfComment"><spring:message code="label.yourComment"/>:</form:label>
                <form:input type="text" path="textOfComment" placeholder="Text"/><br>
                <p style="color:#ff0000">${errorMessage}</p>
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
                    <th></th>
                </tr>
                </thead>

                <c:forEach var="comment" items="${comments}">

                    <tbody>

                    <tr>
                        <form method="POST" action="/editCommentPage">
                            <input type="hidden" name="commentId" value="${comment.id}"/>
                            <td>${comment.authorOfComment.name}</td>
                            <td>${comment.textOfComment}</td>
                            <td>${comment.localDate}</td>
                            <c:if test="${pageContext.request.userPrincipal.name==comment.authorOfComment.login}">
                            <td><input type="submit" name="Edit" value=<spring:message code="label.edit"/>></td>

                        </form>
                        <form method="POST" action="/deleteComment">
                            <input type="hidden" name="commentId" value="${comment.id}"/>
                            <td><input type="submit" value=<spring:message code="label.delete"/>></td>
                            </c:if>
                        </form>

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