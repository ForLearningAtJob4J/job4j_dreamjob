package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PsqlStore;

import java.io.IOException;
import java.util.logging.Logger;

public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("op") == null || "edit".equals(req.getParameter("op"))) {
            String id = req.getParameter("id");
            int intId;
            String title;
            if (id == null) {
                title = "Новая вакансия.";
                intId = 0;
            } else {
                title = "Редактирование вакансии.";
                try {
                    intId = Integer.parseInt(id);
                } catch (NumberFormatException nfe) {
                    Logger.getLogger(PostServlet.class.getName()).warning("Suspicious parameter\n" + nfe.getMessage());
                    intId = 0;
                }
            }

            Post post = PsqlStore.instOf().findPostById(intId);
            if (post == null) {
                post = new Post(0, "", "");
                title = "Новая вакансия.";
            }

            req.setAttribute("title", title);
            req.setAttribute("post", post);
            req.getRequestDispatcher("WEB-INF/post/edit.jsp").forward(req, resp);
        } else {
            doPost(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        switch (req.getParameter("op")) {
            case "edit":
                PsqlStore.instOf().save(new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("description")));
                break;
            case "del":
                PsqlStore.instOf().delete(new Post(
                        Integer.parseInt(req.getParameter("id")),
                        "",
                        ""));
                break;
            default:
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
