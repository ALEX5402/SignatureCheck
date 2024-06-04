package com.test;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

public class Startup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Start.check(this);
    }
}
class Start {
    static {
        System.loadLibrary("haha");
    }
    static native String dadawdawd();
    public static void check(final Context context) {
        try {
            String baseurl = dadawdawd();
            ffffffff.gggggggggggggggggggg(
                    context,
                    baseurl,
                    (String reason) -> {
                        Log.d("TAG", reason);
                    }
            );
        }catch (Exception errrr){
            errrr.printStackTrace();
            Log.d("TAG", errrr.getMessage());
        }
    }
}
class ffffffff {
    public static void gggggggggggggggggggg(Context context, String url,Consumer<String> onFailure) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    response -> {
                        try {
                            String hashid = response.getString("signature");
                            Log.i("hashid", hashid);
                            String ciificatehashid =  getAppCertificateSHA256(context);
                            if (ciificatehashid != null ){
                                Log.w("Cirtificate :" , ciificatehashid);
                                boolean check = ciificatehashid.equals(hashid);
                                if (!check){
                                 System.exit(0);
                                    Log.i("hashid", "not match");
                                }
                            }

                        } catch (JSONException e) {
                            onFailure.accept("Invalid response data: " + e.getMessage());
                        }
                    },
                    error -> {
                        Log.e("error", error.getMessage());
                        onFailure.accept("Network error: " + error.getMessage());
                    });

            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("error", "Internet connectivity not found: " + e.getMessage());
            onFailure.accept("Internet connectivity not found: " + e.getMessage());
        }
    }

    public static String getAppCertificateSHA256(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);
            } else {
                packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            }
            byte[] cert;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo != null && signingInfo.hasMultipleSigners()) {
                    cert = signingInfo.getApkContentsSigners()[0].toByteArray();
                } else {
                    cert = signingInfo.getSigningCertificateHistory()[0].toByteArray();
                }
            } else {
                cert = packageInfo.signatures[0].toByteArray();
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] sha256Digest = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte b : sha256Digest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("TAG", "Error while getting SHA-256 certificate digest", e);
        }
        return null;
    }
}