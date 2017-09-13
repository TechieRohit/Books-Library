package com.example.rohit.task.Activities.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.task.Activities.Database.BookData;
import com.example.rohit.task.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by ROHIT on 9/11/2017.
 */

public class EditDetails extends ActionBarActivity {

    BookData myDb;

    EditText name,author,isbn = null;
    Button save,cover;

    ImageView imageView;

    TextView textView;

    final static int REQUEST_CODE = 999;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        myDb = new BookData(this);

        Toolbar addToolbar = (Toolbar)findViewById(R.id.add_toolbar);
        setSupportActionBar(addToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("DEFAULT_VALUES",MODE_PRIVATE);

        name = (EditText)findViewById(R.id.book_name);
        author = (EditText)findViewById(R.id.author_name);
        isbn = (EditText)findViewById(R.id.isbn_number);

        textView = (TextView)findViewById(R.id.add_book);
        textView.setText("Edit Book");

        String p_name = getIntent().getStringExtra("present_book_name");
        String p_author = getIntent().getStringExtra("present_author_name");
        String p_isbn = getIntent().getStringExtra("present_isbn_num");

       /* final int column = getIntent().getIntExtra("column_position", 0);

        Toast.makeText(EditDetails.this,column,Toast.LENGTH_LONG).show();*/

        Toast.makeText(EditDetails.this, p_name + p_author + p_isbn,Toast.LENGTH_LONG).show();

        name.setText(p_name);
        author.setText(p_author);
        isbn.setText(p_isbn);


        imageView = (ImageView)findViewById(R.id.imageView_add);

        save = (Button)findViewById(R.id.save_book);
        save.setText("Finish");


        cover = (Button)findViewById(R.id.book_cover);

        /** gettig the cover **/
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditDetails.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );

            }
        });


        /**Saving the changes made **/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = name.getText().toString();
                String Author = author.getText().toString();
                String Isbn = isbn.getText().toString();

                int column = sharedPreferences.getInt("present_column",0);

                /*Toast.makeText(EditDetails.this,column,Toast.LENGTH_LONG).show();*/

                if ( Name.equals("") || Author.equals("") || Isbn.equals("") ){
                    /*Toast.makeText(AddBook.this,"Enter all the information",Toast.LENGTH_LONG).show();*/
                    showMessage("Cannot Enter the Book","You need to enter all the details of the book");
                }else {
                    myDb.updateData(Integer.toString(column), Name,
                            author.getText().toString(),
                            Isbn);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*Toast.makeText(EditDetails.this, "Details Edited", Toast.LENGTH_SHORT).show();*/
                            startActivity(new Intent(EditDetails.this,ShowBooks.class));
                            finish();
                        }
                    }, 800);

                }

                /*myDb.insertData(name.getText().toString(),
                        author.getText().toString(),
                        isbn.getText().toString(),imageViewToByte(imageView));*/


               /*imageView.setImageResource(R.mipmap.ic_launcher);*/
            }
        });
    }

    /** Creating alert in case something goes wrong **/
    private void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    /**Converting image to bytes **/
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK ){
            /*Uri uri = data.getData();*/

            try {
               /* InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);*/

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                Toast.makeText(EditDetails.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else {
            Toast.makeText(EditDetails.this,"You haven't pick the image",Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
