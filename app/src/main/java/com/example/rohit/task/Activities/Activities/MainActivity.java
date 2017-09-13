package com.example.rohit.task.Activities.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rohit.task.Activities.Database.BookData;
import com.example.rohit.task.R;

/**
 * Created by ROHIT on 9/11/2017.
 */

public class MainActivity extends ActionBarActivity {

    Button Add,ShowBook;

    BookData bookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         bookData = new BookData(this);

         Add = (Button)findViewById(R.id.add_book);
         ShowBook = (Button)findViewById(R.id.book_list);

        /** Adding the Book,moving to class AddBook on click **/
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddBook.class));
            }
        });

        /** Displaying all the Book,moving to class ShowBook on click **/

        ShowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ShowBooks.class));
            }
        });

    }


}
