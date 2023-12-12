package com.jnu.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.student.data.Book;
import com.jnu.student.data.DataBank;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    ActivityResultLauncher<Intent> launcher;
    BookAdapter bookAdapter;
    ArrayList<Book> bookItems;
    DataBank bookBank = new DataBank();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_list, container, false);
        //为RecyclerView设置线性布局
        RecyclerView booksRecyclerView = rootView.findViewById(R.id.recycler_view_books);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //从内部文件中读取
        bookItems = bookBank.LoadBookItems(requireContext().getApplicationContext());
        //如果内部文件中没有数据，预添加三条数据
        if( bookItems.isEmpty()){
            bookItems.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
            bookItems.add(new Book("创新工程实践", R.drawable.book_no_name));
            bookItems.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        }

        bookAdapter = new BookAdapter(bookItems);
        //将bookAdapter设置为booksRecyclerView的适配器
        booksRecyclerView.setAdapter(bookAdapter);
        //为 booksRecyclerView 注册上下文菜单,长按某个项时弹出一个选项菜单
        registerForContextMenu(booksRecyclerView);
        //使用registerForActivityResult()方法注册ActivityResultLauncher
        launcher = registerForActivityResult(
                //创建一个ActivityResultCallback的实现来处理返回的结果
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // 获取返回的数据
                        Intent data = result.getData();
                        //处理返回的数据
                        String returnTitle = data.getStringExtra("title");
                        if(returnTitle==null || returnTitle.equals("")) return;
                        String action = data.getAction();
                        if(action.equals("add")){
                            bookItems.add(new Book(returnTitle, R.drawable.book_no_name));
                            bookAdapter.notifyItemInserted(bookItems.size());
                        } else if (action.equals("modify")) {
                            Book book = bookItems.get(bookAdapter.getPosition());
                            book.setTitle(returnTitle);
                            bookItems.set(bookAdapter.getPosition(),book);
                            bookAdapter.notifyItemChanged(bookAdapter.getPosition());
                        }
                        bookBank.saveBookItems(requireContext().getApplicationContext(),bookItems);
                    }else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );
        return rootView;
    }

    public boolean onContextItemSelected(MenuItem item) {
        //创建用于启动BookDetailsActivity的Intent对象
        Intent intent = new Intent(requireContext(), BookDetailsActivity.class);
        //处理菜单的某一项
        switch (item.getItemId()) {
            case 0:
                intent.setAction("add");
                //调用launch方法启动另一个activity
                launcher.launch(intent);
                break;
            case 1:
                //添加确认删除的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("确认删除");
                builder.setMessage("确定要删除图书"+bookAdapter.getPosition()+"吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 执行删除操作
                        bookItems.remove(bookAdapter.getPosition());
                        bookBank.saveBookItems(requireContext().getApplicationContext(),bookItems);
                        bookAdapter.notifyItemRemoved(bookAdapter.getPosition());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭对话框并释放相关资源
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 2:
                intent.setAction("modify");
                Book bookItem = bookItems.get(bookAdapter.position);
                intent.putExtra("title",bookItem.getTitle());
                launcher.launch(intent);     //调用launch方法启动另一个修改activity
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

        private ArrayList<Book> localDataSet;
        //保存当前item的position
        private int position;
        //Initialize the dataset of the Adapter
        public BookAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }
        public int getPosition() {
            return position;
        }
        public void setPosition(int position) {
            this.position = position;
        }

        // Provide a reference to the type of views that you are using
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewBookTitle;
            private final ImageView imageViewBookCover;
            public ViewHolder(View view) {
                super(view);
                textViewBookTitle = (TextView) view.findViewById(R.id.text_view_book_title);
                imageViewBookCover = (ImageView) view.findViewById(R.id.image_view_book_cover);
                view.setOnCreateContextMenuListener(this);
            }
            public TextView getTextViewBookTitle() {
                return textViewBookTitle;
            }
            public ImageView getImageViewBookCover() {
                return imageViewBookCover;
            }
            //重写 onCreateContextMenu() 方法,创建上下文菜单样式
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                menu.add(0,0, this.getAdapterPosition(),"添加");
                menu.add(0,1, this.getAdapterPosition(),"删除"+this.getAdapterPosition());
                menu.add(0,2, this.getAdapterPosition(),"修改"+this.getAdapterPosition());
            }
        }
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_item_row, viewGroup, false);
            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextViewBookTitle()
                    .setText(localDataSet.get(position).getTitle());
            viewHolder.getImageViewBookCover()
                    .setImageResource(localDataSet.get(position).getCoverResourceId());
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setPosition(viewHolder.getLayoutPosition());
                    return false;
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
}