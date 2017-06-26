package com.unipu.marko.pametnakuca;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.Toast;

import com.unipu.marko.pametnakuca.dummy.DummyContent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static java.lang.Boolean.TRUE;

/**
 * A fragment representing a single Senzor detail screen.
 * This fragment is either contained in a {@link SenzorListActivity}
 * in two-pane mode (on tablets) or a {@link SenzorDetailActivity}
 * on handsets.
 */
public class SenzorDetailFragment extends Fragment {

    public static final String JSON_URL = "http://192.168.1.50/getData.php";
    private String sDatum;
    private String sTemperatura;
    private String sPritisak;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SenzorDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.item_name);
            }
        }
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        //CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails);
        //listView.setAdapter(cl);
        sDatum = ParseJSON.Datum;
        sTemperatura = ParseJSON.Temperatura;
        sPritisak = ParseJSON.Pritisak;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.senzor_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            StringRequest stringRequest = new StringRequest(JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            showJSON(response);
                            if(mItem.item_name.equals("Pregled")) {
                                (rootView.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.PregledTempPrikaz)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.PregledPritisakPrikaz)).setVisibility(View.VISIBLE);
                                ((TextView) rootView.findViewById(R.id.PregledTempPrikaz)).setText(sTemperatura);
                                if (Integer.parseInt(sTemperatura)<15) {
                                    ((TextView) rootView.findViewById(R.id.PregledTempPrikaz)).setTextColor(ColorStateList.valueOf(Color.rgb(0,191,255)));
                                } else if (Integer.parseInt(sTemperatura)<26) {
                                    ((TextView) rootView.findViewById(R.id.PregledTempPrikaz)).setTextColor(ColorStateList.valueOf(Color.GREEN));
                                } else if (Integer.parseInt(sTemperatura)<32) {
                                    ((TextView) rootView.findViewById(R.id.PregledTempPrikaz)).setTextColor(ColorStateList.valueOf(Color.YELLOW));
                                } else {
                                    ((TextView) rootView.findViewById(R.id.PregledTempPrikaz)).setTextColor(ColorStateList.valueOf(Color.RED));
                                }
                                ((TextView) rootView.findViewById(R.id.PregledPritisakPrikaz)).setText(sPritisak);
                                ((TextView) rootView.findViewById(R.id.PregledPritisakPrikaz)).setTextColor(ColorStateList.valueOf(Color.BLACK));
                            }
                            if(mItem.item_name.equals("Temperatura")) {
                                ((ProgressBar) rootView.findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.value0)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.value1)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.value2)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.value3)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.value4)).setVisibility(View.VISIBLE);
                                (rootView.findViewById(R.id.value5)).setVisibility(View.VISIBLE);
                                ((TextView) rootView.findViewById(R.id.senzor_detail)).setText(sTemperatura);
                                if (Integer.parseInt(sTemperatura)<15) {
                                    ((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgressTintList(ColorStateList.valueOf(Color.rgb(0,191,255)));
                                } else if (Integer.parseInt(sTemperatura)<26) {
                                    ((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                } else if (Integer.parseInt(sTemperatura)<32) {
                                    ((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                                } else {
                                    ((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgressTintList(ColorStateList.valueOf(Color.RED));
                                }
                                ((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgress(Integer.parseInt(sTemperatura), TRUE);
                            }
                            if(mItem.item_name.equals("Pritisak")) {
                                (rootView.findViewById(R.id.progressBar1)).setVisibility(View.VISIBLE);
                                ((TextView) rootView.findViewById(R.id.senzor_detail)).setTextSize(30);
                                ((TextView) rootView.findViewById(R.id.senzor_detail)).setText(sPritisak);
                                ((ProgressBar) rootView.findViewById(R.id.progressBar1)).setMax(1050);
                                ((ProgressBar) rootView.findViewById(R.id.progressBar1)).setProgress(Integer.parseInt(sPritisak), TRUE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(SenzorDetailFragment.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(stringRequest);
        }

        return rootView;
    }
}
