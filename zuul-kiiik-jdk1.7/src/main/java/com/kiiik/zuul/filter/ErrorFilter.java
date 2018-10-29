package com.kiiik.zuul.filter;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月16日
 */
public class ErrorFilter { /*extends ZuulFilter {
	 
 
    @Override
    public String filterType() {
        return "error";
    }
 
    @Override
    public int filterOrder() {
        return 0;
    }
 
    @Override
    public boolean shouldFilter() {
        return true;
    }
 
    @Override
    public Object run() {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            ZuulException exception = this.findZuulException(context.getThrowable());
            logger.error("进入系统异常拦截", exception);
 
            HttpServletResponse response = context.getResponse();
            response.setContentType("application/json; charset=utf8");
            response.setStatus(exception.nStatusCode);
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print("{code:"+ exception.nStatusCode +",message:\""+ exception.getMessage() +"\"}");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(writer!=null){
                    writer.close();
                }
            }
 
        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }
 
        return null;
    }
 
    ZuulException findZuulException(Throwable throwable) {
        if (ZuulRuntimeException.class.isInstance(throwable.getCause())) {
            return (ZuulException)throwable.getCause().getCause();
        } else if (ZuulException.class.isInstance(throwable.getCause())) {
            return (ZuulException)throwable.getCause();
        } else {
            return ZuulException.class.isInstance(throwable) ? (ZuulException)throwable : new ZuulException(throwable, 500, (String)null);
        }
    }*/
}
