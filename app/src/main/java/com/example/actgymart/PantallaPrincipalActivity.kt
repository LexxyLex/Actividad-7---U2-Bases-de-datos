package com.example.actgymart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu

class PantallaPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        val tvNombreUsuario = findViewById<TextView>(R.id.tvNombreUsuario)
        val btnPerson = findViewById<ImageView>(R.id.btnPerson)

        val usuarioLogueado = intent.getStringExtra("USUARIO_LOGUEADO") ?: ""

        if (usuarioLogueado.isNotEmpty()) {
            val admin = AdminSQLiteOpenHelper(this)
            val nombreReal = admin.obtenerNombreUsuario(usuarioLogueado)
            tvNombreUsuario.text = "Hola $nombreReal"
        }

        btnPerson.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menu.add("Ir al perfil")
            popup.menu.add("Cerrar sesión")

            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Ir al perfil" -> {
                        val intent = Intent(this, PerfilActivity::class.java)
                        intent.putExtra("USUARIO_LOGUEADO", usuarioLogueado)
                        startActivity(intent)
                        true
                    }
                    "Cerrar sesión" -> {
                        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}