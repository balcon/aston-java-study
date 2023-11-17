package com.github.balcon.venue.servlet;

import com.github.balcon.venue.ApplicationFactory;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.persistence.MusicianPersistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/musicians")
public class MusicianServlet extends AbstractServlet {
    private MusicianPersistence musicianPersistence;

    @Override
    public void init() {
        super.init();
        musicianPersistence = ApplicationFactory.getPersistenceFactory().getMusicianPersistence();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        PrintWriter respWriter = resp.getWriter();
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            List<Musician> musicians = musicianPersistence.findAll();
            respWriter.write(mapper.writeValueAsString(musicians));
        } else {
            Optional<Musician> band = musicianPersistence.find(Integer.parseInt(id));
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
        musicianPersistence.save(musician);
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
            checkResult(musicianPersistence.update(musician), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (checkId(id, resp)) {
            checkResult(musicianPersistence.delete(Integer.parseInt(id)), resp);
        }
    }
}
