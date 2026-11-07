package dao;

import model.XeMay;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object - quản lý danh sách xe máy trong bộ nhớ.
 */
public class XeMayDAO {
    private List<XeMay> danhSachXe;
    private int nextId;

    public XeMayDAO() {
        danhSachXe = new ArrayList<>();
        nextId = 1;
        taoMauDuLieu();
    }

    /** Tạo dữ liệu mẫu ban đầu */
    private void taoMauDuLieu() {
        them(new XeMay(0, "59-F1 12345", "Honda", "Wave Alpha", 2023, "Đen", 18.5, "Còn hàng"));
        them(new XeMay(0, "59-F1 67890", "Yamaha", "Exciter 155", 2024, "Xanh", 50.5, "Còn hàng"));
        them(new XeMay(0, "59-B2 11111", "Honda", "Air Blade", 2023, "Đỏ", 42.0, "Đã bán"));
        them(new XeMay(0, "51-F3 22222", "Suzuki", "Raider 150", 2022, "Trắng", 49.0, "Còn hàng"));
        them(new XeMay(0, "59-D1 33333", "Honda", "SH 150i", 2024, "Bạc", 105.0, "Còn hàng"));
        them(new XeMay(0, "59-F1 44444", "Yamaha", "NVX 155", 2023, "Đen nhám", 55.0, "Đang sửa chữa"));
        them(new XeMay(0, "50-C1 55555", "Honda", "Vision", 2024, "Trắng ngà", 32.0, "Còn hàng"));
        them(new XeMay(0, "59-F2 66666", "Yamaha", "Sirius", 2022, "Xanh dương", 21.0, "Đã bán"));
        them(new XeMay(0, "59-B1 77777", "SYM", "Elegant", 2021, "Nâu", 16.5, "Còn hàng"));
        them(new XeMay(0, "51-D2 88888", "Piaggio", "Vespa Sprint", 2024, "Xanh rêu", 85.0, "Còn hàng"));
    }

    /** Lấy toàn bộ danh sách */
    public List<XeMay> layTatCa() {
        return new ArrayList<>(danhSachXe);
    }

    /** Thêm xe mới */
    public void them(XeMay xe) {
        xe.setId(nextId++);
        danhSachXe.add(xe);
    }

    /** Cập nhật xe theo ID */
    public boolean capNhat(XeMay xeMoi) {
        for (int i = 0; i < danhSachXe.size(); i++) {
            if (danhSachXe.get(i).getId() == xeMoi.getId()) {
                danhSachXe.set(i, xeMoi);
                return true;
            }
        }
        return false;
    }

    /** Xóa xe theo ID */
    public boolean xoa(int id) {
        return danhSachXe.removeIf(xe -> xe.getId() == id);
    }

    /** Tìm xe theo ID */
    public XeMay timTheoId(int id) {
        return danhSachXe.stream()
                .filter(xe -> xe.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** Tìm kiếm xe theo từ khóa (biển số, hãng, tên, màu) */
    public List<XeMay> timKiem(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return layTatCa();
        }
        String keyword = tuKhoa.toLowerCase().trim();
        return danhSachXe.stream()
                .filter(xe ->
                        xe.getBienSo().toLowerCase().contains(keyword) ||
                        xe.getHangXe().toLowerCase().contains(keyword) ||
                        xe.getTenXe().toLowerCase().contains(keyword) ||
                        xe.getMauSac().toLowerCase().contains(keyword) ||
                        xe.getTrangThai().toLowerCase().contains(keyword)
                )
                .collect(Collectors.toList());
    }

    /** Đếm tổng số xe */
    public int demTongSo() {
        return danhSachXe.size();
    }

    /** Đếm số xe theo trạng thái */
    public long demTheoTrangThai(String trangThai) {
        return danhSachXe.stream()
                .filter(xe -> xe.getTrangThai().equals(trangThai))
                .count();
    }
}
