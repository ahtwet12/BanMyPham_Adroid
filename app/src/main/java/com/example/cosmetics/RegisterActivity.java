package com.example.cosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText editHoTen,editSDT, editEmail,editMatKhau;
    Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //anh xa
        editHoTen = findViewById(R.id.editHoTen);
        editSDT = findViewById(R.id.editSDT);
        editEmail = findViewById(R.id.editEmail);
        editMatKhau = findViewById(R.id.editMatKhau);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);


        //chuyển hướng trang login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent4);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editMatKhau.getText().toString();
                String username = editHoTen.getText().toString();
                String phoneNumber = editSDT.getText().toString();
                String email = editEmail.getText().toString();

                // Kiểm tra mật khẩu
                if (password.length() < 6) {
                    editMatKhau.setError("Mật khẩu phải nhiều hơn 6 ký tự");
                } else if (password.chars().noneMatch(Character::isUpperCase)) {
                    editMatKhau.setError("Mật khẩu phải có ít nhất 1 chữ in hoa");
                } else if (password.chars().noneMatch(Character::isLowerCase)) {
                    editMatKhau.setError("Mật khẩu phải có ít nhất 1 chữ in thường");
                } else if (password.chars().noneMatch(Character::isDigit)) {
                    editMatKhau.setError("Mật khẩu phải có ít nhất 1 ký tự số");
                }

                // Kiểm tra tài khoản
                else if (!isValidUsername(username)) {
                    editHoTen.setError("Tài khoản không hợp lệ: Đảm bảo độ dài từ 6 ký tự trở lên và không có khoảng trắng");
                }

                // Kiểm tra số điện thoại
                else if (!isValidPhoneNumber(phoneNumber)) {
                    editSDT.setError("Số điện thoại không hợp lệ");
                }

                // Kiểm tra email
                else if (!isValidGmail(editEmail)) {
                    editEmail.setError("Email không hợp lệ");
                }

                // Nếu không có lỗi, tiến hành đăng ký và chuyển hướng
                else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                }
            }
        });


        // Áp dụng WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Kiểm tra tài khoản
    public boolean isValidUsername(String username) {
        if (username.length() >= 6 && !username.contains(" ")) {
            return true;
        } else {
            return false;
        }
    }

    // Kiểm tra số điện thoại
    public boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^[0-9]{10,15}$"; // Điều chỉnh độ dài tùy theo yêu cầu
        return phoneNumber.matches(phonePattern);

    }
    //KIEM tra gmail
    public boolean isValidGmail(EditText emailField) {
        String email = emailField.getText().toString().trim();
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        boolean isMatch = Pattern.matches(emailPattern, email);

        if (!isMatch) {
            Toast.makeText(emailField.getContext(), "Email phải có đuôi @gmail.com", Toast.LENGTH_SHORT).show();
        }

        return isMatch;
    }
}
