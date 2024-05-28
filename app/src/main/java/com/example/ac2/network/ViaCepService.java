package com.example.ac2.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<ViaCepResponse> getEndereco(@Path("cep") String cep);
}
