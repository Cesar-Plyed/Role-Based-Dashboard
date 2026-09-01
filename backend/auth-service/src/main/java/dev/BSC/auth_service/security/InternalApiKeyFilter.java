package dev.BSC.auth_service.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class InternalApiKeyFilter implements Filter {

    @Value("${internal.api.key}")
    private String internalApiKey;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        System.out.println(request.getHeader("X-Internal-Key"));
        if (request.getRequestURI().matches("^/users/\\d+$")) { // solo /users/{id} numérico
            String key = request.getHeader("X-Internal-Key");
            if (key == null || !key.equals(internalApiKey)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid internal key");
                return;
            }
        }
        chain.doFilter(req, res);
        chain.doFilter(req, res);
    }
}