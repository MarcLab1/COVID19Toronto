package com.covid19toronto.helpers;

import android.content.Context;

public class HelperPixel {

    private float scale;
    public HelperPixel(Context context)
    {
        scale = context.getResources().getDisplayMetrics().density;
    }

    public int convertDptoPx(int dps)
    {
        return (int) (dps * scale + 0.5f);
    }

}
