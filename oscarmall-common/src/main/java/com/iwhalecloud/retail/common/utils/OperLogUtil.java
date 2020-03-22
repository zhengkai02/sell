package com.iwhalecloud.retail.common.utils;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.OperObjDetail;
import com.iwhalecloud.retail.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/26 <br>
 * @see com.iwhalecloud.retail.common.utils <br>
 * @since V9.0C<br>
 */
@Slf4j
public final class OperLogUtil {

    private OperLogUtil() { }

    public static final String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";


    public static List<OperObjDetail> getOperInfoList(Class<?> classType, Object newObj, Object oldObj) throws BaseException {

        Class<?> classType2 = classType;

        List<OperObjDetail> operDetailList = new ArrayList<>();
        OperObjDetail operDetail;

        //获取class及其父类的所有属性
        List<Field> fieldList = new ArrayList<>();
        String tableName = getTableName(classType2);
        while (classType2 != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(classType2.getDeclaredFields())));
            classType2 = classType2.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);


        String newFieldValue;
        String oldFieldValue;

        Long pkId = getPkId(oldObj, fields);

        for (Field field : fields) {
            String fieldName = field.getName();
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }

            field.setAccessible(true);
            newFieldValue = getFieldValue(field, newObj);
            oldFieldValue = getFieldValue(field, oldObj);

            if (hasChangeField(newFieldValue, oldFieldValue)) {
                operDetail = new OperObjDetail();
                operDetail.setOperDetailId(UidGeneator.getUID());
                operDetail.setColumnName(field.getName());
                operDetail.setNewValue(newFieldValue);
                operDetail.setOldValue(oldFieldValue);
                operDetail.setTableName(tableName);
                operDetail.setPkId(pkId);
                operDetailList.add(operDetail);
            }
        }
        return operDetailList;
    }

    private static String getTableName(Class<?> classType) throws BaseException {
        TableName tableName = classType.getAnnotation(TableName.class);
        if (null == tableName || StringUtils.isEmpty(tableName.value())) {
            throw new BaseException(CommonBaseMessageCodeEnum.TABLE_NAME_NOT_EXIST);
        }

        return tableName.value();
    }

    private static Long getPkId(Object oldObj, Field[] fields) throws BaseException {
        String pkId = null;
        for (Field field : fields) {
            if (null != field.getAnnotation(TableId.class)) {
                pkId = getFieldValue(field, oldObj);
                break;
            }
        }

        if (StringUtils.isEmpty(pkId)) {
            throw new BaseException(CommonBaseMessageCodeEnum.TABLE_PK_NOT_EXIST);
        }

        return Long.valueOf(pkId);
    }

    /**
     *
     * Description:从dto中取出某一个字段的值 <br>
     *
     * @author yu.xiaoyan1<br>
     * @taskId <br>
     * @param field Field
     * @param obj Object
     * @return String
     */
    private static String getFieldValue(Field field, Object obj) {
        String fieldValue = null;
        Class<?> type = field.getType();
        try {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                if (type.equals(Date.class)) {
                    fieldValue = DateUtil.formatString((Date) field.get(obj), DATE_FORMAT_TIME);
                }
                else {
                    fieldValue = field.get(obj).toString();
                }
            }
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            log.error(e.getMessage());
        }

        return fieldValue;
    }

    private static boolean hasChangeField(String newFieldValue, String oldFieldValue) {
        return (StringUtils.isEmpty(newFieldValue) && StringUtils.isNotEmpty(oldFieldValue)) ||
                (null != newFieldValue && !newFieldValue.equals(oldFieldValue));
    }

}
