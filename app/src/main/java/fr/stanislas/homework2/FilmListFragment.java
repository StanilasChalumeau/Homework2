package fr.stanislas.homework2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmListFragment extends ListFragment {


    public FilmListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<StansFilm>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, MainActivity.myFilms));

    }
}
