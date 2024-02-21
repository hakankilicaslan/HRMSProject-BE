package org.hrms.constant;

public class ApiUrls {
    //Endpoints sınıfında API'ye url vermek için static final değişkenler oluşturup onları kullanıyoruz.
    public static final String VERSION = "api/v1";
    public static final String GUEST = VERSION + "/guest";
    public static final String SAVE="/save";
    public static final String UPDATE="/update";
    public static final String DELETE_BY_ID = "/delete-by-id/{id}";
    public static final String FIND_ALL = "/find-all";
    public static final String FIND_BY_ID = "/find-by-id/{id}";
    public static final String FIND_BY_AUTH_ID = "/find-by-auth-id/{authId}";

}
