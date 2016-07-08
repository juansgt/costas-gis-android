package com.toponort.costasgismodel.service.ocupationservice;

import com.google.gson.reflect.TypeToken;
import com.toponort.costasgismodel.entities.Ocupation;
import com.toponort.webserviceapi.restful.IRestConnection;
import com.toponort.webserviceapi.restful.RestConnectionImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Juan on 22/06/2016.
 */
public class OcupationServiceImpl implements IOcupationService
{
    public static final String HOST = "http://costasgis.shopinshock.com/";

    @Override
    public Ocupation findOcupation(long idOcupacion)
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);

        Ocupation ocupation = restConnection.request(HOST + "ocupations/" + idOcupacion, Ocupation.class);

        return ocupation;
    }

    @Override
    public List<Ocupation> findOcupations()
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Ocupation>>(){}.getType();

        List<Ocupation> vias = restConnection.request(HOST + "ocupations/", tipo);

        return vias;
    }

    @Override
    public List<Ocupation> findOcupations(long idProvincia)
    {
        IRestConnection restConnection = new RestConnectionImpl(IRestConnection.RequestMethodType.GET, IRestConnection.ContentType.JSON);
        Type tipo = new TypeToken<List<Ocupation>>(){}.getType();

        List<Ocupation> vias = restConnection.request(HOST + "ocupations/provincia/" + idProvincia, tipo);

        return vias;
    }
}
