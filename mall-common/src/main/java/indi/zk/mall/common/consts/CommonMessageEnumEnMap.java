package com.iwhalecloud.retail.common.consts;

import java.util.HashMap;
import java.util.Map;

public final class CommonMessageEnumEnMap {

    private CommonMessageEnumEnMap() { }

    private static final String UNKNOWN_USER = "The user ID cannot be empty";

    public static final Map<String, String> enMap = new HashMap<>();



    static {

        enMap.put("入参不能为空", "The input parameter cannot be empty");
        enMap.put("配置项showUrl不能为空", "The configuration item showUrl cannot be empty");
        enMap.put("文件上传失败", "File upload is failed");
        enMap.put("目录名称不能为空", "The category name cannot be empty");
        enMap.put("目录名称已经存在", "The category name already exists");
        enMap.put("目录标识不能为空", "The category ID cannot be empty");
        enMap.put("目录不存在", "The category does not exist");
        enMap.put("目录存在子目录，不能删除", "The category contains subcategory, and cannot be deleted");
        enMap.put("目录下存在问题，不能删除", "There is a problem in the category, and cannot be deleted");
        enMap.put("问题标识不能为空", "The problem ID cannot be empty");
        enMap.put("问题不存在", "The problem does not exist");
        enMap.put("关联问题不存在", "The related problem does not exist");
        enMap.put("问题不能为空", "Problem topic cannot be empty");
        enMap.put("目录不能为空", "Problem category cannot be empty");
        enMap.put("问题已经存在", "The same problem already exists");
        enMap.put("存在已经有关联关系的问题", "The same related problem already exists");
        enMap.put("问题失效", "The problem is expired");
        enMap.put("答案标识不能为空", "The answer ID cannot be empty");
        enMap.put("答案不存在", "The answer does not exist");
        enMap.put("答案摘要不能为空", "The answer summary cannot be empty");
        enMap.put("模板ID不能为空", "The template ID cannot be empty");
        enMap.put("模板编码不能为空", "The template code cannot be empty");
        enMap.put("模板通知类型不能为空", "The template notification type cannot be empty");
        enMap.put("模板编码已经存在", "The template codes already exists");
        enMap.put("模板不存在", "The template does not exist");
        enMap.put("通知类型ID不能为空", "The notification type ID cannot be empty");
        enMap.put("通知内容ID不能为空", "The notification content ID cannot be empty");
        enMap.put("用户账号为空", "The user account is empty");
        enMap.put("密码为空", "The password is empty");
        enMap.put("账号或者密码错误", "The user account or password is wrong");
        enMap.put("验证码不正确", "The verification code is incorrect");
        enMap.put("验证码已过期", "The verification code is expired");
        enMap.put("调用微信接口异常", "Invoking WeChat API is error");
        enMap.put("微信通知模板不存在", "The WeChat notification template does not exist");
        enMap.put("微信通知消息不存在", "The WeChat notification message does not exist");
        enMap.put("微信通知消息已发送", "The WeChat notification message is already sent");
        enMap.put("站内信标识符不能为空", "The message ID cannot be empty");
        enMap.put("当前页不能为空", "The current page cannot be empty");
        enMap.put("每页显示条数不能为空", "The displayed items per page cannot be empty");
        enMap.put("商品标识符错误", "The offer ID is error");
        enMap.put("商品不存在", "The offer does not exist");
        enMap.put("商品内容不存在", "The offer content does not exist");
        enMap.put("存在上架时间不在货架期内", "The offer's shelf time is out of the shelf date");
        enMap.put("商品已失效", "The offer is expired");
        enMap.put("交易流水不能为空", "The transaction flow cannot be empty");
        enMap.put("目录类型不能为空", "The category type cannot be empty");
        enMap.put("目录类型不正确", "The category type is incorrect");
        enMap.put("目录名称字数超过上限", "The numbers of category name are out of the limit");
        enMap.put("父目录不存在", "The parent category does not exist");
        enMap.put("目录下存在销售目录，不能删除", "The category contains sales subcategory, and cannot be deleted");
        enMap.put("成员中含有已发布商品，无法删除", "The items contain published offers, that cannot be deleted");
        enMap.put("成员中没有可删除的商品，请选择", "There are no deletable offers in the items, please select");
        enMap.put("标识错误", "The identification ID is error");
        enMap.put("上架日期已经存在", "The offer's shelf time already exists");
        enMap.put("上架日期不在货架期内", "The offer's shelf time is out of the shelf date");
        enMap.put("上架方式错误", "The offer's shelf type is error");
        enMap.put("状态错误,无法操作", "The status is error, it is unable to operate");
        enMap.put("属性标识符不能为空", "The attribute ID cannot be empty");
        enMap.put("属性标识符错误", "The attribute ID is error");
        enMap.put("属性值不能为空", "The attribute value cannot be empty");
        enMap.put("商品名称不能为空", "The offer name cannot be empty");
        enMap.put("商品编码不能为空", "The offer code cannot be empty");
        enMap.put("产品编码不能为空", "The product code cannot be empty");
        enMap.put("商品类型不能为空", "The offer type cannot be empty");
        enMap.put("商品编码已存在", "The offer code already exists");
        enMap.put("管理目录不能为空", "The management category cannot be empty");
        enMap.put("销售目录不能为空", "The sales category cannot be empty");
        enMap.put("商品中含有已存在的商品关系", "There are exclusive relationship between different offers");
        enMap.put("A端商品ID或商品关系类型为空", "The source offer ID or offer relationship type is empty");
        enMap.put("所要删除的商品关系不存在", "The offer relationship to be deleted does not exist");
        enMap.put("内容标识不能为空", "The content ID cannot be empty");
        enMap.put("商品内容类型不能为空", "The offer content type cannot be empty");
        enMap.put("商品内容类型不存在", "The offer content type does not exist");
        enMap.put("目标商品不能为空", "The target offer cannot be empty");
        enMap.put("销售规则标识不能为空", "The sales rule ID cannot be empty");
        enMap.put("销售规则对象标识不能为空", "The sales rule object ID cannot be empty");
        enMap.put("渠道编码不能为空", "The channel code cannot be empty");
        enMap.put("用户信息查询异常", "The user information query is abnormal");
        enMap.put("属性编码已经存在", "The attribute code already exists");
        enMap.put("产品库存不足", "The product inventory is insufficient");
        enMap.put("产品不存在", "The product does not exist");
        enMap.put("商品数量不能为空", "The offer quantity cannot be empty");
        enMap.put("日期转换错误", "The date format conversion is error");
        enMap.put("父级行政代码为空", "The parent administrative code is empty");
        enMap.put("层级标识为空", "The hierarchical ID is empty");
        enMap.put("店铺等级名称已存在", "The store level name already exists");
        enMap.put("店铺等级标识符为空", "The store level ID is empty");
        enMap.put("店铺等级标识符错误", "The store level ID is error");
        enMap.put("店铺等级已绑定店铺", "The store level is bound with the store");
        enMap.put("创建用户标识符错误", "The staff ID creation is error");
        enMap.put("更新用户标识符错误", "The staff ID update is error");
        enMap.put("店铺名称不能为空", "The store name cannot be empty");
        enMap.put("店铺名称已存在", "The store name already exists");
        enMap.put("店铺标识不能为空", "The store ID cannot be empty");
        enMap.put("店铺标识错误", "The store ID is error");
        enMap.put("店主不能为空", "The store owner cannot be empty");
        enMap.put("是否自营店标识为空", "The self-owned store flag is empty");
        enMap.put("是否实体店标识为空", "The physical store flag is empty");
        enMap.put("店铺不存在", "The store does not exist");
        enMap.put("店铺有子店铺不允许删除", "The store has child store, and cannot be deleted");
        enMap.put("店铺适用规则规则不能为空", "The store applicable rules cannot be empty");
        enMap.put("店铺目录不存在", "The store category does not exist");
        enMap.put("店铺目录下含有子节点", "The store category has child node");
        enMap.put("店铺目录下含有有效商品", "The store category has effective offer");
        enMap.put("对象类型或对象标志符错误", "The object type or object ID is error");
        enMap.put("操作类型不能为空", "The operation type cannot be empty");
        enMap.put("队列不能为空", "The queue cannot be empty");
        enMap.put("消息标识不能为空", "The message ID cannot be empty");
        enMap.put("Topic不能为空", "The topic cannot be empty");
        enMap.put("Exchange不能为空", "The exchange cannot be empty");
        enMap.put("消息体不能为空", "The message content cannot be empty");
        enMap.put("消息不存在", "The message does not exist");
        enMap.put("消息不可重处理", "The message cannot be reprocessed");
        enMap.put("敏感词不为空", "The sensitive word is not empty");
        enMap.put("敏感词已经存在", "The sensitive word already exists");
        enMap.put("敏感词标识不为空", "The sensitive word ID is not empty");
        enMap.put("敏感词不存在", "The sensitive word does not exist");
        enMap.put("店铺状态不能为空", "The store status cannot be empty");
        enMap.put("商品品牌不存在", "The offer brand does not exist");
        enMap.put("批量删除中含有异常状态商品品牌,或商品品牌不存在", "Batch delete contains the offer brand with abnormal status, or offer brand does not exist");
        enMap.put("属性标识符不存在", "The attribute ID does not exist");
        enMap.put("租户标识不能为空", "The tenant ID cannot be empty");
        enMap.put("租户标识错误", "The tenant ID is error");
        enMap.put("目录标识集合为空", "The category ID collection is empty");
        enMap.put("SPU名称为空", "The SPU name is empty");
        enMap.put("SPU标识符为空", "The SPU ID is empty");
        enMap.put("SPU标识符错误", "The SPU ID is error");
        enMap.put("更新用户为空", "The update staff ID is empty");
        enMap.put("SKU标识符为空", "The SKU ID is empty");
        enMap.put("规则为空", "The rule is empty");
        enMap.put("SKU属性列表为空", "The SKU attribute list is empty");
        enMap.put("SKU属性标识符列表为空", "The SKU attribute ID list is empty");
        enMap.put("关联商品为空", "The associated offer is empty");
        enMap.put("关联商品SKU属性不匹配", "The associated offer SKU attribute does not match");
        enMap.put("创建用户为空", "The created staff is empty");
        enMap.put("店铺状态为空", "The store status is empty");
        enMap.put("用户不能为空", "The user cannot be empty");
        enMap.put("微信标识符获取错误", "The WeChat code is error");
        enMap.put("商品标识符为空", "The offer ID is empty");
        enMap.put("权益编码不能为空", "The benefits code cannot be empty");
        enMap.put("权益实例不能为空", "The benefits instance cannot be empty");
        enMap.put("优惠券规格标识符不能为空", "The coupon specification ID cannot be empty");
        enMap.put("支付类型不能为空", "The payment type cannot be empty");
        enMap.put("支付渠道不能为空", "The payment channel cannot be empty");
        enMap.put("油站编号不能为空", "The station number cannot be empty");
        enMap.put("油号编号不能为空", "The oil number cannot be empty");
        enMap.put("对象类型不能为空", "The object type cannot be empty");
        enMap.put("对象标志符不能为空", "The object ID cannot be empty");
        enMap.put("操作动作不能为空", "The operation action cannot be empty");
        enMap.put("页面标识为空", "The page ID is empty");
        enMap.put("订单号为空", "The order ID is empty");
        enMap.put("用户不存在", "The user does not exist");
        enMap.put("手机号为空", "The mobile number is empty");
        enMap.put("短信验证码为空", "The SMS verification code is empty");
        enMap.put("用户来源为空", "The user source is empty");
        enMap.put("用户保存失败", "The user saves failed");
        enMap.put("用户昵称为空", "The user nickname is empty");
        enMap.put("用户性别为空", "The user sex is empty");
        enMap.put("用户年龄为空", "The user age is empty");
        enMap.put("用户生日为空", "The user birthdate is empty");
        enMap.put("车辆品牌、型号、车牌、车系、类型、默认车辆标记不能全为空", "The car brand, model, license number, car series, type and default car mark cannot be all empty");
        enMap.put("车辆品牌、型号、车牌、车系、类型、默认车辆标记不能为空", "The car brand, model, license number, car series, type and default car mark cannot be empty");
        enMap.put("车辆品牌标志为空", "The car brand logo is empty");
        enMap.put("车辆规格标志为空", "The car specification ID is empty");
        enMap.put("操作失败,默认车辆不能为空", "Operation failed, the default car cannot be empty");
        enMap.put("用户未登陆", "User is not login");
        enMap.put("券标志为空", "Ticket ID is empty");
        enMap.put("用户当前位置纬度为空", "The user's current latitude is empty");
        enMap.put("用户当前位置经度为空", "The user's current longitude is empty");
        enMap.put("分页大小为空", "The paging size is empty");
        enMap.put("请求失败，请稍后重试", "The request is failed, please try again later");
        enMap.put("卡券不存在", "The ticket does not exist");
        enMap.put("未查询到系统参数SHOP_PICURL_MAPPING", "The system parameter of SHOP_PICURL_MAPPING was not queried");
        enMap.put("权益商品已领取", "The benefits offer has been already received");
        enMap.put("所要领取的权益商品不存在", "The benefits offer to be received does not exist");
        enMap.put("品牌名称为空", "The brand name is empty");
        enMap.put("品牌介绍为空", "The brand introduction is empty");
        enMap.put("品牌图片为空", "The brand image is empty");
        enMap.put("未查询到发票信息", "No invoice information was found");
        enMap.put("手机号不能为空", "The mobile number cannot be empty");
        enMap.put("江苏资费类型没有配置相应商品编码", "The tariff type is not configured with the corresponding offer code");
        enMap.put("操作类型错误", "The operation type is error");
        enMap.put("资费类型错误", "The tariff type is error");
        enMap.put("一级渠道不能为空", "The first channel cannot be empty");
        enMap.put("二级渠道不能为空", "The second channel cannot be empty");
        enMap.put("验签校验错误", "The verification is error");
        enMap.put("不存在合法的优惠券，请检查优惠券的货架期、状态、剩余数量是否合法", "There is no valid coupon, please check whether the shelf date, status and remaining quantity of the coupon are valid or not");
        enMap.put("优惠券剩余数量不足，无法领取", "The remaining coupon is not enough to receive");
        enMap.put("优惠券标识与优惠券规格标识不能同时为空", "The coupon ID and coupon specification ID cannot be empty at the same time");
        enMap.put("找不到相应的优惠券", "No corresponding coupon can be found");
        enMap.put("优惠券标识不能为空", "The coupon specification ID cannot be empty");
        enMap.put("请求参数不正确, requestId必填", "The request parameter is incorrect, the RequestID must be filled in");
        enMap.put("优惠券不存在", "The coupon does not exist");
        enMap.put("当前优惠券不可叠加使用", "The current coupons cannot be used in conjunction with each other");
        enMap.put("请求参数不能为空", "The request parameter cannot be empty");
        enMap.put("请求集合不能为空", "The request list cannot be empty");
        enMap.put("用户id不能为空", UNKNOWN_USER);
        enMap.put("请求标识不能为空", "The request ID cannot be empty");
        enMap.put("渠道不能为空", "The user channel cannot be empty");
        enMap.put("源优惠券类型标志为空", "The source coupon relationship type ID is empty");
        enMap.put("目标优惠券类型标志为空", "The target coupon relationship type ID is empty");
        enMap.put("优惠券关系类型为空", "The coupon relationship type is empty");
        enMap.put("优惠券关系类型标识为空", "The coupon relationship type ID is empty");
        enMap.put("优惠券规格下存在优惠券实例，无法删除", "A coupon specification has coupon instances, and cannot be deleted");
        enMap.put("相同的优惠券类型关系已经存在", "The same coupon type relationship already exists");
        enMap.put("优惠券数量不合法", "The number of coupons is invalid");
        enMap.put("优惠券集合为空", "The coupon list is empty");
        enMap.put("订单id为空", "The order ID is empty");
        enMap.put("原订单id为空", "The original order ID is empty");
        enMap.put("优惠券新增订单号失败，请检查是否部分优惠券已使用、失效或者不存在", "The coupon new added order number is failed, please check if some coupons have been used, expired or do not exist");
        enMap.put("优惠券修改订单号失败，请检查是否部分优惠券不存在或者原订单号不匹配", "The coupon modification order number is failed, please check if some coupons do not exist or the original order number does not match");
        enMap.put("部分优惠券不存在或者用户id不匹配", "Some coupons don't exist or user IDs don't match");
        enMap.put("优惠券不合法", "The coupon is invalid");
        enMap.put("优惠券规格不存在", "The coupon specification do not exist");
        enMap.put("订购商品不能为空", "The ordered offer cannot be empty");
        enMap.put("请求对象为空", "The request object is empty");
        enMap.put("目录名称为空", "The catalog name is empty");
        enMap.put("目录Id为空", "The catalog ID is empty");
        enMap.put("此目录存在子目录不可删除", "The catalog exists sub-catalog that cannot be deleted");
        enMap.put("此目录下存在内容不可删除", "The contents in this catalog cannot be deleted");
        enMap.put("同层级目录已存在相同名称目录", "The catalog name is already existed under the same hierarchy catalog");
        enMap.put("父目录下已存在相同名称目录", "The catalog name is already existed under the parent catalog");
        enMap.put("已存在同名标签", "The tag's name already exists");
        enMap.put("标签名称为空", "The tag name is empty");
        enMap.put("标签说明为空", "The tag description is empty");
        enMap.put("标签ID为空", "The tag ID is empty");
        enMap.put("regionId不能为空", "The RegionID cannot be empty");
        enMap.put("删除标签失败，因为它已经被其他内容使用", "The tag is failed to delete, as it has been already used by other contents");
        enMap.put("删除标签失败，因为它已经被其他商品使用", "The tag is failed to delete, as it has been already used by other offers");
        enMap.put("商城模板ID为空", "The marketplace template ID is empty");
        enMap.put("商城模板删除失败，因为它已经被其他页面引用", "The marketplace template is failed to delete, as it has already referenced by other pages");
        enMap.put("商城模板不存在", "The marketplace template does not exist");
        enMap.put("模板校验失败，请检查相同主题、设备，渠道是否存在有效期重叠", "The template validation is failed, please check if there are duplicate validity for the same theme, device and channel");
        enMap.put("模板编码为空", "The template code is empty");
        enMap.put("商城模板页不存在", "The marketplace template page does not exist");
        enMap.put("模板主题不能为空", "The template theme cannot be empty");
        enMap.put("模板名称不能为空", "The template name cannot be empty");
        enMap.put("模板渠道不能为空", "The template channel cannot be empty");
        enMap.put("模板适用设备不能为空", "The template applicable device cannot be empty");
        enMap.put("模板生效日期不能为空", "The template effective date cannot be empty");
        enMap.put("模板失效日期不能为空", "The template expired date cannot be empty");
        enMap.put("赞或者踩类型不能为空", "The useful type or useless type cannot be empty");
        enMap.put("内容标识符不能为空", "The content ID cannot be empty");
        enMap.put("内容标识符错误", "The content ID is error");
        enMap.put("赞数量不能为空", "The useful numbers cannot be empty");
        enMap.put("踩数量不能为空", "The useless numbers cannot be empty");
        enMap.put("RGB不能为空", "The RGB cannot be empty");
        enMap.put("创建用户不能为空", "The created staff ID cannot be empty");
        enMap.put("更新用户不能为空", "The update staff ID cannot be empty");
        enMap.put("活动标识不能为空", "The campaign ID cannot be empty");
        enMap.put("商品标识不能为空", "The offer ID cannot be empty");
        enMap.put("用户标识不能为空", UNKNOWN_USER);
        enMap.put("用户号码不能为空", "The user mobile number cannot be empty");
        enMap.put("用户昵称不能为空", "The user nickname cannot be empty");
        enMap.put("活动不存在，未生效或者未发布", "The activity does not exist, not be effective or published");
        enMap.put("裂变活动不存在", "The fission activity does not exist");
        enMap.put("裂变活动价格不存在", "The fission activity price does not exist");
        enMap.put("商品不存在或者价格为空", "The offer does not exist or the price is empty");
        enMap.put("活动类型不能为空", "The activity type cannot be empty");
        enMap.put("渠道类型不能为空", "The channel type cannot be empty");
        enMap.put("活动不存在", "The campaign activity does not exist");
        enMap.put("活动与商品不匹配", "The campaign activity does not match the offer");
        enMap.put("活动与商品类型不匹配", "The campaign activity does not match the offer type");
        enMap.put("活动与渠道不匹配", "The campaign activity does not match the channel");
        enMap.put("活动未发布", "The campaign activity is not published");
        enMap.put("活动尚未生效", "The campaign activity is not effective");
        enMap.put("活动已结束", "The campaign activity is finished");
        enMap.put("非团购和砍价活动", "Non group buying and bargain activity");
        enMap.put("非砍价活动", "Non bargain activity");
        enMap.put("活动组不存在", "The activity group does not exist");
        enMap.put("不能加入该活动组", "Cannot join the activity group");
        enMap.put("活动组未开团", "The activity group is not open");
        enMap.put("活动组已成团", "The activity group has been finished");
        enMap.put("活动组已取消", "The activity group has been cancelled");
        enMap.put("已参加活动", "Has participated the campaign activity");
        enMap.put("存在未完成的活动", "Unfinished campaign activity exists");
        enMap.put("活动已完成不能被取消", "The campaign activity is completed, and cannot be cancelled");
        enMap.put("活动已失效", "The campaign activity is expired");
        enMap.put("活动组团标识不能为空", "The campaign group ID cannot be empty");
        enMap.put("已加入团", "Has joined the group");
        enMap.put("已帮忙砍价", "Has already helped to bargain");
        enMap.put("活动人数已满", "The number of participants in the campaign is full");
        enMap.put("活动编码已经存在", "Activity code already exists");
        enMap.put("无法加入团", "Unable to join the group");
        enMap.put("砍价活动已存在订单", "Bargaining activity already exists in the order");
        enMap.put("砍价活动组长信息不存在", "Bargaining activity leader information does not exist");
        enMap.put("砍价异常", "Abnormal bargaining");
        enMap.put("活动标识和用户信息不匹配", "The campaign ID does not match the user information");
        enMap.put("活动标识和订单信息不匹配", "The campaign ID does not match the order information");
        enMap.put("团成员数量不正确", "The campaign group members quantity is incorrect");
        enMap.put("活动类型与入参不匹配", "The campaign type does not match the input parameter");
        enMap.put("裂变规格价格不能为空", "The campaign group price cannot be empty");
        enMap.put("砍价组团实例标识不能为空", "The campaign group instance ID cannot be empty");
        enMap.put("组团信息不能为空", "The campaign group information cannot be empty");
        enMap.put("该团已过期", "The campaign group is expired");
        enMap.put("团状态不正确", "The campaign group status is incorrect");
        enMap.put("参数异常", "Parameter abnormal");
        enMap.put("图片类别不能为空", "The picture type cannot be empty");
        enMap.put("标识不能为空", "The ID cannot be empty");
        enMap.put("图片url不能为空", "The picture URL cannot be empty");
        enMap.put("店铺等级名称为空", "The store level name is empty");
        enMap.put("店铺标识符为空", "The store ID is empty");
        enMap.put("名称不能为空", "The store name cannot be empty");
        enMap.put("成功", "Success");
        enMap.put("返回数据异常", "The response data is abnormal");
        enMap.put("Token生成失败", "Token generation failed");
        enMap.put("用户标识符不能为空", UNKNOWN_USER);
        enMap.put("文件不能为空", "The file cannot be empty");
        enMap.put("资源标识不能为空", "The resource ID cannot be empty");
        enMap.put("渠道标识不能为空", "The channel ID cannot be empty");
        enMap.put("订单标识不能为空", "The order ID cannot be empty");
        enMap.put("未登录或登录超时，请重新登录", "Not login or login out of time, please login again");
        enMap.put("站内信已读", "The message is already read");
        enMap.put("当前仅支持以下图片类型：jpg,gif,png,ico,bmp,jpeg", "Currently, only the following image types are supported: jpg, gif, png, ico, bmp, jpeg");
        enMap.put("记录日志失败，未查到表名", "Logging is failed, and no table name was found");
        enMap.put("记录日志失败，未查到唯一标识", "Logging is failed, and no unique ID was found");
        enMap.put("未查询到消息队列规则", "The message queue rule is not queried");
        enMap.put("日期格式转换错误", "The date format conversion is error");
        enMap.put("系统异常,请稍后再试", "The system is abnormal, please try again later");

    }
}
