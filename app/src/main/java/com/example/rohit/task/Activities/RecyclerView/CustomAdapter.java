package com.example.rohit.task.Activities.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.task.Activities.Activities.AddBook;
import com.example.rohit.task.Activities.Activities.BookDetail;
import com.example.rohit.task.Activities.Activities.ShowBooks;
import com.example.rohit.task.R;

import java.util.Collections;
import java.util.List;


/**
 * Created by ROHIT on 9/11/2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<DataItems> dataItemseList = Collections.emptyList();
    Context context;

    /**Adapter **/
    public CustomAdapter(List<DataItems> dataItemseList,Context context)
    {
        layoutInflater = LayoutInflater.from(context);
        this.dataItemseList = dataItemseList;
        this.context =context;
    }

    /** fetching the data_item xml file **/
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        /*View view = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_items,viewGroup,false);*/
        View view = layoutInflater.inflate(R.layout.data_items, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view,context,dataItemseList);

        return viewHolder;
    }


    /**Bing all the items in data xml file **/
    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder,final int  position) {



        DataItems items = dataItemseList.get(position);
        viewHolder.book.setText("Book's Name - " + items.getBookName());
        viewHolder.author.setText("Author's Name - " + items.getBookAuthor());

        /*byte[] Image = items.getBookCover();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        viewHolder.cover.setImageBitmap(bitmap);*/

        /*for (int i = 0; i<=position ; i++)
        {
            DataItems items = dataItemseList.get(position);
            viewHolder.book.setText(items.getBookName());
            viewHolder.author.setText(items.getBookAuthor());
       }*/

    }

    @Override
    public int getItemCount() {
        return dataItemseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView book;
        TextView author;
        ImageView cover;
        public LinearLayout details;
        List<DataItems> dataItemseList = Collections.emptyList();
        Context context;

        public ViewHolder(View itemView,Context context,List<DataItems> dataItemsList) {
            super(itemView);
            this.dataItemseList = dataItemsList;
            this.context = context;
            itemView.setOnClickListener(this);

            book = (TextView)itemView.findViewById(R.id.book);
            author = (TextView)itemView.findViewById(R.id.author);
            cover = (ImageView)itemView.findViewById(R.id.cover);
            details = (LinearLayout)itemView.findViewById(R.id.detail_view);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            DataItems dataItems = this.dataItemseList.get(position);

            /*Toast.makeText(context,"onClick " + dataItems.getBookName(),Toast.LENGTH_SHORT).show();*/

            Intent intent = new Intent(this.context, BookDetail.class);
            intent.putExtra("book_name",dataItems.getBookName());
            intent.putExtra("author_name",dataItems.getBookAuthor());
            intent.putExtra("isbn",dataItems.getBookIsbn());
            intent.putExtra("column_position", position + 1);
            this.context.startActivity(intent);


            /*context.startActivity(new Intent(context,BookDetail.class));*/
        }
    }
}
