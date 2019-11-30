package com.ekomuliyo.mywidgets;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.widget.RemoteViews;

public class UpdateWidgetService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.random_numbers_widgets);
        ComponentName widget = new ComponentName(this, RandomNumbersWidgets.class);
        String lastUpdate = "Random: " + NumberGenerator.Generator(10);
        remoteViews.setTextViewText(R.id.appwidget_text, lastUpdate);
        appWidgetManager.updateAppWidget(widget, remoteViews);
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
