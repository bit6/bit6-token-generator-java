package com.bit6.auth;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;

public class TokenGenerator {

    private String apiKey;
    private String apiSecret;

    public TokenGenerator (String key, String secret) {
        apiKey = key;
        apiSecret = secret;
    }

    public String createToken(String[] identities) {
        JWTSigner.Options options = new JWTSigner.Options();
        options.setAlgorithm(Algorithm.HS256);
        // Current timestamp
        options.setIssuedAt(true);
        // Expiration - 10 minutes
        options.setExpirySeconds(10 * 60);

        // JWT claims
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("aud", apiKey);
        // Primary identity
        String primary = identities[0];
        claims.put("sub", primary);
        // Handle additional identities
        if (identities.length > 1) {
            String [] arr = new String[identities.length - 1];
            System.arraycopy(identities, 1, arr, 0, arr.length);
            claims.put("identities", arr);
        }

        // Generate the token
        JWTSigner jwtSigner = new JWTSigner(apiSecret);
        String token = jwtSigner.sign(claims, options);
        return token;
    }
}
