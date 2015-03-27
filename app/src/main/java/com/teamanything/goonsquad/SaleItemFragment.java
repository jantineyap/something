package com.teamanything.goonsquad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamanything.goonsquad.database.SaleItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaleItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaleItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleItemFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_ITEM = "item";
    private static final String ARG_PRICE = "price";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_X = "x";
    private static final String ARG_Y = "y";

    private String mItem = "";
    private String mLocation = "";
    private Double mX = 0.0;
    private Double mY = 0.0;
    private Double mPrice = 0.0;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param saleItem the SaleItem for the Fragment
     * @return A new instance of fragment SaleItemFragment.
     */
    public static SaleItemFragment newInstance(SaleItem saleItem) {
        SaleItemFragment fragment = new SaleItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, saleItem.getItem());
        args.putDouble(ARG_PRICE, saleItem.getPrice());
        args.putString(ARG_LOCATION, saleItem.getLocation());
        args.putDouble(ARG_X, Double.parseDouble(saleItem.getX()));
        args.putDouble(ARG_Y, Double.parseDouble(saleItem.getY()));
        fragment.setArguments(args);
        return fragment;
    }

    public SaleItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getString(ARG_ITEM);
            mPrice = getArguments().getDouble(ARG_PRICE);
            mLocation = getArguments().getString(ARG_LOCATION);
            mX = getArguments().getDouble(ARG_X);
            mY = getArguments().getDouble(ARG_Y);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sale_item, container, false);

        ((TextView) view.findViewById(R.id.textView_item)).setText(mItem);
        ((TextView) view.findViewById(R.id.textView_price)).setText(mPrice.toString());

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        supportMapFragment.getMapAsync(this);
        getFragmentManager().beginTransaction().add(R.id.map_container, supportMapFragment).commit();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(mX, mY)).title(mItem)).setVisible(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mX, mY), 10.0f));
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

    }

}
