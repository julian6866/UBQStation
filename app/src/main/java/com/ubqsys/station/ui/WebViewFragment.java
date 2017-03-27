package com.ubqsys.station.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ubqsys.station.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment implements WebViewLoading.LoadingTaskFinishedListener {

    private String url = "";

    private OnFragmentInteractionListener mListener;

    private static WebViewFragment instance = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url url to load
     * @return A new instance of fragment WebViewFragment.
     */
    public static WebViewFragment getInstance(String url) {
        if(instance == null)
            instance = new WebViewFragment();

        Bundle args = new Bundle();
        args.putString(instance.getString(R.string.WEBVIEW_URL_KEY), url);
        instance.setArguments(args);
        return instance;
    }

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(getString(R.string.WEBVIEW_URL_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

        //setContentView(R.layout.activity_webview);



        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        onTaskStarted();
        WebView myWebView = (WebView) getView().findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                onTaskFinished();
            }
        });
        myWebView.loadUrl(url);

        //new WebViewLoading(getContext(), this, getView(), url).execute();

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onTaskStarted() {

        ProgressBar pbHeaderProgress = (ProgressBar) getView().findViewById(R.id.progressBarWebViewLoading);
        pbHeaderProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskFinished() {
        ProgressBar pbHeaderProgress = (ProgressBar) getView().findViewById(R.id.progressBarWebViewLoading);
        pbHeaderProgress.setVisibility(View.GONE);
    }
}
