package DTO;

import javax.swing.*;

public class CategoryDTO {
    private Integer maPhanLoai;
    private String tenPhanLoai;
    private String icon;

    public CategoryDTO(Integer maPhanLoai, String tenPhanLoai, String icon) {
        this.maPhanLoai = maPhanLoai;
        this.tenPhanLoai = tenPhanLoai;
        this.icon = icon;
    }

    public Integer getMaPhanLoai() {
        return maPhanLoai;
    }

    public String getTenPhanLoai() {
        return tenPhanLoai;
    }

    public String getIcon() {
        return icon;
    }
}
