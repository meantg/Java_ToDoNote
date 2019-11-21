package DTO;

public class UserDTO {
    private Integer maNguoiDung;
    private String tenNguoiDung;
    private String tenDangNhap;
    private String matKhau;
    private String gioiTinh;
    private String soDienThoai;
    private String Email;

    public UserDTO(Integer maNguoiDung, String tenNguoiDung, String tenDangNhap, String matKhau, String gioiTinh, String soDienThoai, String Email) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.Email = Email;
    }

    public UserDTO(String tenNguoiDung, String tenDangNhap, String matKhau, String gioiTinh, String soDienThoai, String Email) {
        this.tenNguoiDung = tenNguoiDung;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.Email = Email;
    }

    public Integer getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() { return matKhau; }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return Email;
    }
}
