package ru.job4j.dream.servlet;

import jakarta.servlet.ServletException;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PostListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", Store.instOf().findAllPosts());
        req.getRequestDispatcher("WEB-INF/posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().save(new Post(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("description")));
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}