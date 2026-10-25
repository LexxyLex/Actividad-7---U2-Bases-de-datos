package com.example.actgymart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val btnVolver = findViewById<ImageView>(R.id.btnVolver)
        val tvSubNombre = findViewById<TextView>(R.id.tvSubNombre)
        val etNombrePerfil = findViewById<EditText>(R.id.etNombrePerfil)
        val etUsuarioPerfil = findViewById<EditText>(R.id.etUsuarioPerfil)
        val etPassPerfil = findViewById<EditText>(R.id.etPassPerfil)
        val etEmailPerfil = findViewById<EditText>(R.id.etEmailPerfil)
        val btnEditarInfo = findViewById<AppCompatButton>(R.id.btnEditarInfo)
        val btnGuardarInfo = findViewById<Button>(R.id.btnGuardarInfo)

        val usuarioLogueado = intent.getStringExtra("USUARIO_LOGUEADO") ?: ""
        val admin = AdminSQLiteOpenHelper(this)

        if (usuarioLogueado.isNotEmpty()) {
            val datos = admin.obtenerDatosUsuario(usuarioLogueado)
            if (datos != null) {
                tvSubNombre.text = datos[0]
                etNombrePerfil.setText(datos[0])
                etUsuarioPerfil.setText(usuarioLogueado)
                etPassPerfil.setText(datos[1])
                etEmailPerfil.setText(datos[2])
            }
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipalActivity::class.java)
            intent.putExtra("USUARIO_LOGUEADO", usuarioLogueado)
            startActivity(intent)
            finish()
        }

        btnEditarInfo.setOnClickListener {
            etNombrePerfil.isEnabled = true
            etPassPerfil.isEnabled = true
            etEmailPerfil.isEnabled = true
            btnGuardarInfo.isEnabled = true
            Toast.makeText(this, "Campos habilitados para edición", Toast.LENGTH_SHORT).show()
        }

        btnGuardarInfo.setOnClickListener {
            val nombreNuevo = etNombrePerfil.text.toString().trim()
            val passNueva = etPassPerfil.text.toString().trim()
            val emailNuevo = etEmailPerfil.text.toString().trim()

            if (nombreNuevo.isEmpty() || passNueva.isEmpty() || emailNuevo.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val exito = admin.actualizarUsuario(usuarioLogueado, nombreNuevo, passNueva, emailNuevo)
                if (exito) {
                    tvSubNombre.text = nombreNuevo
                    etNombrePerfil.isEnabled = false
                    etPassPerfil.isEnabled = false
                    etEmailPerfil.isEnabled = false
                    btnGuardarInfo.isEnabled = false
                    Toast.makeText(this, "Información actualizada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "El correo electrónico ya está en uso por otro usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}