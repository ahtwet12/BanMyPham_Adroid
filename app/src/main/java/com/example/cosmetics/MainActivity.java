package com.example.cosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtDanhMucSP, txtSanPhamMoi, txtTheFaceBT;
    ImageView LoginForm , Cart;

    private static final int MENU_ITEM_1 = 1;
    private static final int MENU_ITEM_2 = 2;
    private static final int MENU_ITEM_3 = 3;
    private static final int MENU_ITEM_4 = 4;

    private static final int MENU_NEW_1 = 1;
    private static final int MENU_NEW_2 = 2;
    private static final int MENU_NEW_3 = 3;
    private static final int MENU_NEW_4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    //anh xa bien
        txtDanhMucSP = (TextView) findViewById(R.id.txtDanhMucSP);
        txtDanhMucSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
        txtSanPhamMoi = (TextView) findViewById(R.id.txtSanPhamMoi);

        txtTheFaceBT = (TextView) findViewById(R.id.txtTheFaceBT);
        txtTheFaceBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTheFaceMenu(view);
            }
        });
        LoginForm = findViewById(R.id.LoginForm);
        LoginForm.setClickable(true);  // Đảm bảo LoginForm có thể nhấn


        Cart = findViewById(R.id.Cart);
        Cart.setClickable(true);  // Đảm bảo LoginForm có thể nhấn

        //thao tasc sang form đang nhap
        LoginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //thao tasc sang form cart
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //hiển thị danh mục sản phẩm
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);

        // Thêm các mục vào menu con trực tiếp
        popup.getMenu().add(0, MENU_ITEM_1, 0, "Chăm Sóc Da Mặt");
        popup.getMenu().add(0, MENU_ITEM_2, 1, "Chăm Sóc Cơ Thể");
        popup.getMenu().add(0, MENU_ITEM_3, 2, "Trang Điểm");
        popup.getMenu().add(0, MENU_ITEM_4, 3, "Phụ Kiện Làm Đẹp");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case MENU_ITEM_1:
                        // Xử lý khi nhấn vào Mục 1
                        // Ví dụ: hiển thị thông báo

                        return true;
                    case MENU_ITEM_2:
                        // Xử lý khi nhấn vào Mục 2

                        return true;
                    case MENU_ITEM_3:
                        // Xử lý khi nhấn vào Mục 3

                    case MENU_ITEM_4:
                        // Xử lý khi nhấn vào Mục 4

                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }
    //hiển thị the face beauty
    private void showTheFaceMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);

        // Thêm các mục vào menu con trực tiếp
        popup.getMenu().add(0, MENU_NEW_1, 0, "Chăm Sóc Da Mặt");
        popup.getMenu().add(0, MENU_NEW_2, 1, "Chăm Sóc Cơ Thể");
        popup.getMenu().add(0, MENU_NEW_3, 2, "Trang Điểm");
        popup.getMenu().add(0, MENU_NEW_4, 3, "Phụ Kiện Làm Đẹp");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case MENU_NEW_1:
                        // Xử lý khi nhấn vào Mục 1
                        showProductList("Chăm Sóc Da Mặt", new String[]{"Nước tẩy trang", "Sữa rửa mặt", "Toner","Xịt khoáng","Mặt nạ","Chống Nắng"});

                        return true;
                    case MENU_NEW_2:
                        // Xử lý khi nhấn vào Mục 2
                        showProductList("Chăm Sóc Cơ Thể", new String[]{"Tẩy tế bào chết", "Sữa tắm", "Dầu gội - Dầu xả", "Dưỡng mi","Nước hoa","Lotion body"});
                        return true;
                    case MENU_NEW_3:
                        // Xử lý khi nhấn vào Mục 3
                        showProductList("Trang Điểm", new String[]{"Son", "Phấn má", "Phấn mắt", "Kem lót", "Kem nền","Che khuyết điểm","Kẻ mày", "Masscara", "Phấn phủ"});
                    case MENU_NEW_4:
                        // Xử lý khi nhấn vào Mục 4
                        showProductList("Phụ Kiện Làm Đẹp", new String[]{"Cọ trang điểm", "Bông tẩy trang", "Gương", "Kẹp tóc" , "Chun buộc tóc", "Bông mút"});
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }
    // Hiển thị danh sách sản phẩm dưới dạng AlertDialog
    private void showProductList(String title, String[] products) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        // Thêm danh sách sản phẩm vào dialog
        builder.setItems(products, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng chọn một sản phẩm từ danh sách
                String selectedProduct = products[which];
                Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + selectedProduct, Toast.LENGTH_SHORT).show();
            }
        });

        // Hiển thị dialog
        builder.show();
    }


}