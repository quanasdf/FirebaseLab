package com.example.firebaselab.ass;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "FetchImageTask";
    private static final String SERVER_API_URL = "YOUR_SERVER_API_URL"; // Thay thế bằng URL của API trên server của bạn

    @Override
    protected String doInBackground(String... params) {
        String imageUrl = params[0];
        String base64Image = Image.convertImageToBase64(imageUrl); // Chuyển hình ảnh sang chuỗi base64
        String response = postImageToServer(base64Image); // Gửi hình ảnh đến máy chủ và nhận phản hồi
        return response; // Trả về kết quả cho phương thức onPostExecute
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "Server Response: " + result);
        //hiển thị thông báo hoặc cập nhật giao diện người dùng
    }

    private String postImageToServer(String base64Image) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/octet-stream"); // Loại phương tiện là dữ liệu nhị phân
            RequestBody body = RequestBody.create(mediaType, base64Image); // Tạo yêu cầu với dữ liệu hình ảnh
            Request request = new Request.Builder()
                    .url(SERVER_API_URL) // Đặt URL máy chủ
                    .post(body)
                    .build();

            // Thực hiện yêu cầu và nhận phản hồi từ máy chủ
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string(); // Trả về dữ liệu phản hồi từ máy chủ
            } else {
                return "Error: " + response.code(); // Trả về mã lỗi nếu phản hồi không thành công
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            return "Error: " + e.getMessage(); // Trả về thông báo lỗi nếu có lỗi xảy ra
        }
    }
}
