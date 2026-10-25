package com.example.actgymart

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    private var intentosRestantes = 3
    private var estaBloqueado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnInicioSesion = findViewById<AppCompatButton>(R.id.btnInicioSesion)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        val admin = AdminSQLiteOpenHelper(this)

        btnInicioSesion.setOnClickListener {
            if (estaBloqueado) {
                Toast.makeText(this, "Espera a que termine el tiempo de bloqueo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = etUsuario.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (admin.validarUsuario(user, pass)) {
                    intentosRestantes = 3
                    Toast.makeText(this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, PantallaPrincipalActivity::class.java)
                    intent.putExtra("USUARIO_LOGUEADO", user)
                    startActivity(intent)
                    finish()
                } else {
                    intentosRestantes--
                    if (intentosRestantes > 0) {
                        Toast.makeText(
                            this,
                            "Usuario o contraseña incorrectos. Te quedan $intentosRestantes intento(s)",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        estaBloqueado = true
                        Toast.makeText(this, "Has superado los 3 intentos. Espera 3 minutos", Toast.LENGTH_LONG).show()

                        object : CountDownTimer(180000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}

                            override fun onFinish() {
                                estaBloqueado = false
                                intentosRestantes = 3
                                Toast.makeText(this@MainActivity, "Ya puedes volver a intentarlo", Toast.LENGTH_SHORT).show()
                            }
                        }.start()
                    }
                }
            }
        }

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}