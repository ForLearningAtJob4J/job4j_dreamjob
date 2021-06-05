<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <%--    <meta charset="utf-8">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- https://code.jquery.com/jquery-3.4.1.slim.min.js
     sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n-->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script>
        $(document).ready(function () {
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/job4j_dreamjob/cities",
                dataType: "json"
            }).done(function (data) {
                let cities = "";
                for (let i = 0; i < data.length; i++) {
                    cities += "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
                }
                $('#city').html(cities);
                $('#city option[value=${candidate.city.id}]').prop('selected', true);
            })
        });

        function validate() {
            let message = '';
            if ($('#name').val() === '') {
                message += 'Имя\n';
            }
            if ($('#city').val() === '') {
                message += 'Город\n';
            }

            if (message === '') {
                return true;
            }
            message = "Заполните следующие поля:\n\n" + message;
            alert(message);

            return false;
        }
    </script>
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <jsp:include page="../includes/header.jsp"/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                ${title}
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidate.do?op=edit&id=${candidate.id}" method="post">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input id="name" type="text" class="form-control" name="name" value="${candidate.name}">
                    </div>
                    <div class="form-group">
                        <label for="city">Город</label>
                        <select class="form-control" name="city" id="city"></select>
                    </div>
                    <button onclick="return validate();" type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>