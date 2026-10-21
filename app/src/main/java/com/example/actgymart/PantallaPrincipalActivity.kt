package com.example.actgymart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PantallaPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        val tvNombreUsuario = findViewById<TextView>(R.id.tvNombreUsuario)
        val btnSearch = findViewById<ImageView>(R.id.btnSearch)
        val btnHeart = findViewById<ImageView>(R.id.btnHeart)
        val btnPerson = findViewById<ImageView>(R.id.btnPerson)
        val btnCart = findViewById<ImageView>(R.id.btnCart)

        val cardPreEntreno = findViewById<LinearLayout>(R.id.cardPreEntreno)
        val cardProteina = findViewById<LinearLayout>(R.id.cardProteina)
        val cardCombos = findViewById<LinearLayout>(R.id.cardCombos)
        val cardCreatinas = findViewById<LinearLayout>(R.id.cardCreatinas)
        val cardFarmacos = findViewById<LinearLayout>(R.id.cardFarmacos)

        val usuarioIngresado = intent.getStringExtra("USUARIO_LOGUEADO") ?: "Usuario"
        tvNombreUsuario.text = "Hola $usuarioIngresado"

        btnSearch.setOnClickListener {
            Toast.makeText(this, "Buscar productos", Toast.LENGTH_SHORT).show()
        }

        btnHeart.setOnClickListener {
            Toast.makeText(this, "Tus Favoritos", Toast.LENGTH_SHORT).show()
        }

        btnCart.setOnClickListener {
            Toast.makeText(this, "Tu Carrito de compras", Toast.LENGTH_SHORT).show()
        }

        btnPerson.setOnClickListener {
            val opciones = arrayOf("Ver Perfil", "Cerrar Sesión")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Opciones de Perfil")
            builder.setItems(opciones) { _, which ->
                if (which == 0) {
                    Toast.makeText(this, "Información del usuario", Toast.LENGTH_SHORT).show()
                } else if (which == 1) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            builder.show()
        }

        cardPreEntreno.setOnClickListener {
            Toast.makeText(this, "Sección Pre Entreno", Toast.LENGTH_SHORT).show()
        }

        cardProteina.setOnClickListener {
            Toast.makeText(this, "Sección Proteínas", Toast.LENGTH_SHORT).show()
        }

        cardCombos.setOnClickListener {
            Toast.makeText(this, "Sección Combos", Toast.LENGTH_SHORT).show()
        }

        cardCreatinas.setOnClickListener {
            Toast.makeText(this, "Sección Creatinas", Toast.LENGTH_SHORT).show()
        }

        cardFarmacos.setOnClickListener {
            Toast.makeText(this, "Sección Fármacos", Toast.LENGTH_SHORT).show()
        }
    }
}