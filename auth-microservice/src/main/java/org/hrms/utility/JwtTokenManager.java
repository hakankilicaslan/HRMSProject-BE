package org.hrms.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.hrms.exception.AuthServiceException;
import org.hrms.exception.ErrorType;
import org.hrms.repository.enums.ERole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Optional;

/*
 * @Component anotasyonu, Spring Framework'te bileşen türünü işaretlemek için kullanılan genel bir anotasyondur.
 * Spring konteyneri tarafından yönetilen ve oluşturulan herhangi bir sınıfı Spring bileşeni(component) olarak işaretlemek için kullanılır.
 * Spring bileşenleri, genellikle Spring konteyneri tarafından yönetilir ve otomatik olarak kullanılır.
 * @Service, @Repository ve @Controller gibi diğer spesifik bileşen anotasyonları bünyesinde @Component anotasyonuna sahiptir.
 */
@Component
public class JwtTokenManager {

    /*
     * @Value annotasyonu, Spring uygulamalarında dış yapılandırma dosyalarından değerleri doğrudan enjekte etmek için kullanılır.
     * Parantez içinde yml dosyamızdaki yolu vererek o yola karşılık gelen değerleri SecretKey ve Issuer değişkenlerimize enjekte etmiş oluyoruz.
     */
    @Value("${authserviceconfig.secrets.secret-key}")
    String secretKey;
    @Value("${authserviceconfig.secrets.issuer}")
    String issuer;

    private final Long expirationTime=1000L*60*1; // 1 dakika boyunca token'imiz aktif olacak süre bittiğinde token'ın geçerliliği bitecek.

    //JWT sınıfı üzerinden create() metodunu çağırarak gerekli parametreleri veriyoruz ve geriye optional olarak String bir token dönüyoruz.
    public Optional<String> createToken(Long id) {
        try {
            return Optional.of(JWT.create()
                    .withAudience()
                    .withClaim("id", id)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis())) //Token'ın oluştuğu zaman
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) //Token'ın geçerlilik süresinin bittiği zaman
                    .sign(Algorithm.HMAC512(secretKey)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> createToken(Long id, ERole role){
        //TOKENDAN DOLAYI HATA ALIRSAN BURAYI DÜZELT...DIŞARIDA NULL TANIMLAYIP EN SON RETURN ETMEN GEREKEBİLİR.
        try{
            return Optional.of(JWT.create()
                    .withClaim("id",id)
                    .withClaim("role",role.toString())
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
                    .sign(Algorithm.HMAC512(secretKey)));
        }catch (Exception e){
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        }
    }

    public Optional<String> createToken(Long id, ERole role, String code){
        //TOKENDAN DOLAYI HATA ALIRSAN BURAYI DÜZELT...DIŞARIDA NULL TANIMLAYIP EN SON RETURN ETMEN GEREKEBİLİR.
        try{
            return Optional.of(JWT.create()
                    .withClaim("id",id)
                    .withClaim("role",role.toString())
                    .withClaim("code",code)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
                    .sign(Algorithm.HMAC512(secretKey)));
        }catch (Exception e){
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        }
    }

    //Token'ın doğrulunu kontrol ediyoruz. Oluşturduğumuz token'ı verify ederek null olup olmadığını kontrol ediyoruz.
    public Boolean verifyToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC512(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT==null){
                return false;
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    //Token içinden bilgi çıkarımı yapıyoruz. Eğer token boş değilse getClaim() metoduyla içinden id değerini çekiyoruz.
    public Optional<Long> decodeToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC512(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT==null){
                return Optional.empty();
            }

            Long id = decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    //Token içinden bilgi çıkarımı yapıyoruz. Eğer token boş değilse getClaim() metoduyla içinden id değerini çekiyoruz.
    public Optional<Long> getIdFromToken(String token){
        Algorithm algorithm=Algorithm.HMAC512(secretKey);
        JWTVerifier verifier= JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT= verifier.verify(token);

        if (decodedJWT==null){
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }

        Long id=decodedJWT.getClaim("id").asLong();
        return Optional.of(id);
    }

    //Token içinden bilgi çıkarımı yapıyoruz. Eğer token boş değilse getClaim() metoduyla içinden role değerini çekiyoruz.
    public Optional<String> getRoleFromToken(String token){
        Algorithm algorithm=Algorithm.HMAC512(secretKey);
        JWTVerifier verifier= JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT= verifier.verify(token);

        if (decodedJWT==null){
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }

        String  role=decodedJWT.getClaim("role").asString();
        return Optional.of(role);
    }

}