package com.envent.bottlesup.network;

import com.envent.bottlesup.utils.MetaData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by ronem on 4/3/18.
 */

public class MessageParser {

    public static String showMessageForErrorCode(Response<ResponseBody> response) {
        try {
            String _res = response.errorBody().string();
            JSONObject o = new JSONObject(_res);
            if (o.has(MetaData.MESSAGE.MSG)) {
                return o.getString(MetaData.MESSAGE.MSG);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MetaData.MESSAGE.SOME_THING_WENT_WRONG;
    }

    public static String showMessageForErrorCode(String _res) {
        try {
            JSONObject o = new JSONObject(_res);
            if (o.has(MetaData.MESSAGE.MSG)) {
                return o.getString(MetaData.MESSAGE.MSG);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MetaData.MESSAGE.SOME_THING_WENT_WRONG;
    }

    public static String showMessageForSuccessCode(JSONObject o) throws JSONException {
        if (o.has(MetaData.MESSAGE.MSG)) {
            return o.getString(MetaData.MESSAGE.MSG);
        }
        return MetaData.MESSAGE.SOME_THING_WENT_WRONG;
    }

    public static String showMessageForFailure(Throwable t) {
        if (t instanceof IOException) {
            t.printStackTrace();
            return MetaData.MESSAGE.NO_INTERNET;
        } else {
            return t.getLocalizedMessage();
        }
    }

}
