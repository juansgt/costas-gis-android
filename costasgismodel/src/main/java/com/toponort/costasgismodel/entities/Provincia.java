package com.toponort.costasgismodel.entities;

/**
 * Created by Juan on 13/07/2016.
 */
public class Provincia
{
    private long IdProvincia;
    private String Nombre;

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
