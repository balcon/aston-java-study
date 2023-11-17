package com.github.balcon.venue.servlet;

import com.github.balcon.venue.ApplicationFactory;
import com.github.balcon.venue.persistence.EventPersistence;
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
    private EventPersistence eventPersistence;

    @Override
    public void init() {
        super.init();
        eventPersistence = ApplicationFactory.getPersistenceFactory().getEventPersistence();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(JSON);
        PrintWriter respWriter = resp.getWriter();
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            List<Event> events = eventPersistence.findAll();
            respWriter.write(mapper.writeValueAsString(events));
        } else {
            Optional<Event> event = eventPersistence.find(Integer.parseInt(id));
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
        eventPersistence.save(event);
        resp.getWriter().write(mapper.writeValueAsString(event));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String bandId = req.getParameter("bandId");
        if (checkId(id, resp)) {
            if (checkId(bandId, resp)) {
                checkResult(eventPersistence.addBand(Integer.parseInt(id), Integer.parseInt(bandId)), resp);
            } else {
                Event event = Event.builder()
                        .id(Integer.parseInt(id))
                        .name(req.getParameter("name"))
                        .dateTime(LocalDateTime.parse(req.getParameter("dateTime"))).build();
                checkResult(eventPersistence.update(event), resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String bandId = req.getParameter("bandId");
        if (checkId(id, resp)) {
            if (checkId(bandId, resp)) {
                checkResult(eventPersistence.removeBand(Integer.parseInt(id), Integer.parseInt(bandId)), resp);
            } else {
                checkResult(eventPersistence.delete(Integer.parseInt(id)), resp);
            }
        }
    }
}
