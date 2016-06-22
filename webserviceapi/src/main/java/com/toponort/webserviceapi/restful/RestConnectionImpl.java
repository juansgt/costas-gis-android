package com.toponort.webserviceapi.restful;

import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Juan on 20/04/2015.
 */
public class RestConnectionImpl implements IRestConnection, ImageLoader.ImageListener
{
    private RequestMethodType _requestMethodType;
    private ContentType _contentType;
    private String _fromHeaderValue;
    private String _authorizationToken;

    public RestConnectionImpl(IRestConnection.RequestMethodType requestMethodType, IRestConnection.ContentType contentType)
    {
        this._requestMethodType = requestMethodType;
        this._contentType = contentType;
    }

    public RestConnectionImpl(RequestMethodType requestMethodType, ContentType contentType, String fromHeaderValue,
                                 String authorizationToken)
    {
        this._requestMethodType = requestMethodType;
        this._contentType = contentType;
        this._fromHeaderValue = fromHeaderValue;
        this._authorizationToken = authorizationToken;
    }

    @Override
    public void request(String uri)
    {
        this.request(uri, null, null);
    }

    public <T> T request(String uri, Type typeOfT)
    {
        T objectT = this.request(uri, typeOfT, null);

        return objectT;
    }

    public <T> T request(String uri, Type typeOfT, Object postParam)
    {
        Gson gson = new Gson();
        T objectT;

        if (typeOfT != null)
        {
            String response;
            if (postParam != null)
            {
                response = this.getResult(uri, gson.toJson(postParam));
            }
            else
            {
                response = this.getResult(uri, null);
            }
            objectT = gson.<T>fromJson(response, typeOfT);

            return objectT;
        }
        else
        {
            return null;
        }
    }

    private String getResult(String uri, String postParam)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            OutputStream outputStream;
            String result;
            httpURLConnection.setRequestMethod(_requestMethodType.toString());
            if (_contentType == ContentType.JSON)
            {
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
            }
            if (_contentType == ContentType.XML)
            {
                httpURLConnection.setRequestProperty("Content-Type", "application/xml");
            }
            if ((_fromHeaderValue != null) && !_fromHeaderValue.isEmpty())
            {
                httpURLConnection.setRequestProperty("From", _fromHeaderValue);
            }
            if ((_authorizationToken != null) && !_authorizationToken.isEmpty())
            {
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + _authorizationToken);
            }
            if (postParam != null)
            {
                httpURLConnection.setDoOutput(true);
//                Gson gson = new Gson();
//                String jsonPostParam = gson.toJson(postParam);
                byte[] outputBytes = postParam.getBytes("UTF-8");
                httpURLConnection.setFixedLengthStreamingMode(outputBytes.length);
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(outputBytes);
                outputStream.close();
            }
            if (httpURLConnection.getResponseCode() == 200)
            {
                InputStream inputStream = httpURLConnection.getInputStream();
                result = this.getStringFromInputStream(inputStream);
                return result;
            }
            else
            {
                throw new Exception("Status code != 200");
            }
        }
        catch (Exception e)
        {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        finally
        {
            httpURLConnection.disconnect();
        }
        return null;
    }

    // Deprecated, en algunas peticiones introducia caracteres al final del string que forma el json que hacia que estubiese mal formado.

//    private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
//    {
//        Reader reader = null;
//        reader = new InputStreamReader(stream);
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
//    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        String errorMessage = error.getMessage();
        System.out.print(errorMessage);
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
    {

    }
}
