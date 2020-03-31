package com.zhu.provider;

import com.alibaba.fastjson.JSON;
import com.zhu.DTO.AccessToKenDTO;
import com.zhu.pojo.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


@Component
public class GithubProvider {
    //  OkHttp 的 POST请求
    public String getAccessToKen(AccessToKenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        // JSON.toJSONString(accessTokenDTO) 将accessTokenDTO 转换为JSON格式
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            // 将token分解
            String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
            return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    //  OkHttp 的 Get请求
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();

        try (Response response = client.newCall(request).execute()){

            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
