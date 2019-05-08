package com.white.ghost.programmersupermario.network.api;

import com.white.ghost.programmersupermario.base.BaseResponse;
import com.white.ghost.programmersupermario.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Function: 主页等相关接口
 * Author Name: Chris
 * Date: 2019/5/6 15:04
 */
public interface MainService {
    //登录接口
    @FormUrlEncoded
    @POST("/api/?method=user.login")
    Observable<LoginBean> login(@FieldMap Map<String, String> map);

    //获取版本号
    @FormUrlEncoded
    @POST("/api/?method=version")
    Observable<ResponseBody> getVersion(@Field("VERSION") String version);

    //登录时获取学校列表
    @FormUrlEncoded
    @POST("/api/?method=user.getMoreSchool")
    Observable<ResponseBody> getSchoolList(@Field("search_value") String searchText,
                                           @Field("pagesize") int pageSize,
                                           @Field("next_id") int nextPage);

    //登录时切换学校
    @FormUrlEncoded
    @POST("/api/?method=user.changeSchool")
    Observable<ResponseBody> switchSchool(@Field("school_id") String schoolId);

    //重新登录
    @FormUrlEncoded
    @POST("/api/?method=user.relogin")
    Observable<ResponseBody> reLogin(@Field("token") String token, @Field("v") String version);

    //获取主页模块
    @POST("/api/?method=Schooltimetable.teacherAuth")
    Observable<ResponseBody> getModule();

    //检查模块权限
    @FormUrlEncoded
    @POST("/api/?method=user.checkAdmin")
    Observable<ResponseBody> checkAuthority(@Field("admin_type") int type);

    @POST("api/?method=electiveclasses.upload_elective")
    @Multipart
    Observable<ResponseBody> uploadFile(@Part("filename\"; filename=\"avatar.png\"") RequestBody file);

    // 上传图片
    @POST("api/?method=meeting.uploadPicture")
    @Multipart
    Observable<BaseResponse> uploadImage(@Query("token") String token, @Query("v") String version, @Query("index") int index, @Part("filename\"; filename=\"image.png\"") RequestBody file);


    // 上传语音：会议
    @POST("api/?method=meeting.uploadAudio")
    @Multipart
    Observable<BaseResponse> uploadAudio(@Query("token") String token, @Query("v") String version, @Part("filename\"; filename=\"audio.amr\"") RequestBody file);

    // 获取socket的ip列表
    @GET("/api/?method=ip.servers")
    Observable<BaseResponse> getIpList(@Query("v") String version);
}
