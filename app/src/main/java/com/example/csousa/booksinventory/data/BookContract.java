package com.example.csousa.booksinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.csousa.booksinventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BOOKS = "books";


    public static final class BookEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        //TABLE
        public final static String TABLE_NAME = "books";

        //COLUMNS
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_GENRE = "product_genre";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";


        public static final int GENRE_UNKNOWN = 0;
        public static final int GENRE_SCIFI = 1;
        public static final int GENRE_HORROR = 2;
        public static final int GENRE_COMEDY = 3;


        //SANITY CHECKS
        public static boolean isValidGenre(int genre) {
            if (genre == GENRE_UNKNOWN || genre == GENRE_SCIFI || genre == GENRE_HORROR || genre == GENRE_COMEDY) {
                return true;
            }
            return false;
        }
    }

}