package org.glittle.flashlight001;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.RemoteViews;

/**
 * Created by thewhale on 1/12/18.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static Camera cam = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        views.setTextViewText(R.id.button, "\uD83D\uDD26");

        Intent intent = new Intent(context, getClass());
        intent.setAction("click");
        views.setOnClickPendingIntent(R.id.button,
                PendingIntent.getBroadcast(context, 0, intent, 0));

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("click")) {
            if (cam == null) {
                cam = Camera.open();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            } else {
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        }
    }
}
