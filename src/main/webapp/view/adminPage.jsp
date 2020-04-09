<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>


<html>
<head>
    <title>News Portal</title>
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

        <form method="POST" action="/admin/deleteSetUser">
            <!-- Post -->
            <!-- Pagination -->
            <ul class="actions pagination">

                <input type="submit" value=<spring:message code="label.delete"/>>

            </ul>

            <header>

                <div class="table-wrapper">
                    <table>
                        <thead>
                        <tr>
                            <th><spring:message code="label.name"/></th>
                            <th><spring:message code="label.login"/></th>
                            <th><spring:message code="label.role"/></th>
                            <th><spring:message code="label.selectUser"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="user" items="${userList}">
                            <tbody>
                            <tr>
                                <td><a href="/editUser/${user.id}">${user.name}</a></td>
                                <td>${user.login}</td>
                                <td>${user.role}</td>
                                <td><input type="checkbox" class="checkbox" id="checkbox${user.id}"
                                           name="listModelCheckbox" value="${user.id}"/>
                                    <label for="checkbox${user.id}"></label>
                                </td>

                            </tr>

                            </tbody>
                        </c:forEach>

                    </table>
                </div>
            </header>

        </form>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <a href="#" class="logo"><img src="/images/logo.jpg" alt="/"/></a>
            <header>
                <h2><spring:message code="label.newsPortal"/></h2>

            </header>
        </section>

        <!-- Mini Posts -->
        <section>
            <div class="mini-posts">

                <!-- Mini Post -->


                <!-- Mini Post -->
                <article class="mini-post">
                    <header>
                        <h3><a href="/admin/addUserPage"><spring:message code="label.addNewUser"/></a></h3>

                    </header>
                    <a href="/admin/addUserPage" class="image"><img src="<spring:url value="/images/userPic.jpg"/>"
                                                                    alt="image"/></a>

                </article>

                <article class="mini-post">
                    <header>
                        <h3><a href="/newsListByAuthor"><spring:message code="label.myNewsList"/></a></h3>
                    </header>
                    <a href="/newsListByAuthor" class="image"><img src="/images/Picture2.jpg" alt=""/></a>
                </article>

            </div>
        </section>

        <!-- Posts List -->
        <section>

        </section>

        <!-- About -->


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