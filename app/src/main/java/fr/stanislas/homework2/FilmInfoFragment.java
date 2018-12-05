package fr.stanislas.homework2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmInfoFragment extends Fragment implements View.OnClickListener {

    private StansFilm stanFilmDisplay; //StansFilm is a custom class for the movie
    public FilmInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_info, container, false);


    }

    public void displayTask(StansFilm film){
        View displayedTaskView = getActivity().findViewById(R.id.filmDescriptionFragmentID);
        displayedTaskView.setVisibility(View.VISIBLE); // make the task visible
        ((TextView)getActivity().findViewById(R.id.filmTitleID)).setText(film.filmTitle);
        ((TextView)getActivity().findViewById(R.id.filmDirectorID)).setText(film.filmDirector);
        ((TextView)getActivity().findViewById(R.id.filmDateID)).setText(film.filmDate);

        //at each display we will assign a new icon to the fragment
        ImageView imView = (ImageView)getActivity().findViewById(R.id.filmImageID);
        Random rnd = new Random();
         int a = rnd.nextInt(5); //rnd =[0:4]
        switch (a){

            case 0 :
                imView.setImageResource(R.drawable.image7);
                break;

            case 1 :
                imView.setImageResource(R.mipmap.ic_launcher_round);
                break;

            case 2 :
                imView.setImageResource(R.drawable.imag3);
                break;

            case 3 :
                imView.setImageResource(R.drawable.image4);
                break;

            case 4 :
                imView.setImageResource(R.drawable.image5);
                break;

            //default:
              //  break;*/

        }




            stanFilmDisplay = film;
    }


    @Override
    public void onActivityCreated(Bundle savedState) {

        super.onActivityCreated(savedState);
        Intent intent = getActivity().getIntent();
        StansFilm receivedTask = intent.getParcelableExtra(MainActivity.filmExtra);
        if (receivedTask != null)
            displayTask(receivedTask);

    }

    @Override
    public void onClick(View v) {

        Intent intent = getActivity().getIntent();
        StansFilm receivedTask = intent.getParcelableExtra(MainActivity.filmExtra);
        if(receivedTask != null)
            displayTask(receivedTask);




    }


}




