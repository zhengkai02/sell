package indi.zk.mall.order.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private String productId;

    private Integer productQuantity;
}
