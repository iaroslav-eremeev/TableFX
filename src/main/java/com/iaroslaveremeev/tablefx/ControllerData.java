package com.iaroslaveremeev.tablefx;

import java.io.IOException;

public interface ControllerData<T> {

    void initData(T value) throws IOException;
}
