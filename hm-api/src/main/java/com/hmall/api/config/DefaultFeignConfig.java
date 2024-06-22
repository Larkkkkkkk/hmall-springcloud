package com.hmall.api.config;
import com.hmall.api.client.fallback.CartClientFallbackFactory;
import com.hmall.api.client.fallback.ItemClientFallbackFactory;
import com.hmall.api.client.fallback.TradeClientFallbackFactory;
import com.hmall.api.client.fallback.UserClientFallbackFactory;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level fullLoggerLevel() {
        return Logger.Level.FULL;
    }

    //微服务直接调用 给请求头添加user-info信息
    @Bean
    public RequestInterceptor userInfoInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                Long userId=UserContext.getUser();
                if(userId!=null){
                    //所有地方都规定的是 header头是user-info
                    template.header("user-info", String.valueOf(userId));
                }
            }
        };
    }

    //服务保护-fallback快速失败
    @Bean
    public ItemClientFallbackFactory itemClientFallbackFactory() {
        return new ItemClientFallbackFactory();
    }

    //服务保护-fallback快速失败
    @Bean
    public CartClientFallbackFactory cartClientFallbackFactory() {
        return new CartClientFallbackFactory();
    }

    //服务保护-fallback快速失败
    @Bean
    public TradeClientFallbackFactory tradeClientFallbackFactory(){
        return new TradeClientFallbackFactory();
    }

    //服务保护-fallback快速失败
    @Bean
    public UserClientFallbackFactory userClientFallbackFactory(){
        return new UserClientFallbackFactory();
    }

}
