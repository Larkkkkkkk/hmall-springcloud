package com.hmall.api.client.fallback;
import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import com.hmall.common.utils.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import java.util.Collection;
import java.util.List;
@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    //通过ItemClientFallbackFactory生成ItemClient
    @Override
    public ItemClient create(Throwable cause) {
        // 重写ItemClient里面的方法
        //【如果真的openfeign调用时发生限流/熔断之后，请求按照以下逻辑返回一些默认数据/友好提示】
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.error("远程调用ItemClient#queryItemByIds方法出现异常，参数：{}", ids, cause);
                return CollUtils.emptyList(); // 查询购物车允许失败，查询失败，返回空集合
            }
            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.error("扣减业务库存失败！",cause);
                throw new RuntimeException(cause); // 库存扣减业务需要触发事务回滚，查询失败，抛出异常
            }
        };
    }
}
