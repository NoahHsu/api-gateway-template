package org.example.api.gateway.template.filter;

import org.example.api.gateway.template.constants.ApiConstants;
import org.example.api.gateway.template.exception.BadRequestException;
import org.example.api.gateway.template.model.filter.RequestCachedWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BodyAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(ApiConstants.AUTHORIZATION_HEADER);
        try {
            if (!StringUtils.hasLength(token)) {
                throw new BadRequestException(
                        String.format("Header didn't contains token in %s", ApiConstants.AUTHORIZATION_HEADER));
            }
            // cached body inputStream because need to read twice (both in authenticator and controller)
            final RequestCachedWrapper cachedBodyHttpServletRequest = new RequestCachedWrapper(request);
            // TODO use body for auth throws Exception if verify fail
//            String copyRequestBody = StreamUtils.copyToString(cachedBodyHttpServletRequest.getInputStream(),
//                                                              StandardCharsets.UTF_8);

            filterChain.doFilter(cachedBodyHttpServletRequest, response);
        } catch (BadRequestException be) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), be.getMessage());
        }
    }

}
