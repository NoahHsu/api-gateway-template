package org.example.api.gateway.template.config;

import org.example.api.gateway.template.constants.ApiConstants;
import org.example.api.gateway.template.filter.BodyAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    public FilterConfig(BodyAuthFilter requestBodyFilter) {
        this.requestBodyFilter = requestBodyFilter;
    }

    private final BodyAuthFilter requestBodyFilter;

    @Bean
    public FilterRegistrationBean<BodyAuthFilter> requestBodyFilterRegistration() {
        final FilterRegistrationBean<BodyAuthFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(requestBodyFilter);
        // if multiple filter:  filterBean.setOrder();
        filterBean.addUrlPatterns(ApiConstants.BASE_URL + ApiConstants.EXAMPLE_URL);
        return filterBean;
    }
}
