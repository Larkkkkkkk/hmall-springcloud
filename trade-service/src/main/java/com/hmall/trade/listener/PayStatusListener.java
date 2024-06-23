package com.hmall.trade.listener;

import com.hmall.trade.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayStatusListener {

    private final IOrderService orderService;

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name="trade.pay.sucess.queue",durable = "true"),
            exchange = @Exchange(name="pay.direct",type= ExchangeTypes.DIRECT),
            key="pay.success"
    ))
    public void listenPaySuccess(Long orderId){
        //把原来直接同步调用的方法写在这里 tradeClient.markOrderPaySuccess(po.getBizOrderNo());
        orderService.markOrderPaySuccess(orderId);
    }
}
