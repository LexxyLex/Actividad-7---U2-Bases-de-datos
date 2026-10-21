package com.example.actgymart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etUsuarioReg = findViewById<EditText>(R.id.etUsuarioReg)
        val etPassReg = findViewById<EditText>(R.id.etPassReg)
        val etPassConfirm = findViewById<EditText>(R.id.etPassConfirm)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnRegistrarme = findViewById<AppCompatButton>(R.id.btnRegistrarme)
        val btnIrAInicioSesion = findViewById<Button>(R.id.btnIrAInicioSesion)

        btnRegistrarme.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val usuario = etUsuarioReg.text.toString().trim()
            val pass = etPassReg.text.toString().trim()
            val passConfirm = etPassConfirm.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nombre.isEmpty() || usuario.isEmpty() || pass.isEmpty() || passConfirm.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != passConfirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val preferences = getSharedPreferences("GymArtPrefs", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("USUARIO_REGISTRADO", usuario)
            editor.putString("PASSWORD_REGISTRADO", pass)
            editor.putString("NOMBRE_REGISTRADO", nombre)
            editor.putString("EMAIL_REGISTRADO", email)
            editor.apply()

            Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnIrAInicioSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}