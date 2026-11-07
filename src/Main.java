import ui.MainFrame;
import javax.swing.*;

/**
 * Điểm khởi chạy ứng dụng Quản lý Xe máy.
 */
public class Main {
    public static void main(String[] args) {
        // Thiết lập Look and Feel hệ thống cho giao diện đẹp hơn
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Dùng Look and Feel mặc định nếu lỗi
        }

        // Chạy GUI trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
