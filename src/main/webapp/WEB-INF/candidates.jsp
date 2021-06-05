<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
<%--    <meta charset="utf-8">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <jsp:include page="includes/header.jsp"/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Кандидаты
            </div>
            <div class="card-body">
                <table class="table align-content-center">
                    <thead>
                    <tr>
                        <th scope="col">Имя</th>
                        <th scope="col">Фото</th>
                        <th scope="col">Город</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${candidates}" var="candidate">
                        <tr>
                            <td>
                                <a href='<c:url value="/candidate.do?op=edit&id=${candidate.id}"/>'><i
                                        class="fa fa-edit mr-3"></i></a>
                                <c:out value="${candidate.name}"/>
                            </td>
                            <td>
                                <div class="img_wrapper">
                                    <img src="<c:url value='/download?name=${candidate.id}'/>" alt/>
                                </div>
                                <a href="<c:url value='/photo?op=load&id=${candidate.id}'/>"><i class="fa fa-folder-open-o"></i></a>
                                <a href="<c:url value='/photo?op=del&id=${candidate.id}'/>"><i class="fa fa-remove"></i></a>
                            </td>
                            <td>
                                <c:out value="${candidate.city.name}"/>
                            </td>
                            <td>
                                <a href="<c:url value='/candidate.do?op=del&id=${candidate.id}'/>"><i
                                        class="fa fa-remove fa-2x"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>