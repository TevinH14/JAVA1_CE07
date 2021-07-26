package com.example.hamiltontevin_ce07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

class BooksAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<Books> mBooksArrayList;

    public BooksAdapter(Context mContext, ArrayList<Books> bookList) {
        this.mContext = mContext;
        this.mBooksArrayList = bookList;
    }


    @Override
    public int getCount() {
        if(mBooksArrayList.size() > 0 ){
            return mBooksArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int pos) {
        if(mBooksArrayList != null && pos >= 0 & pos < mBooksArrayList.size()){
            return mBooksArrayList.get(pos);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Books newBooks = (Books)getItem(pos);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.book_display_layout,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else{
            vh = (ViewHolder)convertView.getTag();
        }
        // check if the new person object is empty.
        if(newBooks != null){
            vh.imageView.setImageUrl(newBooks.getImage());
            vh.tv_bookName.setText(newBooks.getTitle());
        }
        return convertView;
    }

    static class ViewHolder{
        final SmartImageView imageView;
        final TextView tv_bookName;

        ViewHolder(View layout) {
            this.imageView = layout.findViewById(R.id.siv_bookImage);
            this.tv_bookName = layout.findViewById(R.id.tv_titles);
        }
    }

}
