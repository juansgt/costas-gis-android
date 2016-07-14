package com.toponort.costasgismodel.service.catalogservice;

import com.google.gson.reflect.TypeToken;
import com.toponort.asyncextensions.AsyncTaskDelegate;
import com.toponort.asyncextensions.IAsyncDelegate;
import com.toponort.costasgismodel.entities.Municipio;
import com.toponort.costasgismodel.entities.Provincia;
import com.toponort.webserviceapi.restful.IRestConnection;
import com.toponort.webserviceapi.restful.RestConnectionImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Juan on 14/07/2016.
 */
public class CatalogServiceImpl implements ICatalogService
{
    public static final String HOST = "http://costasgis.shopinshock.com/";
    protected IAsyncDelegate delegate;

    public CatalogServiceImpl(){}

    public CatalogServiceImpl(IAsyncDelegate asyncDelegate)
    {
        delegate = asyncDelegate;
    }

    @Override
    public List<Provincia> findProvincias()
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Provincia>>(){}.getType();

        List<Provincia> result = restConnection.request(HOST + "catalogs/provincias", tipo);

        return result;
    }

    @Override
    public String findProvinciasAsync()
    {
        AsyncTaskDelegate asyncTaskDelegate = new AsyncTaskDelegate(delegate);
        try
        {
            return asyncTaskDelegate.execute(this, ICatalogService.FINDPROVINCIAS);
        }
        catch (NoSuchMethodException e)
        {
            return e.getMessage();
        }
    }

    @Override
    public List<Municipio> findMunicipiosByProvincia(long idProvincia)
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Municipio>>(){}.getType();

        List<Municipio> result = restConnection.request(HOST + "catalogs/municipios/provincia/" + idProvincia, tipo);

        return result;
    }

    @Override
    public String findMunicipiosByProvinciaAsync(long idProvincia)
    {
        AsyncTaskDelegate asyncTaskDelegate = new AsyncTaskDelegate(delegate);
        try
        {
            return asyncTaskDelegate.execute(this, ICatalogService.FINDMUNICIPIOSBYPROVINCIA, long.class, idProvincia);
        }
        catch (NoSuchMethodException e)
        {
            return e.getMessage();
        }
    }
}
