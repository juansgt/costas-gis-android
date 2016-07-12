package com.toponort.costasgismodel.service.ocupationservice;

import com.google.gson.reflect.TypeToken;
import com.toponort.asyncextensions.AsyncTaskDelegate;
import com.toponort.asyncextensions.IAsyncDelegate;
import com.toponort.asyncextensions.TaskResult;
import com.toponort.costasgismodel.entities.Ocupation;
import com.toponort.webserviceapi.restful.IRestConnection;
import com.toponort.webserviceapi.restful.RestConnectionImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Juan on 22/06/2016.
 */
public class OcupationServiceImpl implements IOcupationService, IAsyncDelegate
{
    public static final String HOST = "http://costasgis.shopinshock.com/";
    protected IAsyncDelegate delegate;

    public OcupationServiceImpl(){}

    public OcupationServiceImpl(IAsyncDelegate asyncDelegate)
    {
        delegate = asyncDelegate;
    }

    @Override
    public Ocupation findOcupation(long idOcupacion)
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);

        Ocupation ocupation = restConnection.request(HOST + "ocupations/" + idOcupacion, Ocupation.class);

        return ocupation;
    }

    @Override
    public String findOcupationAsync(long idOcupacion)
    {
        AsyncTaskDelegate asyncTaskDelegate = new AsyncTaskDelegate(delegate);
        try
        {
            return asyncTaskDelegate.execute(this, IOcupationService.FIND_OCUPATION, long.class, idOcupacion);
        }
        catch (NoSuchMethodException e)
        {
            return e.getMessage();
        }
    }

    @Override
    public List<Ocupation> findOcupations()
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Ocupation>>(){}.getType();

        List<Ocupation> result = restConnection.request(HOST + "ocupations/", tipo);

        return result;
    }

    @Override
    public String findOcupationsAsync()
    {
        AsyncTaskDelegate asyncTaskDelegate = new AsyncTaskDelegate(delegate);
        try
        {
            return asyncTaskDelegate.execute(this, IOcupationService.FIND_OCUPATIONS);
        }
        catch (NoSuchMethodException e)
        {
            return e.getMessage();
        }
    }

    @Override
    public List<Ocupation> findOcupationsByProvincia(long idProvincia)
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Ocupation>>(){}.getType();

        List<Ocupation> result = restConnection.request(HOST + "ocupations/provincia/" + idProvincia, tipo);

        return result;
    }

    @Override
    public String findOcupationsByProvinciaAsync(long idProvincia)
    {
        AsyncTaskDelegate asyncTaskDelegate = new AsyncTaskDelegate(delegate);
        try
        {
            return asyncTaskDelegate.execute(this, IOcupationService.FIND_OCUPATIONSBYPROVINCIA, long.class, idProvincia);
        }
        catch (NoSuchMethodException e)
        {
            return e.getMessage();
        }
    }

    @Override
    public List<Ocupation> findOcupationsByMunicipio(long idMunicipio)
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Ocupation>>(){}.getType();

        List<Ocupation> result = restConnection.request(HOST + "ocupations/municipio/" + idMunicipio, tipo);

        return result;
    }

    @Override
    public String findOcupationsByMunicipioAsync(long idMunicipio)
    {
        AsyncTaskDelegate asyncTaskDelegate = new AsyncTaskDelegate(delegate);
        try
        {
            return asyncTaskDelegate.execute(this, IOcupationService.FIND_OCUPATIONSBYMUNICIPIO, long.class, idMunicipio);
        }
        catch (NoSuchMethodException e)
        {
            return e.getMessage();
        }
    }

    @Override
    public void onSuccess(TaskResult taskResult)
    {
        delegate.onSuccess(taskResult);
    }

    @Override
    public void onFail(TaskResult taskResult)
    {
        delegate.onFail(taskResult);
    }
}
