package com.local.common.slog.annotation;

import java.lang.annotation.*;

/**
 * Created by chen on 2018/10/26.
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SLog {
    /**
     * 类型，C、R、U、D，默认R
     *
     * @return
     */
    String type() default "R";
    /**
     * 描述
     *
     * @return
     */
    String tag();

    /**
     * 是否异步执行,默认为true
     *
     * @return true, 如果需要异步执行
     */
    boolean async() default true;
}