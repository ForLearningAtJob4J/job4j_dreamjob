package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.job4j.dream.file.Filer;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;

import java.io.IOException;
import java.util.logging.Logger;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("op") == null || "edit".equals(req.getParameter("op"))) {
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

            Candidate candidate = PsqlStore.instOf().findCandidateById(intId);
            if (candidate == null) {
                candidate = new Candidate(0, "");
                title = "Новый кандидат.";
            }

            req.setAttribute("title", title);
            req.setAttribute("candidate", candidate);
            req.getRequestDispatcher("WEB-INF/candidate/edit.jsp").forward(req, resp);
        } else {
            doPost(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        switch (req.getParameter("op")) {
            case "edit":
                PsqlStore.instOf().save(new Candidate(Integer.parseInt(req.getParameter("id")), req.getParameter("name"),
                        new City(Integer.parseInt(req.getParameter("city")), "")));
                break;
            case "del":
                PsqlStore.instOf().delete(new Candidate(Integer.parseInt(req.getParameter("id")), ""));
                Filer.deleteFile(req.getParameter("id"));
                break;
            default:
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
