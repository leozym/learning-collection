package com.zym.foundation;

public class SubTestFinal extends TestFinal{

    @Override
    public int getMAX_NUM() {
        return super.getMAX_NUM();
    }

    public int getMaxNum(int num){
        return num + super.MAX_NUM;
    }
}
