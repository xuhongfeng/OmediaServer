package org.tsinghua.omedia.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.tsinghua.omedia.utils.StringUtils;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CharsetFilter implements Filter {
    private Logger logger = Logger.getLogger(CharsetFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new Utf8Request((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {
        
    }

    private class Utf8Request extends HttpServletRequestWrapper {
        

        public Utf8Request(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values =  super.getParameterValues(name);
            for(int i=0; i<values.length; i++) {
                values[i] = StringUtils.iso88591toutf8(values[i]);
            }
            return values;
        }
        
    }
}
