package avviait.filter;

import avviait.controller.StartupperSessionController;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/index")
public class IndexFilter implements Filter {
    @Inject
    private StartupperSessionController startupperSessionController;

    /**
     * If startupper is logged in and attempt to go to index, it is redirected to his startupper profile
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (startupperSessionController.isStartupperLoggedIn()) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            res.sendRedirect(req.getContextPath() + "/startupper/profile?startupper=" +
                    startupperSessionController.getStartupper().getId());
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }

    public void destroy() {
        // Nothing to do here!
    }
}