package com.toponort.costasgismodel.service.catalogservice;

import com.toponort.costasgismodel.entities.Municipio;
import com.toponort.costasgismodel.entities.Provincia;

import java.util.List;

/**
 * Created by Juan on 13/07/2016.
 */
public interface ICatalogService
{
    String FINDPROVINCIAS = "findProvincias";
    String FINDMUNICIPIOSBYPROVINCIA = "findMunicipiosByProvincia";

    List<Provincia> findProvincias();
    String findProvinciasAsync();
    List<Municipio> findMunicipiosByProvincia(long idProvincia);
    String findMunicipiosByProvinciaAsync(long idProvincia);
}
