package com.toponort.costasgismodel.service.ocupationservice;

import com.toponort.costasgismodel.entities.Ocupation;

import java.util.List;

/**
 * Created by Juan on 21/06/2016.
 */
public interface IOcupationService
{
    String FIND_OCUPATIONSBYPROVINCIA = "findOcupationsByProvincia";
    String FIND_OCUPATIONSBYMUNICIPIO = "findOcupationsByMunicipio";
    String FIND_OCUPATIONS = "findOcupations";
    String FIND_OCUPATION = "findOcupation";

    Ocupation findOcupation(long idOcupacion);
    String findOcupationAsync(long idOcupacion);
    List<Ocupation> findOcupations();
    String findOcupationsAsync();
    List<Ocupation> findOcupationsByProvincia(long idProvincia);
    String findOcupationsByProvinciaAsync(long idProvincia);
    List<Ocupation> findOcupationsByMunicipio(long idMunicipio);
    String findOcupationsByMunicipioAsync(long idMunicipio);
}
