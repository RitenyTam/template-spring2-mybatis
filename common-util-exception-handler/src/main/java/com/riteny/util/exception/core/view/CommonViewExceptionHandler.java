package com.riteny.util.exception.core.view;

import com.riteny.util.exception.core.exception.CommonException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface CommonViewExceptionHandler<E extends CommonException> {

    /**
     * 儅API 抛出異常后，對該異常進行處理，並輸出格式化的返回值
     *
     * @param e 異常類型
     */
    void handler(E e, HttpServletRequest request, HttpServletResponse response);

    /**
     * 獲取繼承本類的接口設置的汎型 <E> 的類型
     *
     * @return 汎型<E> 的類型
     */
    default String getExceptionClass() {

        Class<?> clazz = this.getClass();

        Type[] interfaceTypes = clazz.getGenericInterfaces();

        for (Type interfaceType : interfaceTypes) {

            ParameterizedType pt = (ParameterizedType) interfaceType;

            if (pt.getRawType().getTypeName().equals(CommonViewExceptionHandler.class.getTypeName())) {

                Type[] actualTypeArguments = pt.getActualTypeArguments();

                return actualTypeArguments[0].getTypeName();
            }
        }
        return null;
    }
}
