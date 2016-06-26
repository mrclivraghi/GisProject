package it.polimi.gis.boot.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements javax.servlet.Filter  {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletResponse response = (HttpServletResponse) res;
		 HttpServletRequest request = (HttpServletRequest) req;
				    response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
				    response.setHeader("Access-Control-Allow-Credentials", "true");
				    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
				    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key");
				  response.setHeader("Access-Control-Max-Age", "3600");
				  response.setHeader("Access-Control-Request-Headers","accept, content-type");//, postman-token
				   
				    if (!request.getMethod().equals("OPTIONS")) {
				    	 System.out.println(request.getSession().getId()+"-corsFilter"+request.getRequestedSessionId()+"-"+request.getMethod());
				    	 //System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId()+"real session id underlying");
				    	 
				    	 System.out.println(request.getQueryString());
				      chain.doFilter(req, res);
				    } else {
				    	 System.out.println(request.getRequestedSessionId()+"-corsFilter OPTION");
				    }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

   

}