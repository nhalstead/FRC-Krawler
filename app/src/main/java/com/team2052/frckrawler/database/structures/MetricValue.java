package com.team2052.frckrawler.database.structures;


import android.util.Log;

import com.team2052.frckrawler.database.DBContract;

import java.text.DecimalFormat;

public class MetricValue implements Structure {

    private Metric metric;
    private String[] value; //Array only used for
    private int[] chooserCounts;    //Only used if this addbutton value is a COMPILED chooser

    public MetricValue(Metric _metric, String[] _value) throws MetricTypeMismatchException {
        this(_metric, _value, null);
    }

    public MetricValue(Metric _metric, String[] _value, int[] _chooserCounts)
            throws MetricTypeMismatchException {
        if (_metric.getType() == DBContract.COUNTER || _metric.getType() == DBContract.SLIDER) {
            for (String v : _value) {
                try {
                    Double.parseDouble(v);
                } catch (Exception e) {
                    Log.e("FRCKrawler", _metric.getMetricName());
                    Log.e("FRCKrawler", v);
                    Log.e("FRCKrawler", "MetricTypeMismatchException thrown.");
                    throw new MetricTypeMismatchException();
                }
            }
        }

        metric = _metric;
        value = _value;
        chooserCounts = _chooserCounts;
    }

    public String getValueAsDBReadableString() {
        if (value == null)
            return new String();

        String returnString = new String();
        for (int i = 0; i < value.length; i++) {
            returnString += value[i] + ":";
        }

        return returnString;
    }

    public String getValueAsHumanReadableString() {
        if (value == null)
            return new String();

        String returnString = new String();
        for (int i = 0; i < value.length; i++) {
            boolean isDecimal = true;

            try {
                Double.parseDouble(value[i]);
            } catch (NumberFormatException e) {
                isDecimal = false;
            }

            if (isDecimal) {
                DecimalFormat format = new DecimalFormat("0.00");
                if (i != value.length - 1) {
                    returnString += format.format(Double.parseDouble(value[i]));
                    if (metric.getType() == DBContract.BOOLEAN)
                        returnString += "%";
                    returnString += ", ";
                } else {
                    returnString += format.format(Double.parseDouble(value[i]));
                    if (metric.getType() == DBContract.BOOLEAN)
                        returnString += "%";
                }

            } else {
                if (i != value.length - 1 && !value[i].trim().equals(""))
                    returnString += value[i] + ", ";
                else
                    returnString += value[i];
            }
        }

        return returnString;
    }

    public Metric getMetric() {
        return metric;
    }

    public String[] getValue() {
        return value;
    }

    public int[] getChooserCounts() {
        return chooserCounts;
    }

    public class MetricTypeMismatchException extends Exception {
    }
}
