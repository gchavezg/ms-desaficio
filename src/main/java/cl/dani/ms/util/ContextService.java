package cl.dani.ms.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * clase para obtener los parametros de la cabecera de las llamadas HTTP
 * @author oracle
 *
 */
public class ContextService extends ServletRequestWrapper {

	public static ThreadLocal<Map<String, String>> headers = new InheritableThreadLocal<>();
    private static ThreadLocal<Locale> localeTL = new ThreadLocal<>();

    public static final String AUTHORIZATION = "Authorization";
	public static final String TRACKING_ID = "Tracking-Id";
	public static final String APPLICATION_ID = "Application-Id";
	public static final String CHANNEL = "Channel";
	public static final String REFERENCE_SERVICE = "Reference-Service";
	public static final String REFERENCE_OPERATION = "Reference-Operation";
	public static final String REMOTE_ADDR = "remote-addr";
    public static final String ORIGIN_ADDR = "Origin-addr";
    public static final String USER_LOCALE = "user-locale";


    /**
     * constructor: obtiene los datos de la cabecera
     * @param request
     */
    public ContextService(ServletRequest request){
        super(request);
        if (request instanceof HttpServletRequest) {
            headers.set(new HashMap<String, String>());
            HttpServletRequest httprequest = (HttpServletRequest) request;
            Enumeration<String> enumer = httprequest.getHeaderNames();
            while (enumer.hasMoreElements()) {
                String type = enumer.nextElement();
                ContextService.headers.get().put(type, httprequest.getHeader(type));
            }
            if (httprequest.getHeader(USER_LOCALE) == null) {
                ContextService.localeTL.set(request.getLocale());
            } else {
                //Parsear locale
                Locale locale = null;
                String strLocale = httprequest.getHeader(USER_LOCALE);
                String[] locAr = strLocale.split("_");
                switch (locAr.length) {
                    case 1:
                        locale = new Locale(locAr[0]);
                        break;
                    case 2:
                        locale = new Locale(locAr[0], locAr[1]);
                        break;
                    case 3:
                        locale = new Locale(locAr[0], locAr[1], locAr[3]);
                        break;
                    default:
                        throw new IllegalArgumentException("Locale mal formado: " + strLocale);
                }
                ContextService.localeTL.set(locale);
            }

        }
        ContextService.headers.get().put(REMOTE_ADDR, request.getRemoteAddr());
    }

    /**
     * Agrega un parametro a la cabecera
     * @param name
     * @param value
     */
    public void putHeader(String name, String value){
        ContextService.headers.get().put(name, value);
    }

    /**
     * Obtiene una parametro de la cabecera
     * @param name
     * @return
     */
    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = headers.get().get(name);

        if (headerValue == null){
        	headerValue = ((HttpServletRequest) getRequest()).getHeader(name);
        }
        // else return from into the original wrapped object
        return headerValue;
    }


    /**
     * Obtienen todos los elementos de la cabecera
     * @return
     */
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<>(headers.get().keySet());

        // now add the headers from the wrapped request object
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }

	public static ThreadLocal<Map<String, String>> getHeaders() {
		return headers;
	}

    public static Locale getCurrentLocale() {
        return localeTL.get();
    }
}