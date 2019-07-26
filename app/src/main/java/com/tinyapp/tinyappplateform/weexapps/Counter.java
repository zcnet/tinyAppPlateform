package com.tinyapp.tinyappplateform.weexapps;

public class Counter{
    private int count;
    public synchronized void set(int cnt){count = cnt;}
    public synchronized int get(){return count;}
    public synchronized void plusplus(){count++;}
    public synchronized void subsub(){count --;}
    public Counter(int cnt){ count = cnt; }
}
