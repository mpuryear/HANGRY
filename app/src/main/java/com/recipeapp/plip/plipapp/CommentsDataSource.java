package com.recipeapp.plip.plipapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hanani on 4/20/16.
 */
public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private Favorites_Database dbHelper;
    private String[] allColumns = { Favorites_Database.COLUMN_ID,
            Favorites_Database.COLUMN_COMMENT };

    public CommentsDataSource(Context context) {
        dbHelper = new Favorites_Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(int id, String comment) {
        ContentValues values = new ContentValues();
        values.put(Favorites_Database.COLUMN_COMMENT, comment);
        values.put(Favorites_Database.COLUMN_ID, id);
        long insertId = database.insert(Favorites_Database.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(Favorites_Database.TABLE_NAME,
                allColumns, Favorites_Database.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    // Original without ID
    /*public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(Favorites_Database.COLUMN_COMMENT, comment);
        long insertId = database.insert(Favorites_Database.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(Favorites_Database.TABLE_NAME,
                allColumns, Favorites_Database.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }*/

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(Favorites_Database.TABLE_NAME, Favorites_Database.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(Favorites_Database.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}


