package com.bit6.auth;

import java.io.IOException;
import java.io.BufferedReader;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;
import java.io.PrintWriter;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet( urlPatterns = {"/auth"} )
public class SampleServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // In this example, POST body contains JSON
        // object with the identity URIs to use. In real life
        // the identities should be provided by your application code.

        // Convert request body to JSON object
        JSONObject json = readJsonFromRequest(req);
        // Convert JSON array with identities into Java array
        String[] arr = toStringArray(json.optJSONArray("identities"));
        if (arr == null || arr.length < 1) {
            resp.getWriter().println("Error: must have at least one identity");
            return;
        }

        // Read Bit6 API key and secret from environment variables
        // You can easily add them in code if necessary
        String key = System.getenv("BIT6_API_KEY");
        String secret = System.getenv("BIT6_API_SECRET");

        System.out.println("BIT6 API key=" + key + ", secret=" + secret);

        // Generate the token
        TokenGenerator tg = new TokenGenerator(key, secret);
        String token = tg.createToken(arr);

        // Response JSON
        JSONObject obj = new JSONObject();
        try {
            obj.put("ext_token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send response
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(obj);
        out.flush();
    }


    private JSONObject readJsonFromRequest(HttpServletRequest request)
            throws IOException {

        final StringBuilder sb = new StringBuilder();

        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        JSONObject o = null;
        try {
            o = new JSONObject(sb.toString());
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
        return o;
    }

    private String[] toStringArray(JSONArray items) {
        if (items == null) {
            return null;
        }
        String[] res = new String[items.length()];
        for(int i = 0; i < items.length(); i++){
            res[i] = items.optString(i);
        }
        return res;
    }
}
