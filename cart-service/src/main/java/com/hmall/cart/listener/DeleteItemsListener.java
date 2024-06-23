package com.hmall.cart.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmall.cart.domain.po.Cart;
import com.hmall.cart.service.ICartService;
import com.hmall.common.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DeleteItemsListener {

    private final ICartService cartService;

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name="cart.clear.queue",durable = "true"),
            exchange = @Exchange(name="trade.topic",type = ExchangeTypes.TOPIC),
            key="order.create"
    ))
    public void listenerDeleteSuccess(List<Long> itemIds, Message message){
        //获取用户信息
        Long userId = message.getMessageProperties().getHeader("user-info");
        // 1.构建删除条件，userId和itemId
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
        queryWrapper.lambda()
                .eq(Cart::getUserId, userId)
                .in(Cart::getItemId, itemIds);
        //2.删除
        // 直接使用RabbitMQ异步调用
        cartService.remove(queryWrapper);
    }

}
