package com.toponort.asyncextensions;

/**
 * Created by Juan on 29/04/2015.
 */
public interface IAsyncDelegate
{
    void onSuccess(TaskResult taskResult);
    void onFail(TaskResult taskResult);
}
