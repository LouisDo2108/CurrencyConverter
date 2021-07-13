package com.example.viewpager2;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner1, spinner2, spinner3, spinner4;
    private EditText et1, et2, et3, et4;
    private Double[] rates = {1.0, 0.0, 0.0, 0.0};
    private int[] curID = {0, 1, 2, 3};
    private ArrayList<EditText> etArray = new ArrayList<>();
    private ArrayList<Spinner> spArray = new ArrayList<>();
    private LayoutInflater themedInflater;
    private View view;
    private int spinnerLayout = 0, spinnerColor = 0;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themedInflater = LayoutInflater.from(new ContextThemeWrapper(getContext(), R.style.Theme_ViewPager2Night));
            view = themedInflater.inflate(R.layout.fragment_1, container, false);
            spinnerLayout = R.layout.spinner_dark;
            spinnerColor = R.color.white;
        } else {
            themedInflater = LayoutInflater.from(new ContextThemeWrapper(getContext(), R.style.Theme_ViewPager2));
            view = themedInflater.inflate(R.layout.fragment_1, container, false);
            spinnerLayout = R.layout.spinner_light;
            spinnerColor = R.color.black;
        }

        variablesSetup(view);
        setHasOptionsMenu(true);
        spinnerSetup(spinnerLayout, spinnerColor);
        textChanged();
        getApiResult(0);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.darkmode:
                darkMode(item);
                return true;
            case R.id.refresh:
                refresh(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void darkMode(MenuItem item) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public void refresh(MenuItem item) {
        exchange();
        Toast.makeText(getContext(), "Rates refreshed", Toast.LENGTH_SHORT).show();
    }

    private void variablesSetup(View view) {
        spinner1 = (Spinner) view.findViewById(R.id.currencySpinner1);
        spinner2 = (Spinner) view.findViewById(R.id.currencySpinner2);
        spinner3 = (Spinner) view.findViewById(R.id.currencySpinner3);
        spinner4 = (Spinner) view.findViewById(R.id.currencySpinner4);

        et1 = (EditText) view.findViewById(R.id.input1);
        et2 = (EditText) view.findViewById(R.id.output2);
        et3 = (EditText) view.findViewById(R.id.output3);
        et4 = (EditText) view.findViewById(R.id.output4);

        etArray.add(et1);
        etArray.add(et2);
        etArray.add(et3);
        etArray.add(et4);

        spArray.add(spinner1);
        spArray.add(spinner2);
        spArray.add(spinner3);
        spArray.add(spinner4);
    }

    private void textChanged() {
        final int index = 0; // only allow to edit text in the first field
//            EditText currentEt = etArray.get(index);
        etArray.get(index).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Main", "Before Text Changed");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // test drive
                Log.d("Main", "On Text Changed");
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (etArray.get(index) == null || TextUtils.isEmpty(etArray.get(index).getText().toString())) {
                        for (int j = 1; j < 4; ++j)
                                etArray.get(j).setText("0.0");
                        return;
                    }
                    exchange();
                } catch (Exception e) {
                    Log.e("Main", e.toString());
                }
            }
        });
    }

//    private void updateRates(){
//        for(int i = 1; i <= 3; ++i)
//            exchange(i);
//    }

    private void getApiResult(int changedIndex) {
        String baseCurrency = (String) spArray.get(0).getItemAtPosition(curID[0]).toString();
        if(changedIndex != 0)
        {
            String alterCurrency = (String) spArray.get(changedIndex).getItemAtPosition(curID[changedIndex]).toString();
            String q = baseCurrency + '_' + alterCurrency;
            String url = "https://free.currconv.com/api/v7/convert?q=" + q + "&compact=ultra&apiKey=e9a4c7cd80676c0b31f2";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    // Check if current EditText is empty or null
                    if (etArray.get(changedIndex) == null || TextUtils.isEmpty(etArray.get(changedIndex).toString())) {
                        Log.e("EditText Error", "EditText Missing");
                        return;
                    }

                    rates[changedIndex] = (Double) response.getDouble(q);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "API Error", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for (int i = 0; i < 4; i++) {
            final int index = i;
            String alterCurrency = (String) spArray.get(index).getItemAtPosition(curID[i]).toString();
            String q = baseCurrency + '_' + alterCurrency;
            String url = "https://free.currconv.com/api/v7/convert?q=" + q + "&compact=ultra&apiKey=e9a4c7cd80676c0b31f2";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    // Check if current EditText is empty or null
                    if (etArray.get(index) == null || TextUtils.isEmpty(etArray.get(index).toString())) {
                        Log.e("EditText Error", "EditText Missing");
                        return;
                    }

                    // Get the exchange rate value for the current currency selection
                    rates[index] = (Double) response.getDouble(q);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "API Error", Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(getContext()).addToRequestQueue(request);
        }
    }

    private void spinnerSetup(int spinnerLayout, int color) {

        for (int i = 0; i < 4; ++i) {
            final int index = i;
            ArrayAdapter adapter;

            adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencies, spinnerLayout);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spArray.get(index).setAdapter(adapter);
            spArray.get(index).setSelection(index, true);
            spArray.get(index).getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

            spArray.get(index).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("position", String.valueOf(position));
                    Log.e("id", String.valueOf(id));
                    String baseCurrency = parent.getItemAtPosition(position).toString();
                    curID[index] = position;
                    getApiResult(index);
                    exchange();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

//        getApiResult("USD", 1);
//        getApiResult("GBP", 2);
//        getApiResult("VND", 3);
    }

    private void exchange() // take the current editText at index as the base value, multiply it by rates of the other threes
    {
        EditText baseEt = (EditText) etArray.get(0);
        if (baseEt == null || TextUtils.isEmpty(baseEt.toString())) {
            Log.e("EditText Error", "EditText Missing");
            return;
        }
        double baseValue = (Double) Double.valueOf(baseEt.getText().toString());
        for (int i = 1; i < 4; i++) {
                double convertedValue = baseValue * rates[i];
                etArray.get(i).setText(String.valueOf(convertedValue));
        }
    }

//    public void darkMode(MenuItem item) {
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }
//    }
}