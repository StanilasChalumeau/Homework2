package fr.stanislas.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;




public class FilmInfoActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 2; // Request code for the addclick of the main activity
    FloatingActionButton fAddbtn, fDeleteBtn;
    private String DELETION_STATUS = "FALSE";



    // if the user wants to delete DELETION_STATUS = "True"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);

        fAddbtn = findViewById(R.id.addButton);
        fAddbtn.hide();

        fDeleteBtn = findViewById(R.id.fdeletBtn);
        fDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DELETION_STATUS = "TRUE";

                Intent intent = new Intent();
                intent.putExtra(MainActivity.DELETION, DELETION_STATUS);
                setResult(RESULT_OK, intent);
                finish();


            }
        });




    }

    //if we click on the fab + it launches a new activity where we can enter the data, these data are given back to this activity
    public void onAddBtnClick(View view){

        Intent intent = new Intent(getApplicationContext(), CustomizedFilmInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

}
