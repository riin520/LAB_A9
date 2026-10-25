package vn.edu.vhu.ltdd.a9_211a290018;



import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

// Kế thừa BaseAdapter bắt buộc cài đặt 4 hàm: getCount, getItem, getItemId, getView
public class MonAnAdapter extends BaseAdapter {

    // Mẫu ViewHolder giữ tham chiếu View để khỏi findViewById nhiều lần gây giật lag[cite: 68, 69]
    private static class ViewHolder {
        ImageView imgAnh;
        TextView tvTen, tvMoTa, tvGia;
    }

    private final Context context;
    private final List<MonAn> danhSach;
    private final LayoutInflater inflater;
    private static final String TAG = "A9_211a290018";

    public MonAnAdapter(Context context, List<MonAn> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return danhSach.size(); }

    @Override
    public Object getItem(int position) { return danhSach.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Thổi layout mới, BẮT BUỘC tham số thứ 3 là false để không bị lỗi addView[cite: 68, 69]
            convertView = inflater.inflate(R.layout.item_mon_an, parent, false);
            holder = new ViewHolder();
            holder.imgAnh = convertView.findViewById(R.id.imgAnh);
            holder.tvTen = convertView.findViewById(R.id.tvTen);
            holder.tvMoTa = convertView.findViewById(R.id.tvMoTa);
            holder.tvGia = convertView.findViewById(R.id.tvGia);
            convertView.setTag(holder); // Cất holder vào ngăn kéo của View

            // Dòng Log bắt buộc để đếm số lần inflate nộp vô Báo cáo[cite: 69]
            Log.d(TAG, "inflate dòng mới");
        } else {
            // Lấy lại View cũ cuộn lên để tái sử dụng[cite: 69]
            holder = (ViewHolder) convertView.getTag();
        }

        // Gán dữ liệu bắt buộc để ngoài nhánh if để không bị lẫn lộn khi cuộn[cite: 69]
        MonAn mon = danhSach.get(position);
        holder.imgAnh.setImageResource(mon.getAnhResId());
        holder.tvTen.setText(mon.getTen());
        holder.tvMoTa.setText(mon.getMoTa());
        holder.tvGia.setText(MonAn.dinhDangGia(mon.getGia()));

        // BÀI NÂNG CAO 2: Đổi màu nền nhạt nếu giá > 45.000đ[cite: 69]
        if (mon.getGia() > 45000) {
            convertView.setBackgroundColor(Color.parseColor("#FFE0B2")); // Màu cam nhạt
        } else {
            // Phải có else gán màu mặc định để lỗi không bị dính màu sang dòng khác khi tái sử dụng[cite: 69]
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
}
