package com.github.balcon.venue.servlet;

import com.github.balcon.venue.ApplicationFactory;
import com.github.balcon.venue.dao.MusicianDao;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Musician;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/musicians")
public class MusicianServlet extends AbstractServlet {
    private MusicianDao musicianDao;

    @Override
    public void init() {
        super.init();
        musicianDao = ApplicationFactory.getDaoFactory().getMusicianDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        PrintWriter respWriter = resp.getWriter();
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            List<Musician> musicians = musicianDao.findAll();
            respWriter.write(mapper.writeValueAsString(musicians));
        } else {
            Optional<Musician> band = musicianDao.find(Integer.parseInt(id));
            if (band.isPresent()) {
                respWriter.write(mapper.writeValueAsString(band.get()));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        Musician musician = Musician.builder()
                .name(req.getParameter("name"))
                .role(req.getParameter("role"))
                .band(Band.builder()
                        .id(Integer.valueOf(req.getParameter("bandId"))).build()).build();
        musicianDao.save(musician);
        resp.getWriter().write(mapper.writeValueAsString(musician));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (checkId(id, resp)) {
            Musician musician = Musician.builder()
                    .id(Integer.parseInt(id))
                    .name(req.getParameter("name"))
                    .role(req.getParameter("role"))
                    .band(Band.builder()
                            .id(Integer.valueOf(req.getParameter("bandId"))).build()).build();
            checkResult(musicianDao.update(musician), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (checkId(id, resp)) {
            checkResult(musicianDao.delete(Integer.parseInt(id)), resp);
        }
    }
}
