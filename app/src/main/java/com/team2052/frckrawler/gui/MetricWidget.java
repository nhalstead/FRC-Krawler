package com.team2052.frckrawler.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.team2052.frckrawler.database.DBContract;
import com.team2052.frckrawler.database.structures.Metric;
import com.team2052.frckrawler.database.structures.MetricValue;
import com.team2052.frckrawler.database.structures.MetricValue.MetricTypeMismatchException;

public abstract class MetricWidget extends FrameLayout {

    protected LayoutInflater inflater;
    private Metric metric;

    protected MetricWidget(Context context, Metric m, String[] val) {
        super(context);
        metric = m;
        inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    public static MetricWidget createWidget(Context c, Metric m) {
        try {
            return createWidget(c, new MetricValue(m, new String[0]));
        } catch (MetricTypeMismatchException e) {
            return null;
        }
    }

    public static MetricWidget createWidget(Context c, MetricValue m) {
        if (m == null)
            return null;

        switch (m.getMetric().getType()) {
            case DBContract.BOOLEAN:
                return new BooleanMetricWidget(c, m);

            case DBContract.CHOOSER:
                return new ChooserMetricWidget(c, m);

            case DBContract.COUNTER:
                return new CounterMetricWidget(c, m);

            case DBContract.TEXT:
                return new TextMetricWidget(c, m);

            case DBContract.SLIDER:
                return new SliderMetricWidget(c, m);

            case DBContract.MATH:
                return new MathMetricWidget(c, m);

            default:

                return null;
        }
    }

    public Metric getMetric() {
        return metric;
    }

    public MetricValue getMetricValue() {

        try {
            return new MetricValue(metric, getValues());
        } catch (MetricTypeMismatchException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract String[] getValues();
}
