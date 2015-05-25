package avviait.controller;

import avviait.model.StartupperFacade;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/startupper/profile")
public class StartupperProfileFilter implements Filter {
    @Inject
    private StartupperFacade startupperFacade;

    /**
     * Checks if user exists from given id, if not redirect to loggedIn startupper profile
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String idString = req.getParameter("id");
        if (idString == null) {
            chain.doFilter(request, response);
        } else {
            try {
                Long id = Long.valueOf(idString);

                if (startupperFacade.getStartupper(id) == null) {
                    res.sendRedirect(req.getContextPath() + "/startupper/profile");
                } else {
                    chain.doFilter(request, response);
                }
            } catch (NumberFormatException e) {
                res.sendRedirect(req.getContextPath() + "/startupper/profile");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }

    public void destroy() {
        // Nothing to do here!
    }
}
