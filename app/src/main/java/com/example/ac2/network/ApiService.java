package com.example.ac2.network;

import com.example.ac2.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("aluno")
    Call<Void> salvarAluno(@Body Aluno aluno);

    @GET("aluno")
    Call<List<Aluno>> listarAlunos();
}
