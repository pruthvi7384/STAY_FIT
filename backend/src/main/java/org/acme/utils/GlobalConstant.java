package org.acme.utils;

public class GlobalConstant {
    public static final String MESSAGE = "MESSAGE";
    public static final String STATUS = "STATUS";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String PROFILENOTFOUNDMESSAGE = "USER PROFILE IS NOT FOUND";

    private GlobalConstant() {
        throw new IllegalStateException();
    }
}
