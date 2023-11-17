package com.github.balcon.venue.servlet;

import com.github.balcon.venue.ApplicationFactory;
import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.entity.Event;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/events")
public class EventServlet extends AbstractServlet {
    private EventDao eventDao;

    @Override
    public void init() {
        super.init();
        eventDao = ApplicationFactory.getDaoFactory().getEventDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        PrintWriter respWriter = resp.getWriter();
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            List<Event> events = eventDao.findAll();
            respWriter.write(mapper.writeValueAsString(events));
        } else {
            Optional<Event> event = eventDao.find(Integer.parseInt(id));
            if (event.isPresent()) {
                respWriter.write(mapper.writeValueAsString(event.get()));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        Event event = Event.builder()
                .name(req.getParameter("name"))
                .dateTime(LocalDateTime.parse(req.getParameter("dateTime")))
                .build();
        eventDao.save(event);
        resp.getWriter().write(mapper.writeValueAsString(event));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String bandId = req.getParameter("bandId");
        if (checkId(id, resp)) {
            if (checkId(bandId, resp)) {
                checkResult(eventDao.addBand(Integer.parseInt(id), Integer.parseInt(bandId)), resp);
            } else {
                Event event = Event.builder()
                        .id(Integer.parseInt(id))
                        .name(req.getParameter("name"))
                        .dateTime(LocalDateTime.parse(req.getParameter("dateTime"))).build();
                checkResult(eventDao.update(event), resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String bandId = req.getParameter("bandId");
        if (checkId(id, resp)) {
            if (checkId(bandId, resp)) {
                checkResult(eventDao.removeBand(Integer.parseInt(id), Integer.parseInt(bandId)), resp);
            } else {
                checkResult(eventDao.delete(Integer.parseInt(id)), resp);
            }
        }
    }
}
