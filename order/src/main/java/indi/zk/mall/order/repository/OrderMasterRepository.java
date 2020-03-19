package indi.zk.mall.order.repository;

import indi.zk.mall.order.DO.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:38
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
