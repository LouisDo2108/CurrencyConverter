package com.example.viewpager2;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
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
    private ArrayList<EditText> etArray = new ArrayList<>();
    private ArrayList<Spinner> spArray = new ArrayList<>();

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
        LayoutInflater themedInflater;
        View view;
        int spinnerLayout, spinnerColor;
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
        spinnerSetup(spinnerLayout, spinnerColor);
        textChanged();

        return view;
    }

    private void variablesSetup(View view)
    {
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
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Main", "Before Text Changed");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Main", "On Text Changed");
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (et1 == null || TextUtils.isEmpty(et1.getText().toString())) {

                        for(int i = 1; i <= 3; ++i)
                            etArray.get(i).setText("");
                        
                        return;
                    }

                    for(int i = 1; i <= 3; ++i)
                        exchange(i);
                } catch (Exception e) {
                    Log.e("Main", e.toString());
                }
            }
        });
    }

    private void getApiResult(String currencyRate, int index) {

        String url = "http://api.exchangeratesapi.io/v1/latest?access_key=5baddb25fdd667935c4ee6fe368e9328";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {

                // Check if current EditText is empty or null
                if (etArray.get(index) == null || TextUtils.isEmpty(etArray.get(index).toString()) || index < 0) {
                    Log.e("EditText Error", "EditText Missing");
                    return;
                }

                // Get the exchange rate value for the current currency selection
                // with base is Euro
                response = response.getJSONObject("rates");
                rates[index] = response.getDouble(currencyRate);

                exchange(index);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Wrong", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void spinnerSetup(int spinnerLayout, int color) {

        for(int i = 0 ; i < 4; ++i){
            final int index = i;
            ArrayAdapter adapter;
            Spinner sp;

            if(index == 0)
                adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currenciesBase, spinnerLayout);
            else
                adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencies, spinnerLayout);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp = spArray.get(index);
            sp.setAdapter(adapter);
            sp.setSelection(index, true);
            sp.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

            spArray.get(index).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String baseCurrency = parent.getItemAtPosition(position).toString();
                    getApiResult(baseCurrency, index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        getApiResult("USD", 1);
        getApiResult("GBP", 2);
        getApiResult("VND", 3);
    }

    private void exchange(int index)
    {
        double convertedValue = (Double) Double.valueOf(et1.getText().toString()) * rates[index];
        etArray.get(index).setText(String.valueOf(convertedValue));
    }
}