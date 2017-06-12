package com.mark.ss;

/**
 * Created by Administrator on 2017/1/7.
 */

public class History {
    private String number;
    private String typeName;
    private String typeCode;

    public History(){}

    public History(String number, String typeName, String typeCode) {
        this.number = number;
        this.typeName = typeName;
        this.typeCode = typeCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
