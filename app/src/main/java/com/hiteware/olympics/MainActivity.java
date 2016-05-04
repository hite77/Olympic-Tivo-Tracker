package com.hiteware.olympics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.security.KeyStore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Output";
    String html;

    private String decode(String url)
    {
        return url.replace("&amp;", "&");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//
    }

    String convertStreamToString(java.io.InputStream is) {
        try {
            return new java.util.Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

    private class TestHttpThread extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            if(params.length > 0){
                String url = params[0];
                String username = params[1];
                String password = params[2];

                try {
                    HttpClient httpClient = getNewHttpClient();

                    URL urlObj = new URL(url);
                    HttpHost host = new HttpHost(urlObj.getHost(), urlObj.getPort(), urlObj.getProtocol());
                    AuthScope scope = new AuthScope(urlObj.getHost(), urlObj.getPort());
                    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);

                    CredentialsProvider cp = new BasicCredentialsProvider();
                    cp.setCredentials(scope, creds);
                    HttpContext credContext = new BasicHttpContext();
                    credContext.setAttribute(ClientContext.CREDS_PROVIDER, cp);

                    HttpGet job = new HttpGet(url);
                    HttpResponse response = httpClient.execute(host,job,credContext);
                    StatusLine status = response.getStatusLine();
                    Log.d(MainActivity.TAG, status.toString());
                    String responseStr = EntityUtils.toString(response.getEntity());
//                    new parser().parse(responseStr);
                    html = Html.fromHtml(responseStr).toString();
                    // split up log....
                    if (responseStr.length() > 4000) {
                        Log.v(TAG, "sb.length = " + responseStr.length());
                        int chunkCount = responseStr.length() / 4000;     // integer division
                        for (int i = 0; i <= chunkCount; i++) {
                            int max = 4000 * (i + 1);
                            if (max >= responseStr.length()) {
                                Log.v(TAG, "chunk " + i + " of " + chunkCount + ":" + responseStr.substring(4000 * i));
                            } else {
                                Log.v(TAG, "chunk " + i + " of " + chunkCount + ":" + responseStr.substring(4000 * i, max));
                            }
                        }
                    } else {
                        Log.v(TAG, responseStr.toString());
                    }


//                    Log.d(MainActivity.TAG, responseStr);
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}
