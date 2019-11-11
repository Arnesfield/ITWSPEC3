package com.cayle.machineproblem6;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {

    private Item item;

    public ViewFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ViewFragment(Item item) {
        this.item = item;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

        ImageView iv_main = (ImageView) view.findViewById(R.id.iv_main);

        tv_name.setText( item.getName() );
        tv_desc.setText( item.getDesc() );
        tv_price.setText( item.getFormattedPrice() );

        iv_main.setImageResource( item.getLargeImageResource() );

        return view;
    }

}
