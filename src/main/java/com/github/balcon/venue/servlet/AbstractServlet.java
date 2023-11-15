package com.github.balcon.venue.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.venue.ApplicationFactory;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {
    protected static final String JSON = "application/json";
    protected ObjectMapper mapper;

    @Override
    public void init() {
        mapper = ApplicationFactory.getJsonMapper();
    }


    protected boolean checkId(String id, HttpServletResponse resp) throws IOException {
        if (id == null || id.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }

    protected void checkResult(boolean result, HttpServletResponse resp) throws IOException {
        if (!result) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
