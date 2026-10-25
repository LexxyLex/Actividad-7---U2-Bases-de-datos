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

        val admin = AdminSQLiteOpenHelper(this)

        btnRegistrarme.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val usuario = etUsuarioReg.text.toString().trim()
            val pass = etPassReg.text.toString().trim()
            val passConfirm = etPassConfirm.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nombre.isEmpty() || usuario.isEmpty() || pass.isEmpty() || passConfirm.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (pass != passConfirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else if (admin.existeUsuario(usuario)) {
                Toast.makeText(this, "El nombre de usuario ya está registrado", Toast.LENGTH_SHORT).show()
            } else if (admin.existeEmail(email)) {
                Toast.makeText(this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
            } else {
                val exito = admin.registrarUsuario(usuario, nombre, pass, email)
                if (exito) {
                    Toast.makeText(this, "Registro exitoso. Inicia sesión", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar en la base de datos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnIrAInicioSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}