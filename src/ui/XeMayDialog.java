package ui;

import model.XeMay;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog để nhập/chỉnh sửa thông tin xe máy.
 */
public class XeMayDialog extends JDialog {
    private JTextField txtBienSo, txtHangXe, txtTenXe, txtNamSX, txtMauSac, txtGiaBan;
    private JComboBox<String> cboTrangThai;
    private boolean confirmed = false;
    private XeMay xeMay;

    public XeMayDialog(Frame parent, String title, XeMay xeMay) {
        super(parent, title, true);
        this.xeMay = xeMay;
        initComponents();
        if (xeMay != null) {
            fillData(xeMay);
        }
        pack();
        setMinimumSize(new Dimension(450, 380));
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Form panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Biển số
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblBienSo = new JLabel("Biển số: *");
        lblBienSo.setFont(labelFont);
        formPanel.add(lblBienSo, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtBienSo = new JTextField(20);
        txtBienSo.setFont(fieldFont);
        formPanel.add(txtBienSo, gbc);

        // Hãng xe
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblHangXe = new JLabel("Hãng xe: *");
        lblHangXe.setFont(labelFont);
        formPanel.add(lblHangXe, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtHangXe = new JTextField(20);
        txtHangXe.setFont(fieldFont);
        formPanel.add(txtHangXe, gbc);

        // Tên xe
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblTenXe = new JLabel("Tên xe: *");
        lblTenXe.setFont(labelFont);
        formPanel.add(lblTenXe, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTenXe = new JTextField(20);
        txtTenXe.setFont(fieldFont);
        formPanel.add(txtTenXe, gbc);

        // Năm SX
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblNamSX = new JLabel("Năm SX: *");
        lblNamSX.setFont(labelFont);
        formPanel.add(lblNamSX, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNamSX = new JTextField(20);
        txtNamSX.setFont(fieldFont);
        formPanel.add(txtNamSX, gbc);

        // Màu sắc
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        JLabel lblMauSac = new JLabel("Màu sắc: *");
        lblMauSac.setFont(labelFont);
        formPanel.add(lblMauSac, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtMauSac = new JTextField(20);
        txtMauSac.setFont(fieldFont);
        formPanel.add(txtMauSac, gbc);

        // Giá bán
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        JLabel lblGiaBan = new JLabel("Giá bán (triệu): *");
        lblGiaBan.setFont(labelFont);
        formPanel.add(lblGiaBan, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtGiaBan = new JTextField(20);
        txtGiaBan.setFont(fieldFont);
        formPanel.add(txtGiaBan, gbc);

        // Trạng thái
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        JLabel lblTrangThai = new JLabel("Trạng thái: *");
        lblTrangThai.setFont(labelFont);
        formPanel.add(lblTrangThai, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cboTrangThai = new JComboBox<>(new String[]{"Còn hàng", "Đã bán", "Đang sửa chữa"});
        cboTrangThai.setFont(fieldFont);
        formPanel.add(cboTrangThai, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- Button panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnLuu = new JButton("💾 Lưu");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLuu.setBackground(new Color(46, 125, 50));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);
        btnLuu.setPreferredSize(new Dimension(120, 35));
        btnLuu.addActionListener(e -> luuDuLieu());

        JButton btnHuy = new JButton("❌ Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHuy.setBackground(new Color(198, 40, 40));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFocusPainted(false);
        btnHuy.setPreferredSize(new Dimension(120, 35));
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void fillData(XeMay xe) {
        txtBienSo.setText(xe.getBienSo());
        txtHangXe.setText(xe.getHangXe());
        txtTenXe.setText(xe.getTenXe());
        txtNamSX.setText(String.valueOf(xe.getNamSanXuat()));
        txtMauSac.setText(xe.getMauSac());
        txtGiaBan.setText(String.valueOf(xe.getGiaBan()));
        cboTrangThai.setSelectedItem(xe.getTrangThai());
    }

    private void luuDuLieu() {
        // Validate
        String bienSo = txtBienSo.getText().trim();
        String hangXe = txtHangXe.getText().trim();
        String tenXe = txtTenXe.getText().trim();
        String namSXStr = txtNamSX.getText().trim();
        String mauSac = txtMauSac.getText().trim();
        String giaBanStr = txtGiaBan.getText().trim();

        if (bienSo.isEmpty() || hangXe.isEmpty() || tenXe.isEmpty() ||
            namSXStr.isEmpty() || mauSac.isEmpty() || giaBanStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng điền đầy đủ tất cả các trường!",
                "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int namSX;
        try {
            namSX = Integer.parseInt(namSXStr);
            if (namSX < 1900 || namSX > 2030) {
                JOptionPane.showMessageDialog(this,
                    "Năm sản xuất phải từ 1900 đến 2030!",
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Năm sản xuất phải là số nguyên!",
                "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double giaBan;
        try {
            giaBan = Double.parseDouble(giaBanStr);
            if (giaBan <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Giá bán phải lớn hơn 0!",
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Giá bán phải là số hợp lệ!",
                "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (xeMay == null) {
            xeMay = new XeMay();
        }
        xeMay.setBienSo(bienSo);
        xeMay.setHangXe(hangXe);
        xeMay.setTenXe(tenXe);
        xeMay.setNamSanXuat(namSX);
        xeMay.setMauSac(mauSac);
        xeMay.setGiaBan(giaBan);
        xeMay.setTrangThai((String) cboTrangThai.getSelectedItem());

        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() { return confirmed; }
    public XeMay getXeMay() { return xeMay; }
}
