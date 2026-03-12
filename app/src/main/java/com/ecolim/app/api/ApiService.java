package com.ecolim.app.api;

import com.ecolim.app.models.Residuo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("residuos")
    Call<List<Residuo>> obtenerResiduos();

    @POST("residuos")
    Call<Residuo> enviarResiduo(@Body Residuo residuo);
}