<%--
  Created by IntelliJ IDEA.
  User: clort
  Date: 29/02/2020
  Time: 00:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    let pathname = window.location.pathname;
    let pagename = pathname.split("/").pop();

    if (pagename = "index") {
        $('#nav-index').addClass("active");
        $('#nav-settings').removeClass("active");
        $('#nav-history').removeClass("active");
    } else if (pagename = "settings") {
        $('#nav-index').removeClass("active");
        $('#nav-settings').addClass("active");
        $('#nav-history').removeClass("active");

    } else if (pagename = "history") {
        $('#nav-index').removeClass("active");
        $('#nav-settings').removeClass("active");
        $('#nav-history').addClass("active");

    }
</script>
