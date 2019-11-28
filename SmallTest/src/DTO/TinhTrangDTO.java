package DTO;

public class TinhTrangDTO implements Comparable<TinhTrangDTO> {
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

    @Override
    public int compareTo(TinhTrangDTO o) {
        return this.getMaTinhTrang().compareTo(o.getMaTinhTrang());
    }
}