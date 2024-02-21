package org.hrms.utility;

import java.util.Arrays;
import java.util.UUID;

public class CodeGenerator {

    /*
     * generateCode() metodunda UUID üzerinden randomUUID() metodunu kullanarak rastgele String bir randomCode oluşturuyoruz.
     * Daha sonra bu String ifadeyi split() metodunu kullanarak - olan karakterlere göre ayırıyoruz ve bir String dizisi oluşturuyoruz.
     * Bu String dizisini stream'e çevirip forEach() metoduyla her elemanını(item) dolaşıyoruz.
     * StringBuilder ile oluşturduğumuz activationCode içinde append() metodunu kullanarak her elemanın(item) ilk karakterini alarak activationCode'a ekliyoruz.
     */
    public static String generateCode(){
        String randomCode = UUID.randomUUID().toString();
        System.out.println(randomCode);
        String[] split = randomCode.split("-");

        StringBuilder activationCode = new StringBuilder();
        Arrays.stream(split).forEach(item -> activationCode.append(item.charAt(0)));
        System.out.println(activationCode);

        return activationCode.toString();
    }

}
