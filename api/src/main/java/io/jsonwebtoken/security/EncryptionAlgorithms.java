package io.jsonwebtoken.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Classes;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EncryptionAlgorithms {

    private static final String HMAC = "io.jsonwebtoken.impl.security.HmacAesEncryptionAlgorithm";
    private static final Class[] HMAC_ARGS = new Class[]{String.class, SignatureAlgorithm.class};

    private static final String GCM = "io.jsonwebtoken.impl.security.GcmAesEncryptionAlgorithm";
    private static final Class[] GCM_ARGS = new Class[]{String.class, int.class};

    private static EncryptionAlgorithm hmac(EncryptionAlgorithmName name, SignatureAlgorithm alg) {
        return Classes.newInstance(HMAC, HMAC_ARGS, name.getValue(), alg);
    }

    private static EncryptionAlgorithm gcm(EncryptionAlgorithmName name, int keyLen) {
        return Classes.newInstance(GCM, GCM_ARGS, name.getValue(), keyLen);
    }

    //prevent instantiation
    private EncryptionAlgorithms() {
    }

    public static final EncryptionAlgorithm A128CBC_HS256 = hmac(EncryptionAlgorithmName.A128CBC_HS256, SignatureAlgorithm.HS256);

    public static final EncryptionAlgorithm A192CBC_HS384 = hmac(EncryptionAlgorithmName.A192CBC_HS384, SignatureAlgorithm.HS384);

    public static final EncryptionAlgorithm A256CBC_HS512 = hmac(EncryptionAlgorithmName.A256CBC_HS512, SignatureAlgorithm.HS512);

    public static final EncryptionAlgorithm A128GCM = gcm(EncryptionAlgorithmName.A128GCM, 16);

    public static final EncryptionAlgorithm A192GCM = gcm(EncryptionAlgorithmName.A192GCM, 24);

    public static final EncryptionAlgorithm A256GCM = gcm(EncryptionAlgorithmName.A256GCM, 32);

    private static final Map<String, EncryptionAlgorithm> VALUES_BY_NAME;

    private static void put(Map<String,EncryptionAlgorithm> map, EncryptionAlgorithm alg) {
        map.put(alg.getName(), alg);
    }

    static {
        Map<String,EncryptionAlgorithm> map = new LinkedHashMap<>(6);
        put(map, A128CBC_HS256);
        put(map, A192CBC_HS384);
        put(map, A256CBC_HS512);
        put(map, A128GCM);
        put(map, A192GCM);
        put(map, A256GCM);
        VALUES_BY_NAME = java.util.Collections.unmodifiableMap(map);
    }

    public static EncryptionAlgorithm forName(String name) {
        EncryptionAlgorithm alg = VALUES_BY_NAME.get(name);
        if (alg == null) {
            throw new IllegalArgumentException("'" + name + "' " +
                "is not a specification standard JWE encryption algorithm name.");
        }
        return alg;
    }

    public static Collection<EncryptionAlgorithm> values() {
        return VALUES_BY_NAME.values();
    }
}
