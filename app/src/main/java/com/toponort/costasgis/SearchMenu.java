package com.toponort.costasgis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.toponort.asyncextensions.IAsyncDelegate;
import com.toponort.asyncextensions.TaskResult;

public class SearchMenu extends AppCompatActivity implements IAsyncDelegate
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);


    }

    @Override
    public void onSuccess(TaskResult taskResult)
    {

    }

    @Override
    public void onFail(TaskResult taskResult)
    {

    }
}
