package com.toponort.asyncextensions;

import android.os.AsyncTask;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * Created by Juan on 29/04/2015.
 */
public class AsyncTaskDelegate
{
    private IAsyncDelegate _delegate;

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    private static final Set<Class<?>> PRIMITIVE_TYPES = getPrimitiveTypes();

    public AsyncTaskDelegate(IAsyncDelegate delegate)
    {
        this._delegate = delegate;
    }

    protected static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    protected static boolean isPrimitiveType(Class<?> clazz)
    {
        return PRIMITIVE_TYPES.contains(clazz);
    }

    protected static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();

        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);

        return ret;
    }

    protected static Set<Class<?>> getPrimitiveTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();

        ret.add(boolean.class);
        ret.add(char.class);
        ret.add(byte.class);
        ret.add(short.class);
        ret.add(int.class);
        ret.add(long.class);
        ret.add(float.class);
        ret.add(double.class);
        ret.add(void.class);

        return ret;
    }

    public String execute(Object methodClass, String methodName, Object... params) throws NoSuchMethodException, IllegalArgumentException
    {
        return this.actualExecute(null, methodClass, methodName, params);
    }

    public String executeOnExecutor(Executor exec, Object methodClass, String methodName, Object... params) throws NoSuchMethodException, IllegalArgumentException
    {
        return this.actualExecute(exec, methodClass, methodName, params);
    }

    protected Object[] getParamsValues(Object[] params)
    {
        Object values[] = new Object[params.length/2];
        int count = 0;

        for (int i = 1; i < params.length; i = i + 2)
        {
            values[count] = params[i];
            count++;
        }

        return values;
    }

    protected Method getMethod(Object methodClass, String methodName, Object[] params) throws NoSuchMethodException
    {
        Class tipos[] = new Class[params.length/2];
        int count = 0;
        for (int i = 0; i < params.length-1; i = i + 2)
        {
            tipos[count] = (Class)params[i];
            count++;
        }
        Method method = null;
        method = (methodClass.getClass()).getMethod(methodName, tipos);

        return method;
    }

    protected String actualExecute(Executor exec, Object methodClass, String methodName, Object... params) throws NoSuchMethodException, IllegalArgumentException
    {
        String id = UUID.randomUUID().toString();

        if (params.length % 2 == 0)
        {
            AsyncMethodExecute asyncTask = new AsyncMethodExecute();

            if (exec == null)
            {
                asyncTask.execute(id, methodClass, this.getMethod(methodClass, methodName, params), this.getParamsValues(params));
            }
            else
            {
                asyncTask.executeOnExecutor(exec, id, methodClass, this.getMethod(methodClass, methodName, params), this.getParamsValues(params));
            }
        }
        else
        {
            throw new IllegalArgumentException("El numero de parametros debe de ser par (incluyendo cero) siguiendo el esquema (Type, value, [...], Type, value");
        }

        return id;
    }

    class AsyncMethodExecute extends AsyncTask<Object, Void, TaskResult>
    {
        @Override
        protected TaskResult doInBackground(Object... params)
        {
            Method method = (Method)params[2];
            TaskResult taskResult;
            try
            {
                List<Object> list = new ArrayList(Arrays.asList(params));
                list.remove(0);
                list.remove(0);
                list.remove(0);
                Object[] args = (Object[])list.get(0);
                if (args.length > 0)
                {
                    taskResult = new TaskResult(method.invoke(params[1], args), (String) params[0]);
                }
                else
                {
                    taskResult = new TaskResult(method.invoke(params[1]), (String) params[0]);
                }
                return taskResult;
            }
            catch (Exception e)
            {
                return new TaskResult(e, (String)params[0]);
            }
        }

        @Override
        protected void onPostExecute(TaskResult result)
        {
            if (result != null)
            {
                if (result.getMethodResult() != null)
                    _delegate.onSuccess(result);
                else
                    _delegate.onFail(result);
            }
        }
    }
}
