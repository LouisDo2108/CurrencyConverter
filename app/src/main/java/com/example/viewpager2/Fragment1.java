package com.example.viewpager2;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        spinner1 = (Spinner) view.findViewById(R.id.currencySpinner1);
        spinner2 = (Spinner) view.findViewById(R.id.currencySpinner2);
        spinner3 = (Spinner) view.findViewById(R.id.currencySpinner3);
        spinner4 = (Spinner) view.findViewById(R.id.currencySpinner4);
        spinnerSetup(spinnerLayout, spinnerColor);

        return view;
    }

    private void spinnerSetup(int spinnerLayout, int color)
    {
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencies, spinnerLayout);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(0, true);
        spinner1.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencies, spinnerLayout);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(1, true);
        spinner2.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencies, spinnerLayout);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setSelection(2, true);
        spinner3.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencies, spinnerLayout);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setSelection(3, true);
        spinner4.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
    }
}