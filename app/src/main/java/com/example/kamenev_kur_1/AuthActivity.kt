package com.example.kamenev_kur_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kamenev_kur_1.Classes.AuthData
import com.example.kamenev_kur_1.Classes.MainApi
import com.example.kamenev_kur_1.databinding.ActivityAuthBinding
import com.example.kamenev_kur_1.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

lateinit var authBinding: ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(authBinding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.27.8:5293/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val userApi = retrofit.create(MainApi::class.java)

        authBinding.btnAuth.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                if(authBinding.etLogin.text.isEmpty() || authBinding.etPassword.text.isEmpty())
                {
                    runOnUiThread {
                        Toast.makeText(
                            this@AuthActivity,
                            "Все поля должны быть заполнены!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else{
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val user = userApi.postLoginData(
                                AuthData(
                                    authBinding.etLogin.text.toString(),
                                    authBinding.etPassword.text.toString()
                                )
                            )

                            authBinding.etLogin.text.clear()
                            authBinding.etPassword.text.clear()

                            runOnUiThread{
                                Toast.makeText(this@AuthActivity, "Вы успешно авторизовались!", Toast.LENGTH_SHORT).show()
                            }

                            val intent = Intent(this@AuthActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        catch(e: Exception){
                            runOnUiThread{
                                Toast.makeText(this@AuthActivity, "Пользователь не найден!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        authBinding.btnReg.setOnClickListener {
            val intent = Intent(this@AuthActivity, RegActivity::class.java)
            startActivity(intent)
        }
    }
}