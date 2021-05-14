package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import ru.job4j.dream.model.Post;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.dream.store.PsqlStore;

import java.io.IOException;

public class PostListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", PsqlStore.instOf().findAllPosts());
        req.getRequestDispatcher("WEB-INF/posts.jsp").forward(req, resp);
    }
}