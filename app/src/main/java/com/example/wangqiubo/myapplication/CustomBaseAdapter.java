package com.example.wangqiubo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangqiubo.myaidl.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqiubo on 2017/5/3.
 */

public class CustomBaseAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<Book> mBookDatas = new ArrayList<>();
    public CustomBaseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_base_list_view, null);
            viewHolder.bookName = (TextView)convertView.findViewById(R.id.boo_name);
            viewHolder.bookIso =   (TextView)convertView.findViewById(R.id.book_iso);
            viewHolder.bookCat = (TextView)convertView.findViewById(R.id.book_category);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.bookName.setText(mBookDatas.get(position).getmBookName());
        viewHolder.bookIso.setText(mBookDatas.get(position).getmBookISO());
        viewHolder.bookCat.setText(mBookDatas.get(position).getmBookCatagery());

        return convertView;
    }

    private final class ViewHolder{
        public TextView bookName;
        public TextView bookIso;
        public TextView bookCat;
    }

    public List<Book> getBookDatas(){
        return mBookDatas;
    }

    public void addBookData(Book bookData){
        mBookDatas.add(bookData);
    }

    public void setBookDatas(List<Book> bookDatas){
        this.mBookDatas = bookDatas;
    }

}
