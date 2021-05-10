package ru.job4j.dream.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;

import java.io.IOException;

public class CandidateListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", MemStore.instOf().findAllCandidates());
        req.getRequestDispatcher("WEB-INF/candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        MemStore.instOf().save(new Candidate(Integer.parseInt(req.getParameter("id")), req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}