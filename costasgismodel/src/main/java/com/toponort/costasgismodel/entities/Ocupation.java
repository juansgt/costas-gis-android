package com.toponort.costasgismodel.entities;

import java.util.Date;

/**
 * Created by Juan on 21/06/2016.
 */
public class Ocupation
{
    public enum Estado {

        SIN_INICIAR("Sin Iniciar"),
        EN_TRAMITE("En trámite"),
        SIN_DATOS(""),
        CADUCADA_DENEGADA("Extinguido"),
        OTORGADA("En vigor"),
        INDETERMINADO("Indeterminado");

        private String value;

        Estado(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static Estado getEnum(String value) {
            for(Estado v : values())
                if(v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }
    }

    public class GeometriaClase
    {
        private GeometryClass Geometry;
    }

    public class GeometryClass
    {
        private int CoordinateSystemId;
        private String WellKnownText;
    }

    private long IdOcupacion;
    private String DUNA;
    private String SP;
    private String DG;
    private Long IdProvincia;
    private GeometriaClase Geometria;
    private String Huso;
    private String Datum;
    private String Uso;
    private String Tipo;
    private String Titulo;
    private Date FechaOtorgamiento;
    private Date FechaExtincion;
    private Date FechaDenegacion;
    private boolean ExpSancionador;
    private String Descripcion;
    private String Municipio;
    private Double CoordenadaXOriginal;
    private Double CoordenadaYOriginal;
    private String Situacion;
    private double Latitud;
    private double Longitud;


    public Estado getEstado()
    {
        if (this.getSituacion().isEmpty() && this.getTitulo().isEmpty())
        {
            return Estado.SIN_DATOS;
        }
        else if (this.getTitulo().equals("Extinguido"))
        {
            return Estado.CADUCADA_DENEGADA;
        }
        else if (this.getSituacion().equals("Sin Iniciar"))
        {
            return Estado.SIN_INICIAR;
        }
        else if (this.getSituacion().equals("En trámite"))
        {
            return Estado.EN_TRAMITE;
        }
        else if (this.getTitulo().equals("En vigor"))
        {
            return Estado.OTORGADA;
        }
        return Estado.INDETERMINADO;
    }
    public Double getCoordenadaXOriginal()
    {
        return CoordenadaXOriginal;
    }

    public void setCoordenadaXOriginal(Double coordenadaXOriginal)
    {
        CoordenadaXOriginal = coordenadaXOriginal;
    }

    public Double getCoordenadaYOriginal()
    {
        return CoordenadaYOriginal;
    }

    public void setCoordenadaYOriginal(Double coordenadaYOriginal)
    {
        CoordenadaYOriginal = coordenadaYOriginal;
    }

    public String getDatum()
    {
        return Datum;
    }

    public void setDatum(String datum)
    {
        Datum = datum;
    }

    public String getDescripcion()
    {
        return Descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        Descripcion = descripcion;
    }

    public String getDG()
    {
        return DG;
    }

    public void setDG(String DG)
    {
        this.DG = DG;
    }

    public String getDUNA()
    {
        return DUNA;
    }

    public void setDUNA(String DUNA)
    {
        this.DUNA = DUNA;
    }

    public boolean isExpSancionador()
    {
        return ExpSancionador;
    }

    public void setExpSancionador(boolean expSancionador)
    {
        ExpSancionador = expSancionador;
    }

    public Date getFechaDenegacion()
    {
        return FechaDenegacion;
    }

    public void setFechaDenegacion(Date fechaDenegacion)
    {
        FechaDenegacion = fechaDenegacion;
    }

    public Date getFechaExtincion()
    {
        return FechaExtincion;
    }

    public void setFechaExtincion(Date fechaExtincion)
    {
        FechaExtincion = fechaExtincion;
    }

    public Date getFechaOtorgamiento()
    {
        return FechaOtorgamiento;
    }

    public void setFechaOtorgamiento(Date fechaOtorgamiento)
    {
        FechaOtorgamiento = fechaOtorgamiento;
    }

    public GeometriaClase getGeometria()
    {
        return Geometria;
    }

    public void setGeometria(GeometriaClase geometria)
    {
        Geometria = geometria;
    }

    public String getHuso()
    {
        return Huso;
    }

    public void setHuso(String huso)
    {
        Huso = huso;
    }

    public long getIdOcupacion()
    {
        return IdOcupacion;
    }

    public Long getIdProvincia()
    {
        return IdProvincia;
    }

    public void setIdProvincia(Long idProvincia)
    {
        IdProvincia = idProvincia;
    }

    public double getLatitud()
    {
        return Latitud;
    }

    public void setLatitud(double latitud)
    {
        Latitud = latitud;
    }

    public double getLongitud()
    {
        return Longitud;
    }

    public void setLongitud(double longitud)
    {
        Longitud = longitud;
    }

    public String getMunicipio()
    {
        return Municipio;
    }

    public void setMunicipio(String municipio)
    {
        Municipio = municipio;
    }

    public String getSituacion()
    {
        return Situacion;
    }

    public void setSituacion(String situacion)
    {
        Situacion = situacion;
    }

    public String getSP()
    {
        return SP;
    }

    public void setSP(String SP)
    {
        this.SP = SP;
    }

    public String getTipo()
    {
        return Tipo;
    }

    public void setTipo(String tipo)
    {
        Tipo = tipo;
    }

    public String getTitulo()
    {
        return Titulo;
    }

    public void setTitulo(String titulo)
    {
        Titulo = titulo;
    }

    public String getUso()
    {
        return Uso;
    }

    public void setUso(String uso)
    {
        Uso = uso;
    }
}
