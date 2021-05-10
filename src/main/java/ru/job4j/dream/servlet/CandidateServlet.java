package ru.job4j.dream.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.Store;

import java.io.IOException;
import java.util.logging.Logger;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int intId;
        String title;
        if (id == null) {
            title = "Новый кандидат.";
            intId = 0;
        } else {
            title = "Редактирование кандидата.";
            try {
                intId = Integer.parseInt(id);
            } catch (NumberFormatException nfe) {
                Logger.getLogger(CandidateServlet.class.getName()).warning("Suspicious parameters\n" + nfe.getMessage());
                intId = 0;
            }
        }

        Candidate candidate = Store.instOf().findCandidateById(intId);
        if (candidate == null) {
            candidate = new Candidate(0, "");
            title = "Новый кандидат.";
        }

        req.setAttribute("title", title);
        req.setAttribute("candidate", candidate);
        req.getRequestDispatcher("WEB-INF/candidate/edit.jsp").forward(req, resp);
    }
}
