package com.paypal.bfs.test.employeeserv.InputSanitizer;

public interface Sanitizer<T> {

    public T sanitize(T t);
}
