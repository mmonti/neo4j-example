package com.dreamworks.example.support;

import com.google.common.base.CaseFormat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author mmonti
 */
public class IDGenerator implements IDGeneratorOperations {

    private static final String KEY = "::";
    private static final String SHA_256 = "SHA-256";

    /**
     * TODO: Maybe we can Externalize this.
     */
    private String separator = "@";

    /**
     *
     * @return
     */
    public String id() {
        return id((x) -> hashIt());
    }

    /**
     *
     * @param supply
     * @return
     */
    public String id(final Function<IDGeneratorOperations, String> supply) {
        return id(null, supply.apply(this), false, false);
    }

    /**
     *
     * @param type
     * @param supply
     * @return
     */
    public String postfixId(final String type, final Function<IDGeneratorOperations, String> supply) {
        return id(type, supply.apply(this), false, true);
    }

    /**
     *
     * @param type
     * @param supply
     * @param <T>
     * @return
     */
    public <T> String postfixId(final Class<T> type, final Function<IDGeneratorOperations, String> supply) {
        return id(type.getSimpleName(), supply.apply(this), false, true);
    }

    /**
     *
     * @param type
     * @param supply
     * @return
     */
    public String prefixId(final String type, final Function<IDGeneratorOperations, String> supply) {
        return id(type, supply.apply(this), false, true);
    }

    /**
     *
     * @param type
     * @param supply
     * @param <T>
     * @return
     */
    public <T> String prefixId(final Class<T> type, final Function<IDGeneratorOperations, String> supply) {
        return id(type.getSimpleName(), supply.apply(this), false, true);
    }

    /**
     *
     * @param type
     * @param hashedKey
     * @param prefix
     * @param postfix
     * @return
     */
    private String id(final String type, final String hashedKey, boolean prefix, boolean postfix) {
        final StringBuilder builder = new StringBuilder(hashedKey);
        if (prefix) {
            builder.insert(0, separator);
            builder.insert(0, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, type));
        }

        if (postfix) {
            builder.append(separator);
            builder.append(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, type));
        }

        return builder.toString();
    }

    /**
     *
     * @param keys
     * @return
     */
    public String hashIt(final Object ... keys) {
        if (keys == null || keys.length == 0) {
            return hash(uuid());
        }

        final String key = keys.length > 1 ? Arrays.asList(keys)
                .stream()
                .map(x -> x.toString())
                .collect(Collectors.joining(KEY)).toString() : keys[0].toString();

        return hash(key);
    }

    /**
     *
     * @return
     */
    public String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param rawBlockKey
     * @return
     */
    private String hash(final String rawBlockKey) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(rawBlockKey.getBytes());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }

        byte byteData[] = md.digest();

        // = Convert the byte to hex format.
        final StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

}