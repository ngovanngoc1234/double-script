package model;

import java.io.Serializable;

public class DataID implements Serializable {
    String id;
    String nameClassBox;
    String dataStatus;
    String workName;
    String landmark;


    public DataID() {
    }

    public DataID(String id,String nameClassBox, String dataStatus,String workName,String landmark) {
        this.id = id;
        this.nameClassBox = nameClassBox;
        this.dataStatus = dataStatus;
        this.workName = workName;
        this.landmark = landmark;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getNameClassBox() {
        return nameClassBox;
    }

    public void setNameClassBox(String nameClassBox) {
        this.nameClassBox = nameClassBox;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
