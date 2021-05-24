package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("op") == null || "edit".equals(req.getParameter("op"))) {
            String id = req.getParameter("id");
            int intId;
            String title;
            if (id == null) {
                title = "Регистрация пользователя.";
                intId = 0;
            } else {
                title = "Редактирование пользователя.";
                try {
                    intId = Integer.parseInt(id);
                } catch (NumberFormatException nfe) {
                    Logger.getLogger(PostServlet.class.getName()).warning("Suspicious parameter\n" + nfe.getMessage());
                    intId = 0;
                }
            }

            User user = PsqlStore.instOf().findUserById(intId);
            if (user == null) {
                user = new User();
                title = "Регистрация пользователя.";
            }

            req.setAttribute("title", title);
            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/reg.jsp").forward(req, resp);
        } else {
            doPost(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user;
        HttpSession sc = req.getSession();
        req.setCharacterEncoding("UTF-8");

        switch (req.getParameter("op")) {
            case "edit":
                user = new User();
                user.setId(Integer.parseInt(req.getParameter("id")));
                user.setName(req.getParameter("name"));
                user.setEmail(req.getParameter("email"));
                user.setPassword(req.getParameter("password"));
                PsqlStore.instOf().save(user);
                sc.setAttribute("user", user);
                break;
            case "del":
                user = new User();
                user.setId(Integer.parseInt(req.getParameter("id")));
                PsqlStore.instOf().delete(user);
                sc = req.getSession();
                sc.setAttribute("user", null);
                break;
            default:
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
