package com.toponort.costasgis.Ocupations;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.toponort.asyncextensions.IAsyncDelegate;
import com.toponort.asyncextensions.TaskResult;
import com.toponort.costasgis.R;
import com.toponort.costasgismodel.entities.Ocupation;
import com.toponort.costasgismodel.service.ocupationservice.IOcupationService;
import com.toponort.costasgismodel.service.ocupationservice.OcupationServiceImpl;

public class UpdateOcupations extends AppCompatActivity implements IAsyncDelegate
{
    public final static String ID_OCUPACION = "idOcupacion";
    private String findOcupationAsyncId;
    private TextView textViewEstadoValue;
    private TextView textViewDunaValue;
    private TextView textViewSPValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ocupations);

        IOcupationService ocupationService = new OcupationServiceImpl(UpdateOcupations.this);
        Intent intent = this.getIntent();
        long idOcupacion = intent.getLongExtra(ID_OCUPACION, 0);
        if (idOcupacion > 0)
        {
            findOcupationAsyncId = ocupationService.findOcupationAsync(idOcupacion);
            textViewEstadoValue = (TextView) findViewById(R.id.TextViewEstadoValue);
            textViewDunaValue = (TextView) findViewById(R.id.TextViewDunaValue);
            textViewSPValue = (TextView) findViewById(R.id.TextViewSPValue);
        }
        else
        {
            // Mostrar mensaje de no existe esa ocupacion y volver a la Activity anterior.
//            getCallingActivity();
        }
    }

    @Override
    public void onSuccess(TaskResult taskResult)
    {
        if (taskResult.getMethodIdentifier().equals(findOcupationAsyncId))
        {
            Ocupation ocupation = taskResult.getMethodResult();
            textViewEstadoValue.setText(ocupation.getEstado().toString());
            textViewDunaValue.setText(ocupation.getDUNA());
            textViewSPValue.setText(ocupation.getSP());
        }
    }

    @Override
    public void onFail(TaskResult taskResult)
    {

    }
}
