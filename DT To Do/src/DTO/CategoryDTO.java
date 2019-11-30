package DTO;

public class CategoryDTO {
    private Integer maPhanLoai;
    private Integer maNguoiDung;
    private String tenPhanLoai;
    private String icon;

    public CategoryDTO(Integer maPhanLoai, Integer maNguoiDung, String tenPhanLoai, String icon) {
        this.maPhanLoai = maPhanLoai;
        this.maNguoiDung = maNguoiDung;
        this.tenPhanLoai = tenPhanLoai;
        this.icon = icon;
    }

    public CategoryDTO(Integer maNguoiDung, String tenPhanLoai, String icon) {
        this.maNguoiDung = maNguoiDung;
        this.tenPhanLoai = tenPhanLoai;
        this.icon = icon;

    }
    public Integer getMaPhanLoai() {
        return maPhanLoai;
    }

    public Integer getMaNguoiDung() { return maNguoiDung; }

    public String getTenPhanLoai() {
        return tenPhanLoai;
    }

    public String getIcon() {
        return icon;
    }

    public void setTenPhanLoai(String tenPhanLoai) { this.tenPhanLoai = tenPhanLoai; }

    public void setIcon(String icon) { this.icon = icon; }
}
