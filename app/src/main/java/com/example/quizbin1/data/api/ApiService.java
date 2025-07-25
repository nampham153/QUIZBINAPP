package com.example.quizbin1.data.api;

import com.example.quizbin1.data.model.dto.*;

import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.GET;

public interface ApiService {

    @POST("api/User/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/User/register")
    Call<LoginResponse> register(@Body RegisterRequest request);

    @GET("api/Subjects/user/{userId}")
    Call<List<SubjectDTO>> getSubjectsByUser(@Path("userId") String userId);
    @GET("api/Subject")
    Call<List<SubjectDTO>> getAllSubjects();
    @GET("api/Information/User/{userId}")
    Call<InformationDTO> getInformationByUserId(@Path("userId") UUID userId);
    @POST("api/Information")
    Call<InformationDTO> createInformation(@Body UpdateInformationRequestDTO request);
    @PUT("api/Information/{id}")
    Call<InformationDTO> updateInformation(@Path("id") UUID infoId, @Body UpdateInformationRequestDTO updateRequest);
    @PUT("api/User/change-password")
    Call<ResponseBody> changePassword(@Body ChangePasswordDTO request);
    @GET("api/Semester")
    Call<List<SemesterDTO>> getAllSemesters();
    @GET("api/Question/semester/{semesterId}")
    Call<List<QuestionDTO>> getQuestionsBySemesterId(@Path("semesterId") UUID semesterId);
    @GET("api/Option/question/{questionId}")
    Call<List<OptionDTO>> getOptionsByQuestionId(@Path("questionId") UUID questionId);
    @GET("api/Subject/teacher/{teacherId}")
    Call<List<SubjectDTO>> getAllSubjectByTeacher(@Path("teacherId") String teacherId);
    @POST("api/Subject")
    Call<Void> createSubject(@Body AddSubjectRequestDTO request);
    @GET("api/Semester/subject/{subjectId}")
    Call<List<SemesterDTO>> getAllSemesterBySubject(@Path("subjectId") UUID subjectId);
    @POST("api/Semester")
    Call<Void> addSemester(@Body AddSemesterRequestDTO request);
    @POST("api/Question")
    Call<QuestionDTO> createQuestion(@Body AddQuestionRequestDTO request);
    @POST("api/Option")
    Call<OptionDTO> createOption(@Body AddOptionRequestDTO request);
}
