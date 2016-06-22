package com.toponort.costasgismodel.service.ocupationservice;

import com.toponort.costasgismodel.entities.Ocupation;

import java.util.List;

/**
 * Created by Juan on 21/06/2016.
 */
public interface IOcupationService
{
    String FIND_OCUPATIONS = "findOcupations";

    List<Ocupation> findOcupations();
    List<Ocupation> findOcupations(long idProvincia);
}
