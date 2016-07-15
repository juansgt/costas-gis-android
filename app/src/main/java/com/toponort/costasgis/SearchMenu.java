package com.toponort.costasgis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.toponort.asyncextensions.IAsyncDelegate;
import com.toponort.asyncextensions.TaskResult;
import com.toponort.costasgismodel.entities.Municipio;
import com.toponort.costasgismodel.entities.Provincia;
import com.toponort.costasgismodel.service.catalogservice.CatalogServiceImpl;
import com.toponort.costasgismodel.service.catalogservice.ICatalogService;

import java.util.List;

public class SearchMenu extends AppCompatActivity implements IAsyncDelegate, AdapterView.OnItemSelectedListener
{
    ICatalogService catalogService = new CatalogServiceImpl(SearchMenu.this);
    String findProvinciasAsyncId, findMunicipiosByProvinciaAsyncId;
    Spinner spinnerProvincias, spinnerMunicipios;
    boolean firstCallSpinnerMun;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);
        spinnerProvincias = (Spinner) findViewById(R.id.spinnerProvincias);
        spinnerProvincias.setOnItemSelectedListener(SearchMenu.this);
        spinnerMunicipios = (Spinner)this.findViewById(R.id.spinnerMunicipio);
        spinnerMunicipios.setOnItemSelectedListener(SearchMenu.this);
        findProvinciasAsyncId = catalogService.findProvinciasAsync();
    }

    @Override
    public void onSuccess(TaskResult taskResult)
    {
        if (taskResult.getMethodIdentifier().equals(findProvinciasAsyncId))
        {
            List<Provincia> provincias = taskResult.getMethodResult();
            ArrayAdapter<Provincia> arrayAdpater = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provincias);
            spinnerProvincias.setAdapter(arrayAdpater);
        }
        if (taskResult.getMethodIdentifier().equals(findMunicipiosByProvinciaAsyncId))
        {
            List<Municipio> municipios = taskResult.getMethodResult();
            ArrayAdapter<Municipio> arrayAdpater = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, municipios);
            spinnerMunicipios.setAdapter(arrayAdpater);
        }
    }

    @Override
    public void onFail(TaskResult taskResult)
    {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        switch(adapterView.getId())
        {
            case R.id.spinnerProvincias:
                firstCallSpinnerMun = true;
                findMunicipiosByProvinciaAsyncId = catalogService.findMunicipiosByProvinciaAsync(((Provincia)adapterView.getSelectedItem()).getIdProvincia());
                break;
            case R.id.spinnerMunicipio:
                if (firstCallSpinnerMun)
                {
                    firstCallSpinnerMun = false;
                }
                else
                {
                    Intent intent = new Intent(SearchMenu.this, MapsActivity.class);
                    intent.putExtra(MapsActivity.ID_MUNICIPIO, ((Municipio)adapterView.getSelectedItem()).getIdMunicipio());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}
