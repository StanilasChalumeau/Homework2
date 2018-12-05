package fr.stanislas.homework2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements DeleteDialog.NoticeDialogListener {

    private static final int REQUEST_CODE = 1;
    private static final String MESSAGE = "";
    private static final int REQUEST = 2;
    public int pos;

    public static final String FILMS_FILE = "fr.stanislas.todolist.FilmsFile";
    public static final String NUM_FILMS = "NumOfFilms";
    public static final String FILM ="film_";
    public static final String DIR ="dir_";
    public static final String PIC ="pic_";
    public static final String DAT ="date_";


    public static final String DELETION = "DELETION";
    public FilmListFragment eventListFragment;

    public FloatingActionButton addBtn;
    public static final String filmExtra = "Film";

    // Display 3 examples of film and characteristics
    static public ArrayList<StansFilm> myFilms;
    static {
        myFilms = new ArrayList<StansFilm>();
        myFilms.add(new StansFilm("Film1", "Director1","Date1"));
        myFilms.add(new StansFilm("Film2", "Director2", "Date2"));
        myFilms.add(new StansFilm("Film3", "Director3", "Date3"));
    }

    private int listItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreTasks();


        eventListFragment = (FilmListFragment)getSupportFragmentManager().findFragmentById(R.id.filmFragment);
        eventListFragment.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item selected.", Toast.LENGTH_LONG).show();
                if(getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE){
                    FilmInfoFragment frag = (FilmInfoFragment)getSupportFragmentManager().findFragmentById(R.id.filmDescriptionFragmentID);

                    frag.displayTask((StansFilm) parent.getItemAtPosition(position));


                    addBtn = findViewById(R.id.addBtn);
                }
                else
                {
                    //If the orientation is vertical then show the selected item in a new activity.
                    pos = position;
                    startEventInfoActivity(parent, position);

                }
            }
        });





    }

    //==============================================================================================
    //My METHODS
    //==============================================================================================
    private void startEventInfoActivity(AdapterView<?> parent, int position)
    {
        Intent intent = new Intent(this, FilmInfoActivity.class);
        StansFilm tmp = (StansFilm) parent.getItemAtPosition(position);
        intent.putExtra(filmExtra, tmp);
        startActivityForResult(intent ,REQUEST);
    }

    public void onAddBtnClick(View view){

        Intent intent = new Intent(getApplicationContext(), CustomizedFilmInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if((resultCode == RESULT_OK) && (requestCode == REQUEST_CODE)){

            // We retrieve the data and add it
            StansFilm  receivedData = data.getParcelableExtra(getString(R.string.add_film_key));
            myFilms.add(new StansFilm(receivedData.filmTitle, receivedData.filmDirector, receivedData.filmDate));

            //After obtaining the data, we have to update the list
            FilmListFragment eventListFr = (FilmListFragment)getSupportFragmentManager().findFragmentById(R.id.filmFragment);
            ArrayAdapter<StansFilm> eventAdapter = (ArrayAdapter)eventListFr.getListAdapter();  //event Fragment invalidator
            eventAdapter.notifyDataSetChanged();                                                 // for the event fragment to redraw itself
        }

        if((resultCode == RESULT_OK) && (requestCode == REQUEST))  //previous method of the onLongClickListener in the lab4
        {
            String deletionStatus = data.getStringExtra(DELETION);

            if(deletionStatus.contentEquals("TRUE"))
            {

                Toast.makeText(getApplicationContext(), "Ask for deleting", Toast.LENGTH_SHORT).show();

                //Display a DialogFragment
                DialogFragment newFragment = DeleteDialog.newInstance();
                newFragment.show(getSupportFragmentManager(),"DeleteDialogTag");

                //Remember the position of the item that was selected for deletion
                listItemPosition = pos;

            }
        }
    }


    //==============================================================================================
    //DELETE DIALOGUE HANDLER
    //==============================================================================================

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(listItemPosition != -1)
        {
            //Remove the item from the list.
            myFilms.remove(listItemPosition);

            //Invalidate the TaskFragment
            FilmListFragment eventListFragment = (FilmListFragment) getSupportFragmentManager().findFragmentById(R.id.filmFragment);
            ArrayAdapter<StansFilm> eventAdapter = (ArrayAdapter< StansFilm>) eventListFragment.getListAdapter();
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Display SnackBar notification
        View v = findViewById(R.id.addBtn);
        Snackbar.make(v, "Delete canceled", Snackbar.LENGTH_LONG).setAction("Retry?",new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Display a DialogFragment
                DialogFragment newFragment = DeleteDialog.newInstance();
                newFragment.show(getSupportFragmentManager(), "DeleteDialogTag");
            }
        }).show();

    }


    //---------------------------------------------------------------------------//
    //function for the storage of the entries, keep the same name for functions than in lab5
    private boolean saveTasks()
    {
        //Get the sharedPreferences file
        SharedPreferences films = getSharedPreferences(FILMS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = films.edit();
        // Create an editor for the SharedPreferences object
        editor.clear();

        // Add the number of items on the list to the SharedPreferences
        editor.putInt(NUM_FILMS, myFilms.size());

        // Add each task to the SharedPreferences
        for (Integer i = 0; i<myFilms.size();i++){
            editor.putString(FILM + i.toString(), myFilms.get(i).filmTitle);
            editor.putString(DIR+ i.toString(),myFilms.get(i).filmDirector);
            editor.putString(DAT + i.toString(), myFilms.get(i).filmDate);
            editor.putString(PIC + i.toString(), myFilms.get(i).picturePath);

        }


        // Commit the edits
        return editor.commit();
    }

    @Override
    protected void onDestroy()
    {
        // onDestroy: save the elements of the list in the SharedPreferences and external storage file
        super.onDestroy();
        if(!saveTasks()){
            // Display Toast notification if the save is not successful
            Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_LONG).show();

        }
        saveTasksToFile();
    }

    private void restoreTasks(){
        // Restore Preferences
        SharedPreferences tasks = getSharedPreferences(FILMS_FILE, MODE_PRIVATE);
        int numOfTasks = tasks.getInt("NumOfFilms",0);
        if(numOfTasks != 0){
            //Clear the list so it is not retrieved from the SavedInstanceState Bundle
            myFilms.clear();

            // For each item from numOfTasks add a new Task to the List
            for (Integer i =0; i<numOfTasks; i++){

                //Retrieve the stored values
                String title = tasks.getString(FILM + i.toString(), "0");
                String director = tasks.getString(DIR + i.toString(), "0");
                String date = tasks.getString(DAT + i.toString(),"0");
                String pic_path = tasks.getString(PIC + i.toString(), "");
                StansFilm tmp = new StansFilm(title,director,date,pic_path);

                //add the task to the list
                myFilms.add(tmp);

            }
        }

    }

    private void saveTasksToFile(){
        //specify the name of the file
        String filename = "myFilms.txt";
        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            // Create a writer object that will be used to write data to file.
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputStream.getFD()));
            String delim = ";";//Delimiter character

            // Save each task to the file
            for (Integer i=0; i<myFilms.size(); i++){
                StansFilm tmp = myFilms.get(i);
                String line = tmp.filmTitle + delim + tmp.filmDirector + delim + tmp.filmDate + delim + tmp.picturePath;
                writer.write(line);
                writer.newLine();
            }
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}