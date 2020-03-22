package com.iwhalecloud.retail.common.consts;

public interface OrderDef {

     abstract class OrderType {

        private OrderType() { }

        /**
         * 订购
         */
        public static final Integer ORDER = 1;

        /**
         * 重订购
         */
        public static final Integer RE_ORDER = 2;

        /**
         * 团购
         */
        public static final Integer GROUP_ORDER = 3;

        /**
         * 取消
         */
        public static  final Integer CANCEL = 5;

        /**
         * 领取
         */
        public static final Integer RECEIVE = 6;
    }
}
