package com.example.cosmetics;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {
    //khai báo
    private EditText edithoten, editdienthoai,editdiachi, editemail, editmakh;
    private Button btnadd, btnupdate, btndelete, btnshow;
    private ListView lv_customer;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> myadapter;
    private SQLiteDatabase mydatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer,container,false);

        //ánh xạ
        editmakh =  view.findViewById(R.id.editmakh);
        edithoten =  view.findViewById(R.id.edithoten);
        editdienthoai =  view.findViewById(R.id.editdienthoai);
        editdiachi =  view.findViewById(R.id.editdiachi);
        editemail =  view.findViewById(R.id.editemail);

        btnadd =  view.findViewById(R.id.btnadd);
        btnupdate =  view.findViewById(R.id.btnupdate);
        btndelete =  view.findViewById(R.id.btndelete);
        btnshow =  view.findViewById(R.id.btnshow);

        lv_customer =  view.findViewById(R.id.lv_customer);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mylist);
        lv_customer.setAdapter(myadapter);

        //tao csdl
        mydatabase = getActivity().openOrCreateDatabase("mypham.db", MODE_PRIVATE, null);

        try {
            // Tạo bảng nếu chưa tồn tại
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tblcustomer (" +
                    "makh INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "hoten TEXT, " +
                    "dienthoai TEXT, " +
                    "diachi TEXT, " +
                    "email TEXT)";
            mydatabase.execSQL(createTableQuery); // Thực thi câu lệnh SQL tạo bảng

        } catch (Exception e) {
            Log.e("Error","Table đã tồn tại");
        }

        //xly nút add
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makh = editmakh.getText().toString();
                String hoten = edithoten.getText().toString();
                String dienthoai = editdienthoai.getText().toString();
                String diachi = editdiachi.getText().toString();
                String email = editemail.getText().toString();

                if (hoten.isEmpty() || dienthoai.isEmpty() || diachi.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues myvalue = new ContentValues();
                myvalue.put("makh",makh);
                myvalue.put("hoten",hoten);
                myvalue.put("dienthoai",dienthoai);
                myvalue.put("diachi",diachi);
                myvalue.put("email",email);
                String msg = "";
                if(mydatabase.insert("tblcustomer",null,myvalue) == -1){
                    msg = "Fail to insert record!";
                }else{
                    msg = "Insert record Successfully";
                }
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();


            }
        });

        //xly nut delete
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makh = editmakh.getText().toString();
                int n = mydatabase.delete("tblcustomer","makh = ?", new String[]{makh});
                String msg = "";
                if(n == 0){
                    msg = "No record to Delete";
                }else{
                    msg = n + "record is delete";
                }
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

            }
        });
        //xly nút update
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edithoten.getText().toString();
                String dienthoai = editdienthoai.getText().toString();
                String diachi = editdiachi.getText().toString();
                String email = editemail.getText().toString();
                String makh = editmakh.getText().toString();

                ContentValues myvalue = new ContentValues();
                myvalue.put("hoten",hoten);
                myvalue.put("dienthoai",dienthoai);
                myvalue.put("diachi",diachi);
                myvalue.put("email",email);

                int n = mydatabase.update("tblcustomer",myvalue,"makh = ?", new String[]{makh});
                String msg = "";
                if(n == 0){
                    msg = "No record to Update";
                }else{
                    msg = n + " record is update";
                }
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

            }
        });
        //xly nút show
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.clear();
                Cursor c = mydatabase.query("tblcustomer", null, null, null, null, null, null);

                if (c != null && c.moveToFirst()) { // Kiểm tra nếu Cursor không null và có dữ liệu
                    do {
                        String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2) + " - " +
                                c.getString(3) + " - " + c.getString(4);
                        mylist.add(data);
                    } while (c.moveToNext()); // Lặp qua từng bản ghi
                    c.close(); // Đóng Cursor sau khi sử dụng
                }

                myadapter.notifyDataSetChanged(); // Cập nhật danh sách hiển thị
            }
        });


        return view;
    }
}
