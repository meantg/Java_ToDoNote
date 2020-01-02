package DTO;

public class TinhTrangDTO implements Comparable<TinhTrangDTO> {
    private Integer stateID;
    private String stateName;

    public TinhTrangDTO(Integer stateID, String stateName) {
        this.stateID = stateID;
        this.stateName = stateName;
    }

    public Integer getStateID() {
        return stateID;
    }

    public String getStateName() {
        return stateName;
    }

    @Override
    public int compareTo(TinhTrangDTO o) {
        return this.getStateID().compareTo(o.getStateID());
    }
}