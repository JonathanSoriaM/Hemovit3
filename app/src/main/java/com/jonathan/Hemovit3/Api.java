package com.jonathan.Hemovit3;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface Api {

    @Headers({
            "Content-Type:application/json"
    })
    @POST("usuarios")
    Call<ResponseBody> registrarUsuario(@Body Map<String,Object> versionId);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("verificacion")
    Call<ResponseBody> verificarUsuario(@Body Map<String,Object> versionId);

    @Headers({
            "Content-Type:application/json"
    })

    @POST("comentarios")
    Call<ResponseBody> MandarComentarios(@Body Map<String,Object> versionId);

    @Headers({
            "Content-Type:application/json"
    })

    @POST("hemovigilancia")
    Call<ResponseBody> Hemovigilancia (@Body Map<String,Object> versionId);

    @Headers({
            "Content-Type:application/json"
    })

    @POST("pedidos")
    Call<ResponseBody> Pedidos (@Body Map<String,Object> versionId);

    @GET("inventario")
    Call<List<Listado>> getInventario();

    @GET("negativos")
    Call<List<Listado>> getInventarioNeg();
}
