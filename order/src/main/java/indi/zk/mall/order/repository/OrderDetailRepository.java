package indi.zk.mall.order.repository;

import indi.zk.mall.order.DO.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:37
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
