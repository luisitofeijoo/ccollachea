// ApiClient.kt
import com.sisnperu.ccollachea.network.PersonasApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://comunidad.sisnperu.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val personasApiService: PersonasApiService by lazy {
        retrofit.create(PersonasApiService::class.java)
    }
}