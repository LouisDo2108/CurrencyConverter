package com.example.viewpager2;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LayoutInflater themedInflater;
    private View view;

    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ContentLoadingProgressBar progressBar;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themedInflater = LayoutInflater.from(new ContextThemeWrapper(getContext(), R.style.Theme_ViewPager2Night));
            view = themedInflater.inflate(R.layout.fragment_2, container, false);
        } else {
            themedInflater = LayoutInflater.from(new ContextThemeWrapper(getContext(), R.style.Theme_ViewPager2));
            view = themedInflater.inflate(R.layout.fragment_2, container, false);
        }
        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progress_circular);
        recyclerView = view.findViewById(R.id.recyclerView);
        parseItems.add(new ParseItem("Major", "Price", "Day",
                "Weekly", "Monthly", "Date"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ParseAdapter(parseItems, getContext());
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();
        return view;
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            parseItems.clear();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String exchange = "", price = "", nch = "",
                    daychange = "", weeklychange = "", monthlychange = "",
                    YTD = "", date = "";

            try {
                String url = "https://tradingeconomics.com/currencies";
                Document doc = Jsoup.connect(url).get();

                Elements rows = doc.select("#aspnetForm > div.container > div > div.col-lg-8.col-md-9 > div:nth-child(5) > " +
                        "div > table > tbody > tr");
                for (int i = 1; i < rows.size(); ++i) {
                    Element row = rows.get(i);
                    exchange = row.select("td.datatable-item-first > a > b").text();
                    Log.e("exchange", exchange);

                    price = row.select("#p").text();
                    Log.e("price", price);

                    daychange = row.select("#pch").text();
                    Log.e("Daychange", daychange);

                    weeklychange = row.select("td:nth-child(6)").text();
                    Log.e("weeklychange", weeklychange);

                    monthlychange = row.select("td:nth-child(7)").text();
                    Log.e("monthlychange", monthlychange);

                    date = row.select("#date").text();
                    Log.e("date", date);

                    parseItems.add(new ParseItem(exchange, price, daychange, weeklychange,
                            monthlychange, date));
                    Log.e("STT", String.valueOf(i));
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
                Log.e("Not Completed", "Something Wrong");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "New data arrived", Toast.LENGTH_SHORT).show();
            super.onPostExecute(unused);
        }
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
        Toast.makeText(getContext(), "Refreshing. Please be patient", Toast.LENGTH_SHORT).show();
        new Content().execute();
    }
}