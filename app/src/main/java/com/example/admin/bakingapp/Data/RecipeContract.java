/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.admin.bakingapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;


public class RecipeContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.admin.bakingapp.Data";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_TASKS = "tasks";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class RecipeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // Task table and column names
        public static final String TABLE_NAME = "recipeList";

        public static final String COLUMN_RECIPE_NAME = "recipeName";
        public static final String COLUMN_RECIPE_NAME_INGREDIENT = "recipeIngredientName";
        public static final String COLUMN_RECIPE_MEASUREMENT_INGREDIENT = "recipeIngredientMeasurement";
        public static final String COLUMN_RECIPE_QUANTITY_INGREDIENT = "recipeIngredientQuantity";


    }
}