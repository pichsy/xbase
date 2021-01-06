package com.pichs.common.base.vm;

import androidx.lifecycle.ViewModel;

public abstract class XBaseViewModel<T extends XBaseModel> extends ViewModel {
    protected T mModel;
}
