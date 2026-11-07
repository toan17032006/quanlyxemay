package ui;

import model.XeMay;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TableModel tùy chỉnh cho bảng hiển thị xe máy.
 */
public class XeMayTableModel extends AbstractTableModel {
    private final String[] columnNames = {
        "ID", "Biển số", "Hãng xe", "Tên xe", "Năm SX", "Màu sắc", "Giá bán (triệu)", "Trạng thái"
    };

    private List<XeMay> data;

    public XeMayTableModel() {
        data = new ArrayList<>();
    }

    public void setData(List<XeMay> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public XeMay getXeMayAt(int row) {
        if (row >= 0 && row < data.size()) {
            return data.get(row);
        }
        return null;
    }

    @Override
    public int getRowCount() { return data.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int column) { return columnNames[column]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 4: return Integer.class;
            case 6: return Double.class;
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        XeMay xe = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return xe.getId();
            case 1: return xe.getBienSo();
            case 2: return xe.getHangXe();
            case 3: return xe.getTenXe();
            case 4: return xe.getNamSanXuat();
            case 5: return xe.getMauSac();
            case 6: return xe.getGiaBan();
            case 7: return xe.getTrangThai();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
