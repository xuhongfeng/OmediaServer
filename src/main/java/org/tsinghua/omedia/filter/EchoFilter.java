package org.tsinghua.omedia.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class EchoFilter implements Filter {
	private static final Logger logger = Logger.getLogger(EchoFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(response != null) {
			logger.info("charset="+response.getCharacterEncoding());
			logger.info("contentType="+response.getContentType());
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
