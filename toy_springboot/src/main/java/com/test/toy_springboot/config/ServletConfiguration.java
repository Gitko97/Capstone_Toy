package com.test.toy_springboot.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletConfiguration implements WebApplicationInitializer {
	 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
          FilterRegistration charEncodingFilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
          charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
          charEncodingFilterReg.setInitParameter("forceEncoding", "true");
          charEncodingFilterReg.addMappingForUrlPatterns(null, true, "/*");
    }
}