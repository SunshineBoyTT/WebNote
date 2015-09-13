package com.roc.webnote.lib;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * Created by yp-tc-m-2795 on 15/9/9.
 */
public class QiNiuTools {
    private static String ACCESS_KEY = "qSyNG9128rk134ljUe0cTzNjVelUujUaB-yw22jo";
    private static String SECRET_KEY = "Wv7EDU2FZn6T1KFkVaYKIIY_6I1W2lgzQ7IwaiYV";
    static         Auth   auth       = Auth.create(ACCESS_KEY, SECRET_KEY);

    static UploadManager uploadManager = new UploadManager();


    private static String getUpToken() {
        return auth.uploadToken("cshijiel");
    }

    public static void upload(byte[] b, String key) {
        try {
            Response res = uploadManager.put(b, key, getUpToken());
            MyRet ret = res.jsonToObject(MyRet.class);
            System.out.println(res);
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时简单状态信息
        }
    }

}

class MyRet {
    public long   fsize;
    public String key;
    public String hash;
    public int    width;
    public int    height;
}
