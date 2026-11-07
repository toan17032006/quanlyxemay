package model;

public class XeMay {
    private int id;
    private String bienSo;
    private String hangXe;
    private String tenXe;
    private int namSanXuat;
    private String mauSac;
    private double giaBan; 
    private String trangThai; 

    public XeMay() {
    }

    public XeMay(int id, String bienSo, String hangXe, String tenXe,
                 int namSanXuat, String mauSac, double giaBan, String trangThai) {
        this.id = id;
        this.bienSo = bienSo;
        this.hangXe = hangXe;
        this.tenXe = tenXe;
        this.namSanXuat = namSanXuat;
        this.mauSac = mauSac;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }

    public String getHangXe() { return hangXe; }
    public void setHangXe(String hangXe) { this.hangXe = hangXe; }

    public String getTenXe() { return tenXe; }
    public void setTenXe(String tenXe) { this.tenXe = tenXe; }

    public int getNamSanXuat() { return namSanXuat; }
    public void setNamSanXuat(int namSanXuat) { this.namSanXuat = namSanXuat; }

    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }

    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return id + " - " + bienSo + " - " + hangXe + " " + tenXe;
    }
}
