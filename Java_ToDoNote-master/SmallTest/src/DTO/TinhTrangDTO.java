package DTO;

public class TinhTrangDTO {
    private Integer maTinhTrang;
    private String tenTinhTrang;

    public TinhTrangDTO(Integer maTinhTrang, String tenTinhTrang) {
        this.maTinhTrang = maTinhTrang;
        this.tenTinhTrang = tenTinhTrang;
    }

    public Integer getMaTinhTrang() {
        return maTinhTrang;
    }

    public String getTenTinhTrang() {
        return tenTinhTrang;
    }
}