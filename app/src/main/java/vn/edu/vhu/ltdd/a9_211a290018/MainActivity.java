package vn.edu.vhu.ltdd.a9_211a290018;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "A9_211a290018";
    private final List<MonAn> danhSach = new ArrayList<>();
    private MonAnAdapter adapter;
    private ListView lvMonAn;
    private TextView tvTrong, tvThongKe;
    private EditText edtTen, edtGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        lvMonAn = findViewById(R.id.lvMonAn);
        tvTrong = findViewById(R.id.tvTrong);
        tvThongKe = findViewById(R.id.tvThongKe); // Khai báo text NC4
        edtTen = findViewById(R.id.edtTen);
        edtGia = findViewById(R.id.edtGia);
        Button btnThem = findViewById(R.id.btnThem);

        taoDuLieuMau();

        // Bước then chốt: Nối dữ liệu vào ListView thông qua Adapter[cite: 69]
        adapter = new MonAnAdapter(this, danhSach);
        lvMonAn.setAdapter(adapter);

        // Cơ chế tự động hiện Text "Thực đơn trống" khi xóa hết danh sách[cite: 69]
        lvMonAn.setEmptyView(tvTrong);

        capNhatThongKe(); // Chạy hàm đếm tiền lần đầu (NC4)

        // Chạm vào 1 dòng -> Hiện tên món[cite: 69]
        lvMonAn.setOnItemClickListener((parent, view, position, id) -> {
            MonAn mon = (MonAn) parent.getItemAtPosition(position);
            Toast.makeText(this, getString(R.string.chon_mon, mon.getTen()), Toast.LENGTH_SHORT).show();
        });

        // Giữ lâu 1 dòng -> Xóa món[cite: 69]
        lvMonAn.setOnItemLongClickListener((parent, view, position, id) -> {
            MonAn mon = danhSach.remove(position);
            adapter.notifyDataSetChanged(); // Bắt buộc gọi để ListView load lại giao diện[cite: 69]
            capNhatThongKe(); // Cập nhật lại tổng tiền (NC4)[cite: 69]
            Toast.makeText(this, getString(R.string.da_xoa, mon.getTen()), Toast.LENGTH_SHORT).show();
            return true; // Bắt buộc return true để ngắt sự kiện, không trôi xuống click thường[cite: 69]
        });

        btnThem.setOnClickListener(v -> themMon());
    }

    private void taoDuLieuMau() {
        danhSach.add(new MonAn("Cơm tấm Sà Bì Chưởng", "Sườn ướp mật ong", 45000, R.drawable.mon1));
        danhSach.add(new MonAn("Bún Bòa quế!", "gốc huế hơn cả người huế", 50000, R.drawable.mon2));
        danhSach.add(new MonAn("Phở Bòa ", "Nước dùng ninh xương 8x8 mấy giờ", 40000, R.drawable.mon3));
        danhSach.add(new MonAn("Bún Thịt Nướng", "thịt nướng bằng cả tấm lòng", 25000, R.drawable.mon4));
        danhSach.add(new MonAn("Bánh Mì Chảo", "bánh mì Việt Nam, chảo Liên Xô", 30000, R.drawable.mon5));
        danhSach.add(new MonAn("Cơm tấm Sà Bì Chưởng", "Sườn ướp mật ong", 45000, R.drawable.mon1));
        danhSach.add(new MonAn("Bún Bòa quế!", "gốc huế hơn cả người huế", 50000, R.drawable.mon2));
        danhSach.add(new MonAn("Phở Bòa ", "Nước dùng ninh xương 8x8 mấy giờ", 40000, R.drawable.mon3));
        danhSach.add(new MonAn("Bún Thịt Nướng", "thịt nướng bằng cả tấm lòng", 25000, R.drawable.mon4));
        danhSach.add(new MonAn("Bánh Mì Chảo", "bánh mì Việt Nam, chảo Liên Xô", 30000, R.drawable.mon5));
        danhSach.add(new MonAn("Cơm tấm Sà Bì Chưởng", "Sườn ướp mật ong", 45000, R.drawable.mon1));
        danhSach.add(new MonAn("Bún Bòa quế!", "gốc huế hơn cả người huế", 50000, R.drawable.mon2));
        danhSach.add(new MonAn("Phở Bòa ", "Nước dùng ninh xương 8x8 mấy giờ", 40000, R.drawable.mon3));
        danhSach.add(new MonAn("Bún Thịt Nướng", "thịt nướng bằng cả tấm lòng", 25000, R.drawable.mon4));
        danhSach.add(new MonAn("Bánh Mì Chảo", "bánh mì Việt Nam, chảo Liên Xô", 30000, R.drawable.mon5));
    }

    private void themMon() {
        String ten = edtTen.getText().toString().trim();
        String giaStr = edtGia.getText().toString().trim();

        if (ten.isEmpty()) {
            edtTen.setError(getString(R.string.err_ten));
            edtTen.requestFocus();
            return;
        }
        int gia;
        try {
            gia = Integer.parseInt(giaStr);
        } catch (NumberFormatException e) {
            edtGia.setError(getString(R.string.err_gia)); // Chặn lỗi nhập chữ vào ô số[cite: 69]
            edtGia.requestFocus();
            return;
        }
        if (gia <= 0) {
            edtGia.setError(getString(R.string.err_gia_duong));
            edtGia.requestFocus();
            return;
        }

        danhSach.add(0, new MonAn(ten, getString(R.string.moi_them), gia, R.drawable.mon1));
        adapter.notifyDataSetChanged(); // Báo giao diện vẽ lại[cite: 69]
        capNhatThongKe(); // Tính lại tiền (NC4)[cite: 69]
        lvMonAn.smoothScrollToPosition(0); // Cuộn mượt lên dòng đầu tiên vừa thêm[cite: 69]

        edtTen.setText("");
        edtGia.setText("");
        edtTen.requestFocus();
    }

    // BÀI NÂNG CAO 4: Hàm tính tổng số món và tổng giá trị thực đơn[cite: 69]
    private void capNhatThongKe() {
        int tongTien = 0;
        for (MonAn mon : danhSach) {
            tongTien += mon.getGia();
        }
        tvThongKe.setText(getString(R.string.thong_ke, danhSach.size(), MonAn.dinhDangGia(tongTien)));
    }
}