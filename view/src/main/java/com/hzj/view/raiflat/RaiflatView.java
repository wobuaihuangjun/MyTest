package com.hzj.view.raiflat;


import android.os.Parcelable;

interface RaiflatView {

    Parcelable onSaveInstanceState(Parcelable state);

    Parcelable onRestoreInstanceState(Parcelable state);

    void setFlatEnabled(boolean enable);

    boolean isFlatEnabled();
}
