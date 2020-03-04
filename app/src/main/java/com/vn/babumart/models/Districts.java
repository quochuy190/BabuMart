package com.vn.babumart.models;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Districts {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("MAQH")
    String MAQH;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("TYPE")
    String TYPE;
    @SerializedName("MATP")
    String MATP;

    public Districts(String MAQH, String NAME, String MATP) {
        this.MAQH = MAQH;
        this.NAME = NAME;
        this.MATP = MATP;
    }

    public Districts() {
    }

    private static Districts getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), Districts.class);
    }

    public static ArrayList<Districts> getList(String jsonArray) throws JSONException {
        ArrayList<Districts> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Districts>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public String getMATP() {
        return MATP;
    }

    public void setMATP(String MATP) {
        this.MATP = MATP;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }


    public String getMESSGE() {
        return MESSGE;
    }

    public void setMESSGE(String MESSGE) {
        this.MESSGE = MESSGE;
    }

    public String getsERROR() {
        return sERROR;
    }

    public void setsERROR(String sERROR) {
        this.sERROR = sERROR;
    }

    public String getsMESSAGE() {
        return sMESSAGE;
    }

    public void setsMESSAGE(String sMESSAGE) {
        this.sMESSAGE = sMESSAGE;
    }

    public String getsRESULT() {
        return sRESULT;
    }

    public void setsRESULT(String sRESULT) {
        this.sRESULT = sRESULT;
    }

    public String getMAQH() {
        return MAQH;
    }

    public void setMAQH(String MAQH) {
        this.MAQH = MAQH;
    }
}
