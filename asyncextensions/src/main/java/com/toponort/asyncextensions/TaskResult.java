package com.toponort.asyncextensions;

/**
 * Created by Juan on 29/04/2015.
 */
public class TaskResult
{
    private Object _methodResult;
    private String _methodIdentifier;
    private Exception _exception;

    public TaskResult(Object methodResult, String methodIdentifier)
    {
        this._methodResult = methodResult;
        this._exception = null;
        this._methodIdentifier = methodIdentifier;
    }

    public TaskResult(Exception exception, String methodIdentifier)
    {
        this._exception = exception;
        this._methodResult = null;
        this._methodIdentifier = methodIdentifier;
    }

    public <ResultType> ResultType getMethodResult()
    {
        return (ResultType) _methodResult;
    }

    public String getMethodIdentifier()
    {
        return _methodIdentifier;
    }

    public Exception getException() {
        return _exception;
    }

    public void setException(Exception _exception) {
        this._exception = _exception;
    }
}
