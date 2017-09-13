package com.example.rohit.task.Activities.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.task.Activities.Database.BookData;
import com.example.rohit.task.R;

/**
 * Created by ROHIT on 9/11/2017.
 */

public class BookDetail extends ActionBarActivity {

    TextView book,author,isbn;
    Button delete,edit;

    String book_name,author_name,isbn_num;

    SharedPreferences sharedPreferences;

    private BookData bookData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookData = new BookData(this);

        Toolbar detailToolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("DEFAULT_VALUES",MODE_PRIVATE);


        book = (TextView)findViewById(R.id.book_name_d);
         author = (TextView)findViewById(R.id.author_name_d);
         isbn = (TextView)findViewById(R.id.isbn_d);

         delete = (Button)findViewById(R.id.delete);
         edit = (Button)findViewById(R.id.edit);

         book_name = getIntent().getStringExtra("book_name");
         author_name = getIntent().getStringExtra("author_name");
         isbn_num = getIntent().getStringExtra("isbn");



        book.setText("Book's Name - " + book_name);
        author.setText("Author's Name - " + author_name);
        isbn.setText("ISBN Number - " + isbn_num);

        onClickListners();
    }

    private void onClickListners() {

        final int column = getIntent().getIntExtra("column_position", 0);


        /** delete the Book **/
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookData.deleteData(Integer.toString(column));
                /*Toast.makeText(BookDetail.this,"Column " + column,Toast.LENGTH_SHORT).show();*/

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookDetail.this,"Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BookDetail.this,ShowBooks.class));
                        finish();
                    }
                },600);
            }
        });

        /** Edit the Book **/
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("present_column",column);
                editor.apply();

                /*int column_updated = sharedPreferences.getInt("present_column",0);*/

                /*Toast.makeText(BookDetail.this,"Column " + column_updated,Toast.LENGTH_SHORT).show();*/

                /**Setting up delay of 200 microseconds **/
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        /**Passing some values to EditDetails class thorugh Intent **/
                        Intent to_edit = new Intent(BookDetail.this,EditDetails.class);
                        to_edit.putExtra("present_book_name", book_name);
                        to_edit.putExtra("present_author_name", author_name);
                        to_edit.putExtra("present_isbn_num", isbn_num);

                        startActivity(to_edit);
                        finish();
                    }
                },200);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
