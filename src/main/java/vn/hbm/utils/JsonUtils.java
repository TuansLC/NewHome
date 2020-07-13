/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.hbm.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 *
 * @author nghiadc
 */
public class JsonUtils {

    final static Gson gson = new Gson();

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static JsonElement toJsonElement(Object obj) {
        return gson.toJsonTree(obj);
    }
}
