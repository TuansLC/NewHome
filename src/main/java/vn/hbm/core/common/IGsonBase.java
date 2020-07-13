package vn.hbm.core.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface IGsonBase {
    Gson GSON = new GsonBuilder().serializeNulls().create();
    ObjectMapper objectMapper = new ObjectMapper();
}
