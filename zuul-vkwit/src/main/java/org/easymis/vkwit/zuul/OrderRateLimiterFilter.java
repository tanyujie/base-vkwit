package org.easymis.vkwit.zuul;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 限流过滤类
 * 
 * @ClassName: OrderRateLimiterFilter
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lenovo-t
 * @date 2019年5月19日
 *
 */
public class OrderRateLimiterFilter extends ZuulFilter {
	// 每秒产生1000个令牌
	private static final RateLimiter RATE_LIMITER = RateLimiter.create(1000);

	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		// 只对订单接口限流
		if ("/apigateway/order/api/v1/order/save".equalsIgnoreCase(request.getRequestURI())) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();

		// 就相当于每调用一次tryAcquire()方法，令牌数量减1，当1000个用完后，那么后面进来的用户无法访问上面接口
		// 当然这里只写类上面一个接口，可以这么写，实际可以在这里要加一层接口判断。
		if (!RATE_LIMITER.tryAcquire()) {
			requestContext.setSendZuulResponse(false);
			// HttpStatus.TOO_MANY_REQUESTS.value()里面有静态代码常量
			requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre"; // 定义filter的类型，有pre、route、post、error四种
	}

	@Override
	public int filterOrder() {
		return -4;
	}

}
