package org.hrms.constant;

public class ApiUrls {
    //Endpoints sınıfında API'ye url vermek için static final değişkenler oluşturup onları kullanıyoruz.
    public static final String VERSION = "api/v1";
    public static final String AUTH = VERSION + "/auth";
    public static final String GUEST_REGISTER = "/guest-register";
    public static final String COMPANY_REGISTER = "/company-register";
    public static final String LOGIN = "/login";
    public static final String USER_ACTIVE = "/user-active";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String DELETE_BY_ID = "/delete-by-id/{id}";
    public static final String FIND_ALL = "/find-all";
    public static final String FIND_BY_ID = "/find-by-id/{id}";
    public static final String ACTIVATE = "/activate";
    public static final String UPDATE_PASSWORD = "/update-password";
    public static final String SAVE="/save";
    public static final String UPDATE="/update";
}
