package com.toponort.webserviceapi.restful;

import java.lang.reflect.Type;

/**
 * Created by Juan on 20/04/2015.
 */
public interface IRestConnection
{
    public static enum RequestMethodType{GET, POST, PUT, DELETE};
    public static enum ContentType{JSON, XML};

    void request(String uri);
    <T> T request(String uri, Type typeOfT);
    <T> T request(String uri,  Type typeOfT, Object postParam);
}
