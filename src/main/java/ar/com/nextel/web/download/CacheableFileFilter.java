package ar.com.nextel.web.download;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filtro que agrega informacion de caching a los requests de contenido estatico
 * de presentacion.
 * 
 * @author jaizpuru
 */
public class CacheableFileFilter implements Filter {

    private static long SERVING_SINCE = new Date().getTime();

	private static int HOUR = 3600;

	private static int DAY = 24 * HOUR;

    /** Milisegundos en una hora */
    private static final int MILLIS_IN_AN_HOUR = HOUR * 1000;

	private static final long EXPIRES = new Date().getTime() + MILLIS_IN_AN_HOUR;

    private static final String CACHE_HEADER = "private,max-age=" + DAY + ",post-check=" + HOUR
			+ ",pre-check=" + (HOUR * 12);

	
	
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletResponse response = (HttpServletResponse) res;

        // Last modified of cached resource
        long lastModifiedCached;
        try {
        	HttpServletRequest httpReq = (HttpServletRequest) req;
            lastModifiedCached = httpReq.getDateHeader("If-Modified-Since");
        } catch (IllegalArgumentException e) {
            lastModifiedCached = -1;
        }

        // Si no se modificó, 304
        if (lastModifiedCached >= SERVING_SINCE) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;

        } else {
            response.setDateHeader("Last-Modified", SERVING_SINCE);
            response.addHeader("Cache-Control", CACHE_HEADER);
        }

        response.addDateHeader("Expires", EXPIRES);

		// pass the request/response on
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
