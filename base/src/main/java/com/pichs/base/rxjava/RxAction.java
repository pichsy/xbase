package com.pichs.base.rxjava;

/**
 * @author: wu
 */
public interface RxAction<T, R> {
    R run(T t);
}
