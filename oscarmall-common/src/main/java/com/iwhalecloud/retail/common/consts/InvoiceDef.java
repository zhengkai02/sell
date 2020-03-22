package com.iwhalecloud.retail.common.consts;

public interface InvoiceDef {

    public abstract static class InvoiceType {

        private InvoiceType() { }
        /**
         * 增值税普通发票（电子）
         */
        public static final Integer VAT_ELECTRONIC_INVOICE = 1;
    }

    public abstract static class ReceiveMethod {

        private ReceiveMethod() { }

        /**
         * 1:手工下载（电子）
         */
        public static final Integer DOWNLOAD = 1;

        /**
         * 2:邮箱接收（电子）
         */
        public static final Integer EMAIL = 2;

        /**
         * 3:邮寄（纸质）
         */
        public static final Integer SHIPMENT = 3;
    }

    public abstract static class ReceiveMethodName {


        private ReceiveMethodName() { }

        /**
         * 1:手工下载（电子）
         */
        public static final String DOWNLOAD = "手工下载";

        /**
         * 2:邮箱接收（电子）
         */
        public static final String EMAIL = "邮箱接收";

        /**
         * 3:邮寄（纸质）
         */
        public static final String SHIPMENT = "邮寄";
    }

    public abstract static class TitleType {

        private TitleType() { }

        /**
         *  1:企业发票
         */
        public static final Integer COMPANY = 1;

        /**
         *  2:个人/非企业单位
         */
        public static final Integer PERSONAL = 2;
    }

    public abstract static class ContentType {

        private ContentType() { }

        /**
         * 1：类目开票
         */
        public static final Integer CATEGORY = 1;
    }

    public abstract static class Category {

        private Category() { }

        /**
         * 1:合并订单开票
         */
        public static final Integer COMBINE_ORDER = 1;

        /**
         * 2:单个订单开票。默认为2
         */
        public static final Integer SINGLE_ORDER = 2;
    }

    public abstract static class BusinessSource {

        private BusinessSource() { }

        /**
         * 01：TSOP商城
         */
        public static final String TSOP = "01";
    }

    public abstract static class InvoiceState {

        private InvoiceState() { }

        /**
         * 3: 开票中
         */
        public static final Integer IN_PROCESS = 3;

        /**
         * 4: 已开票
         */
        public static final Integer FINISH = 4;

        /**
         * 5: 开票失败
         */
        public static final Integer FAIL = 5;

    }


}
