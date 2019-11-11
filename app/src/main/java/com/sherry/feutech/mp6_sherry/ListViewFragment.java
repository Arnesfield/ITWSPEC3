package com.sherry.feutech.mp6_sherry;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {


    public interface ListItemClickListener {
        void onItemClick(int position);
    }

    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(Anime.getAdapter(getActivity(), android.R.layout.simple_list_item_1));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ListItemClickListener)getActivity()).onItemClick(position);
            }
        });

        return view;
    }

}
