package com.team2052.frckrawler.database.models.metric;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.team2052.frckrawler.database.MetricValue;
import com.team2052.frckrawler.database.models.Event;
import com.team2052.frckrawler.database.models.Robot;

import java.io.Serializable;

/**
 * Created by Adam on 10/1/2014.
 */
@Table(name = "metricpitdata")
public class MetricPitData extends Model implements Serializable
{
    @Column(name = "Robot", onDelete = Column.ForeignKeyAction.CASCADE)
    public Robot robot;

    @Column(name = "Metric", onDelete = Column.ForeignKeyAction.CASCADE)
    public Metric metric;

    @Column(name = "Event", onDelete = Column.ForeignKeyAction.CASCADE)
    public Event event;

    @Column(name = "MetricValue")
    public String data;


    public MetricPitData(Robot robot, Event event, Metric metric, MetricValue values)
    {
        this.robot = robot;
        this.event = event;
        this.metric = metric;
        this.data = values.getValue();
    }

    public MetricPitData()
    {
    }

    public MetricValue getMetricValue(){
        try {
            return new MetricValue(metric, data);
        } catch (MetricValue.MetricTypeMismatchException e) {
            e.printStackTrace();
        }
        return null;
    }
}
