package com.seetaoism.data.okhttp.convertor;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.mr.k.mvp.exceptions.ResultException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/*
 * created by taofu on 2019-10-12
 **/
public class JDResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private ParameterizedType type;

    JDResponseConverter(Gson gson, TypeAdapter<T> adapter, Type type) {
        this.gson = gson;
        this.adapter = adapter;
        if (type instanceof ParameterizedType) {
            this.type = (ParameterizedType) type;
        }

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        if (value != null) {
            MediaType mediaType = value.contentType();
            if (mediaType != null) {
                Charset charset = mediaType.charset();
                if (charset == null) {
                    charset = StandardCharsets.UTF_8;
                }
                if (mediaType.toString().toLowerCase().contains("json")) {

                    String contentStr = new String(value.bytes(), charset);
                    try {
                        JSONObject jsonObject = new JSONObject(contentStr);

                        if (!jsonObject.isNull("data")) {
                            String dataJsonStr = jsonObject.getString("data");

                            if (type != null) {
                                Type[] types = this.type.getActualTypeArguments();
                                boolean isDataTypeString = false;
                                if (types[0] == String.class) {
                                    isDataTypeString = true;
                                }
                                if (TextUtils.isEmpty(dataJsonStr) && !isDataTypeString) {
                                    jsonObject.remove("data");
                                }
                            }
                        }

                        if (!jsonObject.isNull("code")) {
                            int code = jsonObject.getInt("code");
                            if (code != 1) {
                                throw new ResultException(jsonObject.getString("message"));
                            }
                        }
                        value = ResponseBody.create(mediaType, jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }
}
