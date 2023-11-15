package com.github.balcon.venue.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.venue.ApplicationFactory;
import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.entity.Event;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/events")
public class EventServlet extends HttpServlet {
    private static final String JSON = "application/json";
    private EventDao eventDao;
    private ObjectMapper mapper;

    @Override
    public void init() {
        eventDao = ApplicationFactory.getDaoFactory().getEventDao();
        mapper = ApplicationFactory.getJsonMapper();
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
        if (checkId(id, resp)) {
            Event build = Event.builder()
                    .id(Integer.parseInt(id))
                    .name(req.getParameter("name"))
                    .dateTime(LocalDateTime.parse(req.getParameter("dateTime"))).build();
            checkResult(eventDao.update(build), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (checkId(id, resp)) {
            checkResult(eventDao.delete(Integer.parseInt(id)), resp);
        }
    }

    private boolean checkId(String id, HttpServletResponse resp) throws IOException {
        if (id == null || id.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }

    private void checkResult(boolean result, HttpServletResponse resp) throws IOException {
        if (!result) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
