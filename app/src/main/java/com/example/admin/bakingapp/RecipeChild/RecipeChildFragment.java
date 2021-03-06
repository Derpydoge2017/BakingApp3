package com.example.admin.bakingapp.RecipeChild;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.bakingapp.NetworkUtils;
import com.example.admin.bakingapp.R;
import com.example.admin.bakingapp.RecipeChild.Ingredients.Ingredient;
import com.example.admin.bakingapp.RecipeChild.Ingredients.IngredientAdapter;
import com.example.admin.bakingapp.RecipeChild.Ingredients.IngredientJSONData;
import com.example.admin.bakingapp.RecipeChild.Instructions.Instruction;
import com.example.admin.bakingapp.RecipeChild.Instructions.InstructionAdapter;
import com.example.admin.bakingapp.RecipeChild.Instructions.InstructionJSONData;
import com.example.admin.bakingapp.RecipeDisplay.RecipeDisplayChildActivity;

import java.net.URL;
import java.util.ArrayList;

public class RecipeChildFragment extends Fragment implements InstructionAdapter.InstructionAdapterOnClickHandler {

    private static final String SHARE = " #RecipeApp";

    String RECIPE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Context context;

    protected static int step_index = 0;

    private final String KEY_INGREDIENT_STATE = "ingredient_state";
    private final String KEY_INSTRUCTION_STATE = "instruction_state";

    private ArrayList<Ingredient> mIngredientList = new ArrayList<>();
    private ArrayList<Instruction> mInstructionList = new ArrayList<>();

    private IngredientAdapter mIngredientAdapter;
    private InstructionAdapter mInstructionAdapter;

    private RecyclerView mIngredientRV;
    private RecyclerView mInstructionRV;

    private Instruction mInstruction;

    public RecipeChildFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_child, container, false);
        context = getActivity();

        mIngredientRV = (RecyclerView) rootView.findViewById(R.id.ingredient_rv);
        mInstructionRV = (RecyclerView) rootView.findViewById(R.id.instruction_rv);

        GridLayoutManager gridIngredientManager = new GridLayoutManager(context, numberOfColumns());
        GridLayoutManager gridInstructionManager = new GridLayoutManager(context, numberOfColumns());

        mIngredientRV.setLayoutManager(gridIngredientManager);
        mInstructionRV.setLayoutManager(gridInstructionManager);

        mIngredientAdapter = new IngredientAdapter();
        mIngredientRV.setAdapter(mIngredientAdapter);

        mInstructionAdapter = new InstructionAdapter(context, (InstructionAdapter.InstructionAdapterOnClickHandler) this);
        mInstructionRV.setAdapter(mInstructionAdapter);

        if (savedInstanceState != null) {

            mIngredientList = savedInstanceState.getParcelableArrayList(KEY_INGREDIENT_STATE);
            mInstructionList = savedInstanceState.getParcelableArrayList(KEY_INSTRUCTION_STATE);

            mIngredientAdapter.setIngredientData(mIngredientList);
            mInstructionAdapter.setInstructionData(mInstructionList);

        } else {

            loadIngredientData();
            loadInstructionData();

        }

        return rootView;

    }

    /**
     * This method will load the ingredient
     */
    private void loadIngredientData() {
        new IngredientQueryTask().execute();
        showIngredientDataView();
    }


    /**
     * This method will load the instruction
     */
    private void loadInstructionData() {
        new InstructionQueryTask().execute();
        showInstructionDataView();
    }

    private void showIngredientDataView() {
        /* Make sure the recipe data is visible */
        mIngredientRV.setVisibility(View.VISIBLE);

    }

    private void showInstructionDataView() {
        /* Make sure the recipe data is visible */
        mIngredientRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Instruction instruction) {

        Class destinationClass = RecipeDisplayChildActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TITLE, instruction);
        startActivity(intentToStartDetailActivity);
        mInstruction.setInstructionPos(mInstructionList.indexOf(instruction));

    }



    public class IngredientQueryTask extends AsyncTask<String, Void, ArrayList<Ingredient>> {

        @Override
        protected ArrayList doInBackground(String... params) {
            URL recipeSearchUrl = NetworkUtils.buildUrl(RECIPE_BASE_URL);
            try {
                String jsonRecipeResponse = NetworkUtils
                        .getResponseFromHttpUrl(recipeSearchUrl);

                ArrayList simpleJsonIngredientData = IngredientJSONData
                        .getIngredientDataStringsFromJson(context, jsonRecipeResponse);

                mIngredientList = simpleJsonIngredientData;

                return simpleJsonIngredientData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Ingredient> ingredientData) {
            mIngredientAdapter.setIngredientData(ingredientData);
        }

    }


    public class InstructionQueryTask extends AsyncTask<String, Void, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            URL recipeSearchUrl = NetworkUtils.buildUrl(RECIPE_BASE_URL);
            try {
                String jsonRecipeResponse = NetworkUtils
                        .getResponseFromHttpUrl(recipeSearchUrl);

                ArrayList simpleJsonInstructionData = InstructionJSONData
                        .getInstructionDataStringsFromJson(context, jsonRecipeResponse);

                mInstructionList = simpleJsonInstructionData;

                return simpleJsonInstructionData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList instructionData) {
            mInstructionAdapter.setInstructionData(instructionData);
        }

    }

    private int numberOfColumns() {
        int nColumns = 1;
        return nColumns;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        // save RecyclerView state
        outState.putParcelableArrayList(KEY_INGREDIENT_STATE, mIngredientList);
        outState.putParcelableArrayList(KEY_INSTRUCTION_STATE, mInstructionList);

        super.onSaveInstanceState(outState);

    }
}

