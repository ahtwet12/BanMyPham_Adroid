package com.example.cosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ImageView imgLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        imgLogout = view.findViewById(R.id.imgLogout);

        // Xử lý sự kiện khi nhấn vào nút Logout
        imgLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến MainActivity (Trang chủ)
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent); // Mở MainActivity

                // Kết thúc AdminActivity để không quay lại trang Admin khi nhấn nút back
                getActivity().finish();
            }
        });

        return view;
    }

}
