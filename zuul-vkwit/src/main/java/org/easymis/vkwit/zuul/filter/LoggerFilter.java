package org.easymis.vkwit.zuul.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

import org.easymis.vkwit.zuul.service.LogSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * 网关日志
* @ClassName: LoggerFilter
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lenovo-t
* @date 2019年5月19日
*
 */
@Component
public class LoggerFilter extends ZuulFilter{
	@Autowired
    private LogSendService logSendService;
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
        logSendService.send(RequestContext.getCurrentContext());
        return null;
	}

	@Override
	public String filterType() {
		return POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return SEND_RESPONSE_FILTER_ORDER - 1;
	}

}
