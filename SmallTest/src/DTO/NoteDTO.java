package DTO;

import java.time.LocalDate;

public class NoteDTO {
    private Integer maNote;
    private Integer maPhanLoai;
    private Integer maNguoiDung;
    private String tieuDe;
    private String noiDung;
    private Integer maTinhTrang;
    private LocalDate ngayTao;
    private LocalDate hanChot;

    public NoteDTO(Integer maNote, Integer maPhanLoai, Integer maNguoiDung,
                   String tieuDe, String noiDung, Integer maTinhTrang,
                   LocalDate ngayTao)
    {
        this.maNote = maNote;
        this.maPhanLoai = maPhanLoai;
        this.maNguoiDung = maNguoiDung;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.maTinhTrang = maTinhTrang;
        this.ngayTao = ngayTao;
        //this.hanChot = hanChot;
    }

    public NoteDTO(Integer maPhanLoai, Integer maNguoiDung, String tieuDe, String noiDung) {
        this.maPhanLoai = maPhanLoai;
        this.maNguoiDung = maNguoiDung;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
    }

    public Integer getMaNote() {
        return maNote;
    }

    public Integer getMaNguoiDung() {
        return maNguoiDung;
    }

    public Integer getMaPhanLoai() {
        return maPhanLoai;
    }

    public String getTieuDe() {
        return tieuDe;
    }
    public String getNoiDung() {
        return noiDung;
    }
    public Integer getMaTinhTrang() {
        return maTinhTrang;
    }
    public LocalDate getHanChot() {
        return hanChot;
    }

    //Set Methods

    public void setMaPhanLoai(Integer maPhanLoai) {
        this.maPhanLoai = maPhanLoai;
    }

    public void setMaTinhTrang(Integer maTinhTrang) {
        this.maTinhTrang = maTinhTrang;
    }

    public void setMaNguoiDung(Integer maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setHanChot(LocalDate hanChot) {
        this.hanChot = hanChot;
    }
}
