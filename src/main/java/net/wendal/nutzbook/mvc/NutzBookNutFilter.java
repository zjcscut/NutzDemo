package net.wendal.nutzbook.mvc;

import org.nutz.mvc.NutFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangjinci
 * @version 2016/6/7 10:39
 */
public class NutzBookNutFilter extends NutFilter {
    //含义是, 如果访问/druid或/rs下的路径,就无条件跳过NutFilter
    //然后,打开web.xml, 将其中的org.nutz.mvc.NutFilter
    // 改成net.wendal.nutzbook.mvc.NutzBookNutFilter
    protected Set<String> prefixs = new HashSet<>();

    public void init(FilterConfig conf) throws ServletException {
        super.init(conf);
        prefixs.add(conf.getServletContext().getContextPath() + "/druid/");
        prefixs.add(conf.getServletContext().getContextPath() + "/rs/");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) req).getRequestURI();
            for (String prefix : prefixs) {
                if (uri.startsWith(prefix)) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }
        super.doFilter(req, resp, chain);
    }

}
