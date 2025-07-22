package com.example.quizbin1.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.InformationDTO;
import com.example.quizbin1.data.model.dto.UpdateInformationRequestDTO;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhone;
    private Button btnSave;

    private ApiService apiService;
    private UUID userId;
    private UUID infoId;
    private Date dateOfBirth;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);

        apiService = ApiClient.getClient().create(ApiService.class);

        String userIdString = getIntent().getStringExtra("userId");
        if (userIdString == null) {
            Toast.makeText(this, "Không có userId", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Log.d("EditProfileActivity", "Received userId: " + userIdString);

        try {
            userId = UUID.fromString(userIdString);
        } catch (IllegalArgumentException ex) {
            Toast.makeText(this, "UserId không hợp lệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserInformation();
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            finish();
        });
        btnSave.setOnClickListener(v -> {
            String fullName = edtFullName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            if (fullName.isEmpty()) {
                edtFullName.setError("Họ tên không được để trống");
                edtFullName.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                edtEmail.setError("Email không được để trống");
                edtEmail.requestFocus();
                return;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Email không hợp lệ");
                edtEmail.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                edtPhone.setError("Số điện thoại không được để trống");
                edtPhone.requestFocus();
                return;
            } else if (!phone.matches("\\d{9,11}")) {
                edtPhone.setError("Số điện thoại không hợp lệ");
                edtPhone.requestFocus();
                return;
            }

            // Nếu validate thành công thì tạo request và gọi API
            UpdateInformationRequestDTO requestDTO = new UpdateInformationRequestDTO(
                    userId, fullName, email, phone, dateOfBirth, urlImage
            );

            if (infoId == null) {
                // Tạo mới thông tin
                apiService.createInformation(requestDTO).enqueue(new Callback<InformationDTO>() {
                    @Override
                    public void onResponse(Call<InformationDTO> call, Response<InformationDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "Tạo mới thông tin thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Tạo mới thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InformationDTO> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Cập nhật thông tin
                apiService.updateInformation(infoId, requestDTO).enqueue(new Callback<InformationDTO>() {
                    @Override
                    public void onResponse(Call<InformationDTO> call, Response<InformationDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InformationDTO> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadUserInformation() {
        Log.d("EditProfileActivity", "Loading information for userId: " + userId.toString());
        apiService.getInformationByUserId(userId).enqueue(new Callback<InformationDTO>() {
            @Override
            public void onResponse(Call<InformationDTO> call, Response<InformationDTO> response) {
                Log.d("EditProfileActivity", "API response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    InformationDTO info = response.body();
                    Log.d("EditProfileActivity", "Information loaded: " + info.getFullName());
                    infoId = info.getInfoId();
                    edtFullName.setText(info.getFullName());
                    edtEmail.setText(info.getEmail());
                    edtPhone.setText(info.getPhone());
                    dateOfBirth = info.getDateOfBirth(); // giữ ngày sinh
                    urlImage = info.getUrlImage();       // giữ url ảnh nếu có
                } else {
                    Log.d("EditProfileActivity", "API response unsuccessful or empty body");
                    Toast.makeText(EditProfileActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InformationDTO> call, Throwable t) {
                Log.e("EditProfileActivity", "API call failure: " + t.getMessage(), t);
                Toast.makeText(EditProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
