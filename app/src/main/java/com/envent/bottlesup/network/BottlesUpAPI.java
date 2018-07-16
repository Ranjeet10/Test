package com.envent.bottlesup.network;

import com.envent.bottlesup.mvp.model.server_response.CheckInCheckOut;
import com.envent.bottlesup.mvp.model.server_response.PendingBalanceResponse;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.alias_response.AliasResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history.ConsumptionHistoryResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.CancelOrderResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.ConsumptionHistoryDetailResponse;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.model.server_response.notification_response.NotificationBody;
import com.envent.bottlesup.mvp.model.server_response.order_request_response.OrderRequestResponse;
import com.envent.bottlesup.mvp.model.server_response.order_status_response.OrderStatusResponse;
import com.envent.bottlesup.mvp.model.server_response.profile_image.ProfileImageResponse;
import com.envent.bottlesup.mvp.model.server_response.saving_response.SavingResponse;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlaceResponse;
import com.envent.bottlesup.mvp.model.server_response.topup_status_response.TopupStatusResponse;
import com.envent.bottlesup.mvp.model.server_response.user_profile.request.ProfileEditRequestModel;
import com.envent.bottlesup.mvp.model.server_response.user_profile.response.ProfileEditResponseModelHead;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueResponse;
import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ronem on 3/22/18.
 */

public interface BottlesUpAPI {

    @FormUrlEncoded
    @POST("/api/v1/register")
    Call<ResponseBody> getRegisterResponse(
            @Field("bidhee_api_customer_registration[name]") String name,
            @Field("bidhee_api_customer_registration[email]") String email,
            @Field("bidhee_api_customer_registration[mobile]") String mobile,
            @Field("bidhee_api_customer_registration[dob]") String dob,
            @Field("bidhee_api_customer_registration[gender]") int gender,
            @Field("bidhee_api_customer_registration[confirmTerms]") boolean bool,
            @Field("bidhee_api_customer_registration[plainPassword]") String password,
            @Field("bidhee_api_customer_registration[referralCode]") String referralCode
    );

    @FormUrlEncoded
    @POST("/api/v1/verify-mobile")
    Call<ResponseBody> getVerifyResponse(
            @Header("apiKey") String token,
            @Field("username") String userName,
            @Field("verificationCode") String verificationCode
    );

    @GET("/api/v1/user")
    Call<ResponseBody> getUserResponse(
            @Header("apiKey") String token
    );

    @POST("/api/v1/login")
    Call<ResponseBody> getLoginResponse(
            @Body HashMap<String, String> params
    );

    @GET("/api/v1/venue/list")
    Call<VenueResponse> getVenueListResponse(
            @Query("name") String tag,
            @Query("page") String pageCount,
            @Query("limit") String limit
    );

    @FormUrlEncoded
    @POST("/api/v1/resend-otp")
    Call<ResponseBody> getResendOTPResponse(
            @Field("username") String userName,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/user/balance")
    Call<ResponseBody> getBalanceResponse(
            @Header("apiKey") String apiKey
    );

    @FormUrlEncoded
    @POST("/api/v1/request/topup")
    Call<ResponseBody> getTopupResponse(
            @Field("venueId") int venueId,
            @Field("amount") double amount,
            @Field("seatingPlace") int seatingPlace,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/user/pending-balance")
    Call<PendingBalanceResponse> getPendingBalance(@Header("apiKey") String apiKey);

    @FormUrlEncoded
    @POST("/api/v1/payment/khalti")
    Call<ResponseBody> getTopupWithKhaltiResponse(
            @Field("token") String token,
            @Field("amount") int amount,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/venue/{venueId}/food/categories")
    Call<ResponseBody> getFoodCategoryResponse(
            @Path("venueId") String venueId,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/venue/{venueId}/drinks/categories")
    Call<ResponseBody> getDrinkCategoryResponse(
            @Path("venueId") String venueId,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/venue/{venueId}/food/category/{categoryId}")
    Call<ResponseBody> getFoodItemResponse(
            @Path("venueId") String venueId,
            @Path("categoryId") String categoryId,
            @Query("limit") int limit,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/venue/{venueId}/drinks/category/{categoryId}")
    Call<ResponseBody> getDrinkItemResponse(
            @Path("venueId") String venueId,
            @Path("categoryId") String categoryId,
            @Query("limit") int limit,
            @Header("apiKey") String apiKey
    );

    @FormUrlEncoded
    @POST("/api/v1/save-device-information")
    Call<ResponseBody> getDeviceRegistrationResponse(
            @Field("deviceId") String deviceId,
            @Field("deviceType") String deviceType,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/user/topup-history")
    Call<ResponseBody> getTopupHistoryResponse(
            @Query("page") int page,
            @Query("limit") int limit,
            @Header("apiKey") String apiKey
    );

    @FormUrlEncoded
    @POST("/api/v1/change-password")
    Call<StatusMessage> getChangePasswordResponse(
            @FieldMap HashMap<String, String> body,
            @Header("apiKey") String apiKey
    );


    @GET("/api/v1/venue/{venueId}/seating-places")
    Call<SeatingPlaceResponse> getSeatingPlaces(
            @Path("venueId") String venueId,
            @Header("apiKey") String apiKey
    );

    @Headers("Content-Type: application/json")
    @POST("/api/v1/order")
    Call<OrderRequestResponse> getOrderResponse(
            @Body JsonObject json,
            @Header("apiKey") String apiKey
    );

    @FormUrlEncoded
    @POST("/api/v1/forgot-password/request")
    Call<StatusMessage> getForgotPasswordResetTokenResponse(
            @Field("username") String userName
    );

    @FormUrlEncoded
    @POST("/api/v1/reset-password")
    Call<StatusMessage> getForgotPasswordResetPasswordResponse(
            @Field("token") int token,
            @Field("username") String userName,
            @Field("password") String password
    );

    @GET("/api/v1/events")
    Call<EventResponse> getEvents(
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/venue/{venueId}/events")
    Call<EventResponse> getEventByVenue(
            @Path("venueId") int venueId,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/content/{alias}")
    Call<AliasResponse> getAlias(@Path("alias") String alias
//            , @Header("apiKey") String apiKey
    );


    @POST("/api/v1/user/activate/{referralCode}")
    Call<ResponseBody> activateWithReferralCode(@Path("referralCode") String referralCode, @Header("apiKey") String apiKey);

    @GET("/api/v1/user/consumption-history")
    Call<ConsumptionHistoryResponse> getConsumptionHistory(@Header("apiKey") String apiKey);

    @GET("/api/v1/user/order/{orderId}")
    Call<ConsumptionHistoryDetailResponse> getOrderDetail(
            @Path("orderId") int orderId,
            @Header("apiKey") String apiKey
    );

    @POST("/api/v1/user/cancel-order/{orderId}")
    Call<CancelOrderResponse> deletePendingOrder(
            @Path("orderId") int orderId,
            @Header("apiKey") String apiKey
    );

    @GET("/api/v1/user/saving")
    Call<SavingResponse> getSaving(@Header("apiKey") String apiKey);


    @POST("/api/v1/user/edit")
    Call<ProfileEditResponseModelHead> editProfile(@Header("apiKey") String apiKey,
                                                   @Body ProfileEditRequestModel requestModel);


    @Multipart
    @POST("/api/v1/user/profile-image")
    Call<ProfileImageResponse> editProfileImage(@Header("apiKey") String apiKey,
                                                @Part MultipartBody.Part file
    );


    @GET("/api/v1/user/notifications")
    Call<NotificationBody> getNotificationList(
            @Header("apiKey") String apiKey,
            @Query("limit") int limit);

    @POST("/api/v1/user/check-in/{venueId}")
    Call<CheckInCheckOut> checkIn(
            @Header("apiKey") String apiKey,
            @Path("venueId") int venueId
    );

    @POST("/api/v1/user/check-out/{checkInCheckOutId}")
    Call<StatusMessage> checkOut(
            @Header("apiKey") String apiKey,
            @Path("checkInCheckOutId") int checkInCheckOutId
    );

    @POST("/api/v1/user/notifications/delete/{notificationId}")
    Call<StatusMessage> deleteNotification(
            @Header("apiKey") String apiKey,
            @Path("notificationId") int notificationId
    );

    @GET("/api/v1/user/pending-topup")
    Call<TopupStatusResponse> getTopupsStatusResponse(@Header("apiKey") String apiKey);

    @GET("/api/v1/user/pending-orders")
    Call<OrderStatusResponse> getOdersStatusResponse(@Header("apiKey") String apiKey);

    @GET("/api/v1/user/search-food")
    Call<ResponseBody> getSearchedFoodResponse(
            @Query("venueId") int venueId,
            @Query("keyword") String keyword,
            @Header("apiKey") String token
    );
}
