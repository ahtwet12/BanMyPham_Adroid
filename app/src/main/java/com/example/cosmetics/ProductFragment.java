package com.example.cosmetics;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class ProductFragment extends Fragment {

    //khai báo
    private EditText editmasp, edittensp, editmota,editdongia, editsoluong;
    private Button btnthem, btnsua, btnxoa, btntruyvan , btnthemanh;
    private String selectedImagePath;

    private ListView lv_product;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> myadapter;
    private SQLiteDatabase mydatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);

        //ánh xạ
        editmasp =  view.findViewById(R.id.editmasp);
        edittensp =  view.findViewById(R.id.edittensp);
        editmota =  view.findViewById(R.id.editmota);
        editdongia =  view.findViewById(R.id.editdongia);
        editsoluong =  view.findViewById(R.id.editsoluong);

        btnthem =  view.findViewById(R.id.btnthem);
        btnsua =  view.findViewById(R.id.btnsua);
        btnxoa =  view.findViewById(R.id.btnxoa);
        btntruyvan =  view.findViewById(R.id.btntruyvan);
        btnthemanh =  view.findViewById(R.id.btnthemanh);

        lv_product =  view.findViewById(R.id.lv_product);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mylist);
        lv_product.setAdapter(myadapter);

        //tao csdl
        mydatabase = getActivity().openOrCreateDatabase("mypham.db", MODE_PRIVATE, null);
        try {
            // Tạo bảng nếu chưa tồn tại
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tblproduct (" +
                    "masp INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tensp TEXT, " +
                    "mota TEXT, " +
                    "dongia REAL, " +
                    "soluong INTEGER, " +
                    "hinhanh TEXT)";
            mydatabase.execSQL(createTableQuery); // Thực thi câu lệnh SQL tạo bảng

        } catch (Exception e) {
            Log.e("Error","Table đã tồn tại");
        }

        //xly nút thêm ảnh
        btnthemanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Gallery
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        //xly nút thêm
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = editmasp.getText().toString();
                String tensp = edittensp.getText().toString();
                String mota = editmota.getText().toString();
                String dongia = editdongia.getText().toString();
                String soluong = editsoluong.getText().toString();

                if (tensp.isEmpty() || dongia.isEmpty() || soluong.isEmpty() || selectedImagePath == null) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin và chọn ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues myvalue = new ContentValues();
                myvalue.put("masp",masp);
                myvalue.put("tensp",tensp);
                myvalue.put("mota",mota);
                myvalue.put("dongia",dongia);
                myvalue.put("soluong",soluong);
                myvalue.put("hinhanh", selectedImagePath); // Lưu đường dẫn ảnh

                String msg = "";
                if(mydatabase.insert("tblproduct",null,myvalue) == -1){
                    msg = "Fail to insert record!";
                }else{
                    msg = "Insert record Successfully";
                }
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();


            }
        });

        //xly nut delete
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = editmasp.getText().toString();
                int n = mydatabase.delete("tblproduct","masp = ?", new String[]{masp});
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
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = editmasp.getText().toString();
                String tensp = edittensp.getText().toString();
                String mota = editmota.getText().toString();
                String dongia = editdongia.getText().toString();
                String soluong = editsoluong.getText().toString();

                ContentValues myvalue = new ContentValues();
                myvalue.put("tensp",tensp);
                myvalue.put("mota",mota);
                myvalue.put("dongia",dongia);
                myvalue.put("soluong",soluong);

                int n = mydatabase.update("tblproduct",myvalue,"masp = ?", new String[]{masp});
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
        btntruyvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.clear();
                Cursor c = mydatabase.query("tblproduct", null, null, null, null, null, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2) + " - " +
                                    c.getString(3) + " - " + c.getString(4) + " - " + c.getString(5);
                            mylist.add(data);
                        } while (c.moveToNext());
                    }
                    c.close();
                }

                myadapter.notifyDataSetChanged();
            }
        });


        return view;
    }

    //lấy đường dẫn ảnh:
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null) {
            // Lấy URI của ảnh được chọn
            Uri selectedImageUri = data.getData();

            // Lấy đường dẫn thật của ảnh
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                selectedImagePath = cursor.getString(columnIndex);
                cursor.close();
            }

            // Hiển thị ảnh trong ListView (nếu cần)
            if (selectedImagePath != null) {
                mylist.add("Hình ảnh: " + selectedImagePath); // Thêm ảnh vào danh sách
                myadapter.notifyDataSetChanged();
            }
        }
    }
}
