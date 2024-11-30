package com.example.cosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    ImageView Quaylai;
    TextView productName, productDescription, productPrice, quantityText;
    Button decreaseQuantity, increaseQuantity, paymentButton;

    int quantity = 1; // Biến lưu số lượng sản phẩm
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Anh xa
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        quantityText = findViewById(R.id.quantityText);
        decreaseQuantity = findViewById(R.id.decreaseQuantity);
        increaseQuantity = findViewById(R.id.increaseQuantity);
        paymentButton = findViewById(R.id.paymentButton);
        Quaylai = findViewById(R.id.Quaylai);

        // Sự kiện quay lại trang trước
        Quaylai.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Nhận dữ liệu sản phẩm từ Intent
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        // Hiển thị thông tin sản phẩm trong giao diện giỏ hàng
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format("%dđ", product.getPrice()));
            quantityText.setText("Số lượng: " + quantity);
        }

        // Sự kiện giảm số lượng
        decreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;  // Giảm số lượng
                updateQuantity();
            }
        });

        // Sự kiện tăng số lượng
        increaseQuantity.setOnClickListener(v -> {
            quantity++;  // Tăng số lượng
            updateQuantity();
        });

        // Xử lý sự kiện thanh toán
        paymentButton.setOnClickListener(v -> {
            if (product != null) {
                // Tính tổng giá thanh toán
                int totalPrice = product.getPrice() * quantity;

                // Hiển thị thông báo thanh toán thành công
                Toast.makeText(CartActivity.this, "Bạn đã thanh toán thành công: " + totalPrice + "đ", Toast.LENGTH_LONG).show();

                // Quay lại trang chính
                Intent intentBackToMain = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intentBackToMain);
                finish();  // Đảm bảo đóng activity hiện tại
            }
        });
    }

    // Cập nhật số lượng và giá tiền
    private void updateQuantity() {
        quantityText.setText("Số lượng: " + quantity);  // Cập nhật số lượng trên giao diện
    }
}
