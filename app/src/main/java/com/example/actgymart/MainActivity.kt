package com.example.actgymart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnInicioSesion = findViewById<Button>(R.id.btnInicioSesion)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        btnInicioSesion.setOnClickListener {
            val user = etUsuario.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val preferences = getSharedPreferences("GymArtPrefs", MODE_PRIVATE)
            val usuarioGuardado = preferences.getString("USUARIO_REGISTRADO", null)
            val passwordGuardado = preferences.getString("PASSWORD_REGISTRADO", null)

            if (usuarioGuardado == null) {
                Toast.makeText(this, "El usuario no existe. Primero debe crear una cuenta", Toast.LENGTH_LONG).show()
            } else if (user == usuarioGuardado && pass == passwordGuardado) {
                val intent = Intent(this, PantallaPrincipalActivity::class.java)
                intent.putExtra("NOMBRE_USUARIO", user)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}