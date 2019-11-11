package com.sherry.feutech.mp6_sherry;


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
@SuppressLint("ValidFragment")
public class AnimeViewFragment extends Fragment {

    private final int position;

    public AnimeViewFragment(int position) {
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anime_view, container, false);

        Anime anime = Anime.get(position);

        View section1 = view.findViewById(R.id.section1);
        View section2 = view.findViewById(R.id.section2);
        View section3 = view.findViewById(R.id.section3);
        View section4 = view.findViewById(R.id.section4);
        View section5 = view.findViewById(R.id.section5);
        View section6 = view.findViewById(R.id.section6);

        TextView title1 = (TextView) view.findViewById(R.id.title1);
        TextView title2 = (TextView) view.findViewById(R.id.title2);
        TextView title3 = (TextView) view.findViewById(R.id.title3);
        TextView title4 = (TextView) view.findViewById(R.id.title4);
        TextView title5 = (TextView) view.findViewById(R.id.title5);
        TextView title6 = (TextView) view.findViewById(R.id.title6);

        TextView subtitle1 = (TextView) view.findViewById(R.id.subtitle1);
        TextView subtitle2 = (TextView) view.findViewById(R.id.subtitle2);
        TextView subtitle3 = (TextView) view.findViewById(R.id.subtitle3);
        TextView subtitle4 = (TextView) view.findViewById(R.id.subtitle4);
        TextView subtitle5 = (TextView) view.findViewById(R.id.subtitle5);
        TextView subtitle6 = (TextView) view.findViewById(R.id.subtitle6);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        String name = anime.getName();
        String power = anime.getPower();
        String birthday = anime.getBirthday();
        String height = anime.getHeight();
        String weight = anime.getWeight();
        String food = anime.getFood();

        if (name != null) {
            title1.setText(name);
            subtitle1.setText("Name");
        }
        else section1.setVisibility(View.GONE);

        if (power != null) {
            title2.setText(power);
            subtitle2.setText("Minimum Power");
        }
        else section1.setVisibility(View.GONE);

        if (birthday != null) {
            title3.setText(birthday);
            subtitle3.setText("birthday");
        }
        else section3.setVisibility(View.GONE);

        if (height != null) {
            title4.setText(height);
            subtitle4.setText("Height");
        }
        else section4.setVisibility(View.GONE);

        if (weight != null) {
            title5.setText(weight);
            subtitle5.setText("Weight");
        }
        else section5.setVisibility(View.GONE);

        if (food != null) {
            title6.setText(food);
            subtitle6.setText("Favorite Food");
        }
        else section6.setVisibility(View.GONE);

        imageView.setImageResource(anime.getImageResource());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return view;
    }

}
