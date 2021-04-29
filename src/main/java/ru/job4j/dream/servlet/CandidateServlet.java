package ru.job4j.dream.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.Store;

import java.io.IOException;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().save(new Candidate(Integer.parseInt(req.getParameter("id")), req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.jsp");
    }
}