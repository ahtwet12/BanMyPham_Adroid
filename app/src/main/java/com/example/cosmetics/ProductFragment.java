package com.example.cosmetics;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProductFragment extends Fragment {

    // Khai báo
    private EditText editmasp, edittensp, editmota, editdongia, editsoluong;
    private Button btnthem, btnsua, btnxoa, btntruyvan, btnthemanh;
    private ImageView imgHinh;
    private ImageButton imgbtnFolder;

    private ListView lv_product;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> myadapter;
    private SQLiteDatabase mydatabase;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Ánh xạ
        editmasp = view.findViewById(R.id.editmasp);
        edittensp = view.findViewById(R.id.edittensp);
        editmota = view.findViewById(R.id.editmota);
        editdongia = view.findViewById(R.id.editdongia);
        editsoluong = view.findViewById(R.id.editsoluong);

        btnthem = view.findViewById(R.id.btnthem);
        btnsua = view.findViewById(R.id.btnsua);
        btnxoa = view.findViewById(R.id.btnxoa);
        btntruyvan = view.findViewById(R.id.btntruyvan);
        imgHinh = view.findViewById(R.id.imgHinh);
        imgbtnFolder = view.findViewById(R.id.imgbtnFolder);

        lv_product = view.findViewById(R.id.lv_product);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mylist);
        lv_product.setAdapter(myadapter);

        // Tạo CSDL
        mydatabase = getActivity().openOrCreateDatabase("mypham.db", MODE_PRIVATE, null);
        try {
            // Tạo bảng nếu chưa tồn tại
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tblproduct (" +
                    "masp TEXT PRIMARY KEY , " +
                    "tensp TEXT, " +
                    "mota TEXT, " +
                    "dongia INTEGER, " +
                    "soluong INTEGER, " +
                    "hinhanh TEXT)";
            mydatabase.execSQL(createTableQuery); // Thực thi câu lệnh SQL tạo bảng

        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        // Xử lý nút thêm ảnh
        imgbtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        // Xử lý nút thêm
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = editmasp.getText().toString();
                String tensp = edittensp.getText().toString();
                String mota = editmota.getText().toString();
                int dongia = Integer.parseInt(editdongia.getText().toString());
                int soluong = Integer.parseInt(editsoluong.getText().toString());

                if (tensp.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues myvalue = new ContentValues();
                myvalue.put("masp", masp);
                myvalue.put("tensp", tensp);
                myvalue.put("mota", mota);
                myvalue.put("dongia", dongia);
                myvalue.put("soluong", soluong);

                String msg = "";
                if (mydatabase.insert("tblproduct", null, myvalue) == -1) {
                    msg = "Fail to Insert Record!";
                } else {
                    msg = "Insert Record Sucessfully!";
                }
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút delete
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = editmasp.getText().toString();
                int n = mydatabase.delete("tblproduct", "masp = ?", new String[]{masp});
                String msg = "";
                if (n == 0) {
                    msg = "No record to Delete";
                } else {
                    msg = n + " record is delete";
                }
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút update
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = editmasp.getText().toString();
                String tensp = edittensp.getText().toString();
                String mota = editmota.getText().toString();
                int dongia = Integer.parseInt(editdongia.getText().toString());
                int soluong = Integer.parseInt(editsoluong.getText().toString());

                ContentValues myvalue = new ContentValues();
                myvalue.put("tensp", tensp);
                myvalue.put("mota", mota);
                myvalue.put("dongia", dongia);
                myvalue.put("soluong", soluong);

                int n = mydatabase.update("tblproduct", myvalue, "masp = ?", new String[]{masp});
                String msg = "";
                if (n == 0) {
                    msg = "No record to Update";
                } else {
                    msg = n + " record is update";
                }
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút show
        btntruyvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.clear();  // Xóa dữ liệu cũ trong list

                // Truy vấn tất cả sản phẩm trong bảng tblproduct
                Cursor c = mydatabase.query("tblproduct", null, null, null, null, null, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            // Lấy dữ liệu từ cursor và kiểm tra xem tên cột có hợp lệ không
                            int tenspIndex = c.getColumnIndex("tensp");
                            int motaIndex = c.getColumnIndex("mota");
                            int dongiaIndex = c.getColumnIndex("dongia");

                            // Nếu cột tồn tại (index >= 0), lấy dữ liệu và thêm vào danh sách
                            if (tenspIndex >= 0 && motaIndex >= 0 && dongiaIndex >= 0) {
                                String tensp = c.getString(tenspIndex);
                                String mota = c.getString(motaIndex);
                                int dongia = c.getInt(dongiaIndex);

                                // Định dạng và thêm vào danh sách
                                String productData = tensp + " - " + mota + " - " + dongia + "đ";
                                mylist.add(productData);
                            }
                        } while (c.moveToNext());
                    }
                    c.close();  // Đóng con trỏ sau khi lấy dữ liệu xong
                }

                myadapter.notifyDataSetChanged();  // Cập nhật ListView để hiển thị dữ liệu mới
            }
        });

        return view;
    }

    // Xử lý hình ảnh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        }}