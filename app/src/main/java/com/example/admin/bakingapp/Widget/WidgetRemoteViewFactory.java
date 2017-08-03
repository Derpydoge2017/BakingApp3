package com.example.admin.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.admin.bakingapp.Data.RecipeContract;
import com.example.admin.bakingapp.R;
import com.example.admin.bakingapp.RecipeChild.Ingredients.Ingredient;

import java.util.List;

/**
 * Created by Admin on 11-Jul-17.
 */

public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor data = null;

    Context context;

    public WidgetRemoteViewFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (data != null) {
            data.close();
        }

        data = context.getContentResolver().query(RecipeContract.BASE_CONTENT_URI, null, null, null, null);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        // Set ingredients in the widget
        if (position == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(position)) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_layout);

        int ingredientNameIndex = data.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME_INGREDIENT);

        String ingredientName = data.getString(ingredientNameIndex);

        remoteViews.setTextViewText(R.id.widget_ingredientName, ingredientName);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
