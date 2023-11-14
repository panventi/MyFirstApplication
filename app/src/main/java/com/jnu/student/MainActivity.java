package com.jnu.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.jnu.student.data.Book;    // 导入自定义的Book类

public class MainActivity extends AppCompatActivity {
    BookAdapter bookAdapter = null;     // 声明一个BookAdapter对象
    ArrayList<Book> bookItems = new ArrayList<>();    // 声明一个Book类型的ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    // 设置布局文件

        RecyclerView booksRecyclerView = findViewById(R.id.recycler_view_books);     // 获取RecyclerView控件
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));    // 设置RecyclerView的布局管理器为LinearLayoutManager

        // 添加书籍到bookItems列表
        bookItems.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookItems.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookItems.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

        bookAdapter = new BookAdapter(bookItems);   // 创建一个BookAdapter对象，并传入bookItems列表

        booksRecyclerView.setAdapter(bookAdapter);   // 设置RecyclerView的适配器为bookAdapter

    }

    // 嵌套的BookAdapter类，继承自RecyclerView.Adapter
    public static class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        private final ArrayList<Book> localDataSet;   // 声明一个Book类型的ArrayList
        public BookAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }  // 初始化localDataSet

        // 嵌套的ViewHolder类，继承自RecyclerView.ViewHolder
        public static class ViewHolder extends RecyclerView.ViewHolder{
            private final TextView textViewBookTitle;   // 声明一个TextView对象
            private final ImageView imageViewBookCover;  // 声明一个ImageView对象
            public ViewHolder(View view) {
                super(view);
                textViewBookTitle = view.findViewById(R.id.text_view_book_title);  // 获取布局文件中的TextView控件
                imageViewBookCover = view.findViewById(R.id.image_view_book_cover);  // 获取布局文件中的ImageView控件
            }
            public TextView getTextViewBookTitle() {
                return textViewBookTitle;
            }  // 返回TextView对象
            public ImageView getImageViewBookCover() {
                return imageViewBookCover;
            }   // 返回ImageView对象
        }
        // 创建新的视图（由布局管理器调用）
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // 创建一个新的视图，定义列表项的UI
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.shop_item_row, viewGroup, false); // 加载布局文件shop_item_row.xml

            return new ViewHolder(view);  // 返回ViewHolder对象
        }

        // 替换视图的内容（由布局管理器调用）
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // 获取数据集中该位置的元素，并将视图的内容替换为该元素的内容
            viewHolder.getTextViewBookTitle()
                    .setText(localDataSet.get(position).getTitle());  // 设置TextView的文本
            viewHolder.getImageViewBookCover()
                    .setImageResource(localDataSet.get(position).getCoverResourceId());   // 设置ImageView的图片资源
        }

        // 返回数据集的大小（由布局管理器调用）
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}