package fr.stanislas.homework2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class CustomizedFilmInfoActivity extends AppCompatActivity {

    static public ArrayList<StansFilm> stansEvents = new ArrayList<StansFilm>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized_film_info);
    }

    //if click on the submit button we retrieve the value given by the user
    public void onSubmitClick(View view)
    {
        String film_title = ((EditText) findViewById(R.id.titleID)).getText().toString();
        String film_director = ((EditText) findViewById(R.id.directorID)).getText().toString();
        String film_date = ((EditText) findViewById(R.id.dateID)).getText().toString();
        StansFilm stansEvent = null;

        if(film_title.isEmpty() && film_director.isEmpty()&& film_date.isEmpty())
        {
            stansEvent = new StansFilm(getResources().getString(R.string.filmDefaultTitle),
                    getResources().getString(R.string.filmDefautDirector),
                    getResources().getString(R.string.filmDefaultDate));
        }
        else
        {
            // if nothing is entered in the apropriate field put a default field
            if(film_title.isEmpty())
                film_title = getResources().getString(R.string.filmDefaultTitle);

            if(film_director.isEmpty())
                film_director = getResources().getString(R.string.filmDefautDirector);

            if(film_date.isEmpty())
                film_date = getResources().getString(R.string.filmDefaultDate);


            stansEvent = new StansFilm(film_title, film_director, film_date);

            //clear the EditText views after retrieving their data to enable a new entrance of the values
            ((EditText)findViewById(R.id.titleID)).setText(null);
            ((EditText)findViewById(R.id.directorID)).setText(null);
            ((EditText)findViewById(R.id.dateID)).setText(null);

        }

        Intent intent = new Intent();
        intent.putExtra(getString(R.string.add_film_key),stansEvent);
        setResult(RESULT_OK, intent);
        finish();  //pass the data and closing

    }
}


