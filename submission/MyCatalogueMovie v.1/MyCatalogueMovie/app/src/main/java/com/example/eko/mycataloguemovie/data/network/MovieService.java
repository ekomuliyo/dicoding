package com.example.eko.mycataloguemovie.data.network;

import com.example.eko.mycataloguemovie.BuildConfig;
import com.example.eko.mycataloguemovie.data.model.MoviesResponse;
import com.example.eko.mycataloguemovie.data.model.TrailerResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("search/movie")
    Observable<MoviesResponse> searchMovies(@Query("query") String movieName);

    @GET("movie/{id}/videos")
    Observable<TrailerResponse>  getMovieTrailers(@Path("id") String movidId);

    class ServiceGenerator {
        public static MovieService instance(){
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final ApiKeyInterceptor apiKeyInterceptor = new ApiKeyInterceptor();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(apiKeyInterceptor)
                    .build();

            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .build();
            return retrofit.create(MovieService.class);
        }
    }
}
