package com.example.csousa.booksinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.csousa.booksinventory.data.BookContract.BookEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookCursorAdapter extends CursorAdapter {

   static class ViewHolder {
       @BindView(R.id.name) TextView nameTextView;
       @BindView(R.id.genre) TextView genreTextView;
       @BindView(R.id.price) TextView priceTextView;
       @BindView(R.id.quantity) TextView qtyTextView;
       @BindView(R.id.sale_btn) Button sellButton;

       public ViewHolder(View view) {
           ButterKnife.bind(this, view);
       }
   }

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final ViewHolder holder = (ViewHolder) view.getTag();

        // Find the columns of the book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
        int genreColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_GENRE);
        int qtyColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
        //int supNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
        //int supPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        String priceValue = cursor.getString(priceColumnIndex);
        String qtyValue = cursor.getString(qtyColumnIndex);
        //String supName = cursor.getString(supNameColumnIndex);
        //String supPhone = cursor.getString(supPhoneColumnIndex);

        String bookGenre;
        int genre = cursor.getInt(genreColumnIndex);
        switch (genre) {
            case BookEntry.GENRE_SCIFI:
                bookGenre = context.getString(R.string.genre_scifi);
                break;
            case BookEntry.GENRE_HORROR:
                bookGenre = context.getString(R.string.genre_horror);
            case BookEntry.GENRE_COMEDY:
                bookGenre = context.getString(R.string.genre_comedy);
                break;
            default:
                bookGenre = context.getString(R.string.genre_unknown);
                break;
        }

       // Update the TextViews with the attributes for the current book
       holder.nameTextView.setText(bookName);
       holder.genreTextView.setText(bookGenre);

       holder.priceTextView.setText(priceValue);
       holder.qtyTextView.setText(qtyValue);

       final int position = cursor.getPosition();

        holder.sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                int qtyColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
                String quantityStr = cursor.getString(qtyColumnIndex);

                if  ((Integer.valueOf(quantityStr) -1) < 0) {
                    InventoryActivity ia = (InventoryActivity) context;
                    ia.showNotOnStockToast();
                    return;
                }

                int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
                int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
                int genreColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_GENRE);
                int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);

                String id = cursor.getString(idColumnIndex);
                String nameStr = cursor.getString(nameColumnIndex);
                String genre = cursor.getString(genreColumnIndex);
                String priceStr = cursor.getString(priceColumnIndex);

                InventoryActivity ia = (InventoryActivity) context;

                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_PRODUCT_NAME, nameStr);
                values.put(BookEntry.COLUMN_PRODUCT_GENRE, genre);
                values.put(BookEntry.COLUMN_QUANTITY, Integer.valueOf(quantityStr) -1);
                values.put(BookEntry.COLUMN_PRICE, priceStr);

                ia.sellBook(Integer.valueOf(id), values);
            }
        });
    }
}
