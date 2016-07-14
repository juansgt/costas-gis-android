package com.toponort.costasgismodel.entities;

/**
 * Created by Juan on 14/07/2016.
 */
public class Municipio
{
    public long IdMunicipio;
    public String Nombre;
    public long IdProvincia;

    public long getIdMunicipio()
    {
        return IdMunicipio;
    }

    public void setIdMunicipio(long idMunicipio)
    {
        IdMunicipio = idMunicipio;
    }

    public long getIdProvincia()
    {
        return IdProvincia;
    }

    public void setIdProvincia(long idProvincia)
    {
        IdProvincia = idProvincia;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public void setNombre(String nombre)
    {
        Nombre = nombre;
    }

    @Override
    public String toString()
    {
        return getNombre();
    }
}
