package com.example.rohit.task.Activities.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rohit.task.Activities.Database.BookData;
import com.example.rohit.task.Activities.RecyclerView.CustomAdapter;
import com.example.rohit.task.Activities.RecyclerView.DataItems;
import com.example.rohit.task.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROHIT on 9/11/2017.
 */

public class ShowBooks extends ActionBarActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    private BookData bookData;

    Cursor result;

    String[] bookName = new String[30];
    String[] authorName = new String[30];
    String[] isbn = new String[30];
    byte[] cover ;

    /*String bookName;
    String authorName;
    String isbn;*/
    /*byte[] cover = new byte[30];*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_books);

        bookData = new BookData(this);


        Toolbar showToolbar = (Toolbar)findViewById(R.id.show_books_toolbar);
        setSupportActionBar(showToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         checkData();

    }



    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    private void checkData() {

        result = bookData.getAllData();


        /** In case there is nothing in the database **/
        if (result.getCount() == 0)
        {
            showMessage("Error Nothing Found","Database is empty,add some books first");

            /*Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);*/
            return;
        }


        int i = 0;

        /**Autoincrement to next column **/
        while (result.moveToNext())
        {
            bookName[i] = result.getString(1);
            authorName[i] = result.getString(2);
            isbn[i] = result.getString(3);
            /*cover = result.getBlob(4);*/
            i++;
        }

         adapter = new CustomAdapter(getDetails(bookName, authorName, isbn),this);
         /*adapter = new CustomAdapter(getDetails(bookName, authorName, isbn,cover),this);*/


       /* while (result.moveToNext())
        {
            bookName = result.getString(1);
            authorName = result.getString(2);
            isbn = result.getString(3);
            *//*cover[i] = result.getBlob(4);*//*
            //i++;

            adapter = new CustomAdapter(getDetails(bookName, authorName, isbn),this);
        }*/
        recyclerView.setAdapter(adapter);

    }


    /** Adding all the details in the database to the list **/
    private List<DataItems> getDetails(String[] bookName,String[] authorName, String[] isbn) {
        List<DataItems> myData = new ArrayList<>();

        for (int i = 0; i<result.getCount() ; i++)
        {
            DataItems items = new DataItems();

            items.setBookName(bookName[i]);
            items.setBookAuthor(authorName[i]);
            items.setBookIsbn(isbn[i]);
            items.setBookCover(cover);
            myData.add(items);
        }

        return myData;
    }


   /* private List<DataItems> getDetails(String bookName,String authorName, String isbn) {
        List<DataItems> myData = new ArrayList<>();


        DataItems items = new DataItems();

        items.setBookName(bookName);
        items.setBookAuthor(authorName);
        items.setBookIsbn(isbn);
        *//*items.setBookCover(cover);*//*
        myData.add(items);


        return myData;
    }*/

    private void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
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
