package ui;

import dao.XeMayDAO;
import model.XeMay;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Giao diện chính của ứng dụng Quản lý Xe máy.
 */
public class MainFrame extends JFrame {
    private XeMayDAO dao;
    private XeMayTableModel tableModel;
    private JTable table;
    private JTextField txtTimKiem;
    private JLabel lblTongSo, lblConHang, lblDaBan, lblDangSua;

    public MainFrame() {
        dao = new XeMayDAO();
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("🏍️ Hệ thống Quản lý Xe máy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 550));
        setLocationRelativeTo(null);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(new Color(245, 245, 245));

        // ====== HEADER ======
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // ====== CENTER: Stats + Table ======
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Stats cards
        JPanel statsPanel = createStatsPanel();
        centerPanel.add(statsPanel, BorderLayout.NORTH);

        // Search + Table
        JPanel tableContainer = createTablePanel();
        centerPanel.add(tableContainer, BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // ====== FOOTER ======
        JPanel footerPanel = createFooterPanel();
        contentPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
    }

    // ======================== HEADER ========================
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setPreferredSize(new Dimension(0, 65));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("🏍️  HỆ THỐNG QUẢN LÝ XE MÁY");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);

        JLabel versionLabel = new JLabel("v1.0  ");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        versionLabel.setForeground(new Color(200, 220, 255));
        header.add(versionLabel, BorderLayout.EAST);

        return header;
    }

    // ======================== STATS CARDS ========================
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 12, 0));
        statsPanel.setOpaque(false);
        statsPanel.setPreferredSize(new Dimension(0, 75));

        lblTongSo = new JLabel("0");
        lblConHang = new JLabel("0");
        lblDaBan = new JLabel("0");
        lblDangSua = new JLabel("0");

        statsPanel.add(createStatCard("📊 Tổng số xe", lblTongSo, new Color(25, 118, 210)));
        statsPanel.add(createStatCard("✅ Còn hàng", lblConHang, new Color(46, 125, 50)));
        statsPanel.add(createStatCard("🛒 Đã bán", lblDaBan, new Color(230, 126, 34)));
        statsPanel.add(createStatCard("🔧 Đang sửa", lblDangSua, new Color(198, 40, 40)));

        return statsPanel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(Color.DARK_GRAY);
        card.add(lblTitle, BorderLayout.NORTH);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // ======================== TABLE PANEL ========================
    private JPanel createTablePanel() {
        JPanel container = new JPanel(new BorderLayout(0, 8));
        container.setOpaque(false);

        // --- Toolbar: Search + Buttons ---
        JPanel toolbar = new JPanel(new BorderLayout(10, 0));
        toolbar.setOpaque(false);
        toolbar.setPreferredSize(new Dimension(0, 42));

        // Search
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);

        JLabel lblSearch = new JLabel("🔍 Tìm kiếm: ");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchPanel.add(lblSearch, BorderLayout.WEST);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiem.setPreferredSize(new Dimension(250, 32));
        txtTimKiem.setToolTipText("Tìm theo biển số, hãng xe, tên xe, màu sắc, trạng thái...");
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiem();
            }
        });
        searchPanel.add(txtTimKiem, BorderLayout.CENTER);

        JButton btnXoaTimKiem = new JButton("✖");
        btnXoaTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnXoaTimKiem.setPreferredSize(new Dimension(40, 32));
        btnXoaTimKiem.setToolTipText("Xóa tìm kiếm");
        btnXoaTimKiem.addActionListener(e -> {
            txtTimKiem.setText("");
            loadData();
        });
        searchPanel.add(btnXoaTimKiem, BorderLayout.EAST);

        toolbar.add(searchPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        buttonPanel.setOpaque(false);

        JButton btnThem = createButton("➕ Thêm", new Color(46, 125, 50));
        btnThem.addActionListener(e -> themXe());
        buttonPanel.add(btnThem);

        JButton btnSua = createButton("✏️ Sửa", new Color(25, 118, 210));
        btnSua.addActionListener(e -> suaXe());
        buttonPanel.add(btnSua);

        JButton btnXoa = createButton("🗑️ Xóa", new Color(198, 40, 40));
        btnXoa.addActionListener(e -> xoaXe());
        buttonPanel.add(btnXoa);

        JButton btnLamMoi = createButton("🔄 Làm mới", new Color(121, 85, 72));
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadData();
        });
        buttonPanel.add(btnLamMoi);

        toolbar.add(buttonPanel, BorderLayout.EAST);
        container.add(toolbar, BorderLayout.NORTH);

        // --- Table ---
        tableModel = new XeMayTableModel();
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(187, 222, 251));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(25, 118, 210));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 36));
        header.setReorderingAllowed(false);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(110);  // Biển số
        table.getColumnModel().getColumn(2).setPreferredWidth(80);   // Hãng xe
        table.getColumnModel().getColumn(3).setPreferredWidth(120);  // Tên xe
        table.getColumnModel().getColumn(4).setPreferredWidth(60);   // Năm SX
        table.getColumnModel().getColumn(5).setPreferredWidth(100);  // Màu sắc
        table.getColumnModel().getColumn(6).setPreferredWidth(100);  // Giá bán
        table.getColumnModel().getColumn(7).setPreferredWidth(110);  // Trạng thái

        // Center alignment for some columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        // Price formatting
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            private DecimalFormat df = new DecimalFormat("#,##0.0");
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                if (value instanceof Double) {
                    value = df.format(value) + " tr";
                }
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.RIGHT);
                return c;
            }
        });

        // Status coloring
        table.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected && value != null) {
                    String status = value.toString();
                    switch (status) {
                        case "Còn hàng":
                            setBackground(new Color(200, 230, 201));
                            setForeground(new Color(27, 94, 32));
                            break;
                        case "Đã bán":
                            setBackground(new Color(255, 224, 178));
                            setForeground(new Color(230, 81, 0));
                            break;
                        case "Đang sửa chữa":
                            setBackground(new Color(255, 205, 210));
                            setForeground(new Color(183, 28, 28));
                            break;
                        default:
                            setBackground(Color.WHITE);
                            setForeground(Color.BLACK);
                    }
                } else {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                }
                return c;
            }
        });

        // Double-click to edit
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    suaXe();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(105, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    // ======================== FOOTER ========================
    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(50, 50, 50));
        footer.setPreferredSize(new Dimension(0, 30));
        footer.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JLabel footerText = new JLabel("Ứng dụng Quản lý Xe máy  |  Java Swing  |  © 2026");
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerText.setForeground(new Color(180, 180, 180));
        footer.add(footerText, BorderLayout.WEST);

        return footer;
    }

    // ======================== CRUD OPERATIONS ========================

    /** Load / refresh data vào bảng */
    private void loadData() {
        List<XeMay> list = dao.layTatCa();
        tableModel.setData(list);
        capNhatThongKe();
    }

    /** Tìm kiếm */
    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        List<XeMay> result = dao.timKiem(keyword);
        tableModel.setData(result);
    }

    /** Thêm xe mới */
    private void themXe() {
        XeMayDialog dialog = new XeMayDialog(this, "➕ Thêm xe máy mới", null);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            XeMay xe = dialog.getXeMay();
            dao.them(xe);
            loadData();
            JOptionPane.showMessageDialog(this,
                "Đã thêm xe: " + xe.getHangXe() + " " + xe.getTenXe(),
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Sửa xe đã chọn */
    private void suaXe() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một xe trong bảng để sửa!",
                "Chưa chọn xe", JOptionPane.WARNING_MESSAGE);
            return;
        }

        XeMay xe = tableModel.getXeMayAt(row);
        if (xe == null) return;

        // Create a copy for editing
        XeMay xeCopy = new XeMay(
            xe.getId(), xe.getBienSo(), xe.getHangXe(), xe.getTenXe(),
            xe.getNamSanXuat(), xe.getMauSac(), xe.getGiaBan(), xe.getTrangThai()
        );

        XeMayDialog dialog = new XeMayDialog(this, "✏️ Sửa thông tin xe", xeCopy);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            XeMay xeMoi = dialog.getXeMay();
            dao.capNhat(xeMoi);
            loadData();
            JOptionPane.showMessageDialog(this,
                "Đã cập nhật xe: " + xeMoi.getHangXe() + " " + xeMoi.getTenXe(),
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Xóa xe đã chọn */
    private void xoaXe() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một xe trong bảng để xóa!",
                "Chưa chọn xe", JOptionPane.WARNING_MESSAGE);
            return;
        }

        XeMay xe = tableModel.getXeMayAt(row);
        if (xe == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa xe:\n\n" +
            "   Biển số: " + xe.getBienSo() + "\n" +
            "   Xe: " + xe.getHangXe() + " " + xe.getTenXe() + "\n\n" +
            "Hành động này không thể hoàn tác!",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            dao.xoa(xe.getId());
            loadData();
            JOptionPane.showMessageDialog(this,
                "Đã xóa xe: " + xe.getHangXe() + " " + xe.getTenXe(),
                "Đã xóa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Cập nhật thẻ thống kê */
    private void capNhatThongKe() {
        lblTongSo.setText(String.valueOf(dao.demTongSo()));
        lblConHang.setText(String.valueOf(dao.demTheoTrangThai("Còn hàng")));
        lblDaBan.setText(String.valueOf(dao.demTheoTrangThai("Đã bán")));
        lblDangSua.setText(String.valueOf(dao.demTheoTrangThai("Đang sửa chữa")));
    }
}
