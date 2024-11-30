package com.example.cosmetics;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtDanhMucSP, txtSanPhamMoi, txtTheFaceBT;
    ImageView LoginForm, Cart;
    ListView lv_show;
    private ArrayList<String> productList;
    private ArrayAdapter<String> productAdapter;

    SQLiteDatabase mydatabase; // Cơ sở dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ biến
        lv_show = findViewById(R.id.lv_show);
        txtDanhMucSP = findViewById(R.id.txtDanhMucSP);
        txtDanhMucSP.setOnClickListener(view -> showPopupMenu(view));

        txtSanPhamMoi = findViewById(R.id.txtSanPhamMoi);
        txtTheFaceBT = findViewById(R.id.txtTheFaceBT);
        txtTheFaceBT.setOnClickListener(view -> showTheFaceMenu(view));

        LoginForm = findViewById(R.id.LoginForm);
        Cart = findViewById(R.id.Cart);

        // Sự kiện chuyển sang form đăng nhập
        LoginForm.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Sự kiện chuyển sang form giỏ hàng
        Cart.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Khởi tạo danh sách và adapter cho ListView
        productList = new ArrayList<>();
        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        lv_show.setAdapter(productAdapter);

        // Mở hoặc tạo cơ sở dữ liệu
        mydatabase = openOrCreateDatabase("mypham.db", MODE_PRIVATE, null);

        // Tạo bảng nếu chưa tồn tại
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS tblproduct (" +
                "masp TEXT PRIMARY KEY, " +
                "tensp TEXT, " +
                "mota TEXT, " +
                "dongia INTEGER, " +
                "soluong INTEGER)");

        // Hiển thị dữ liệu từ cơ sở dữ liệu
        loadProductData();

        // Bắt sự kiện click vào item trong ListView
        lv_show.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy thông tin sản phẩm từ danh sách
            String productInfo = productList.get(position);
            String[] productDetails = productInfo.split(" - ");
            String name = productDetails[0]; // Tên sản phẩm
            String description = productDetails[1]; // Mô tả sản phẩm
            int price = Integer.parseInt(productDetails[2].replace("đ", "").trim()); // Giá sản phẩm

            // Tạo đối tượng Product
            Product selectedProduct = new Product(name, description, price);

            // Chuyển dữ liệu sản phẩm qua Intent sang CartActivity
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.putExtra("product", selectedProduct); // Truyền sản phẩm qua Intent
            startActivity(intent);

            // Hiển thị thông báo sản phẩm đã được thêm vào giỏ hàng
            Toast.makeText(MainActivity.this, "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });
    }

    // Hàm hiển thị danh mục sản phẩm
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add(0, 1, 0, "Chăm Sóc Da Mặt");
        popup.getMenu().add(0, 2, 1, "Chăm Sóc Cơ Thể");
        popup.getMenu().add(0, 3, 2, "Trang Điểm");
        popup.getMenu().add(0, 4, 3, "Phụ Kiện Làm Đẹp");

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1: // Chăm Sóc Da Mặt
                case 2: // Chăm Sóc Cơ Thể
                case 3: // Trang Điểm
                case 4: // Phụ Kiện Làm Đẹp
                    Toast.makeText(MainActivity.this, "Chức năng đang được phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        });

        popup.show();
    }

    // Hàm hiển thị menu sản phẩm The Face
    private void showTheFaceMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add(0, 1, 0, "Chăm Sóc Da Mặt");
        popup.getMenu().add(0, 2, 1, "Chăm Sóc Cơ Thể");
        popup.getMenu().add(0, 3, 2, "Trang Điểm");
        popup.getMenu().add(0, 4, 3, "Phụ Kiện Làm Đẹp");

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1:
                case 2:
                case 3:
                case 4:
                    Toast.makeText(MainActivity.this, "Chức năng đang được phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        });

        popup.show();
    }

    // Hàm load dữ liệu từ cơ sở dữ liệu
    private void loadProductData() {
        productList.clear(); // Xóa danh sách cũ

        Cursor cursor = mydatabase.rawQuery("SELECT * FROM tblproduct", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tensp = cursor.getString(1); // Tên sản phẩm
                String mota = cursor.getString(2); // Mô tả sản phẩm
                int dongia = cursor.getInt(3); // Giá sản phẩm

                // Định dạng dữ liệu hiển thị
                String product = tensp + " - " + mota + " - " + dongia + "đ";
                productList.add(product);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Không có sản phẩm nào trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) cursor.close();
        productAdapter.notifyDataSetChanged(); // Cập nhật ListView
    }
}
