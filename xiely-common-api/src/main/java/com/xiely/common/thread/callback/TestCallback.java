package com.xiely.common.thread.callback;

public class TestCallback implements Callback
{
    private ActionCallBack callBack;

    public TestCallback(ActionCallBack callBack)
    {
        this.callBack = callBack;
    }

    public void doAction(final int number)
    {
        new Thread(() -> callBack.produceAnswer(TestCallback.this, number)).start();
    }

    @Override
    public void solve(int num)
    {
    }
}
