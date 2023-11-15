package com.github.balcon.venue.servlet;

import com.github.balcon.venue.ApplicationFactory;
import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.entity.Band;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/bands")
public class BandServlet extends AbstractServlet {
    private BandDao bandDao;

    @Override
    public void init() {
        super.init();
        bandDao = ApplicationFactory.getDaoFactory().getBandDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        PrintWriter respWriter = resp.getWriter();
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            List<Band> bands = bandDao.findAll();
            respWriter.write(mapper.writeValueAsString(bands));
        } else {
            Optional<Band> band = bandDao.find(Integer.parseInt(id));
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
        Band band = Band.builder()
                .name(req.getParameter("name")).build();
        bandDao.save(band);
        resp.getWriter().write(mapper.writeValueAsString(band));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (checkId(id, resp)) {
            Band band = Band.builder()
                    .id(Integer.parseInt(id))
                    .name(req.getParameter("name")).build();
            checkResult(bandDao.update(band), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (checkId(id, resp)) {
            checkResult(bandDao.delete(Integer.parseInt(id)), resp);
        }
    }
}
