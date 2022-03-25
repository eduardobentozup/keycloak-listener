package br.com.edumatt3.keycloacklistener;

import org.json.JSONObject;

public class Util {
    public static String getIdFromPath(String path){
        try {
            return path.split("/")[1];
        }catch (Error e) {
            return null;
        }
    }

    public static String getRoleFromRepresentation(String representation){
        try {
            return new JSONObject(representation).getString("name");
        } catch (Error e){
            return null;
        }
    }
}
