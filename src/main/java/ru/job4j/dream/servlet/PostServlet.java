package ru.job4j.dream.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;

import java.io.IOException;
import java.util.logging.Logger;

public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        Post post = MemStore.instOf().findPostById(intId);
        if (post == null) {
            post = new Post(0, "", "");
            title = "Новая вакансия.";
        }

        req.setAttribute("title", title);
        req.setAttribute("post", post);
        req.getRequestDispatcher("WEB-INF/post/edit.jsp").forward(req, resp);
    }
}
