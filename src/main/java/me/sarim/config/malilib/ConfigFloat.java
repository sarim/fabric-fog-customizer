package me.sarim.config.malilib;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import fi.dy.masa.malilib.config.options.ConfigDouble;

public class ConfigFloat extends ConfigDouble {

    public ConfigFloat(String name, float defaultValue, String comment)
    {
        super(name, defaultValue, Float.MIN_VALUE, Float.MAX_VALUE, comment);
    }

    public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, String comment)
    {
        super(name, defaultValue, minValue, maxValue, false, comment);
    }

    public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, boolean useSlider, String comment)
    {
        super(name, defaultValue, minValue, maxValue, useSlider, comment);
    }

    @Override
    public void setDoubleValue(double value)
    {
        super.setDoubleValue(Double.valueOf(new DecimalFormat("#.####", DecimalFormatSymbols.getInstance(Locale.US)).format(value)));
    }

    public float getFloatValue()
    {
        return (float)super.getDoubleValue();
    }
}
