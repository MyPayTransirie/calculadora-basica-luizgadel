package com.example.mycalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

enum class Operacao(val simbolo: String) {
    SOMAR("+"),
    DIMINUIR("-"),
    MULTIPLICAR("×"),
    DIVIDIR("÷");

    override fun toString() = simbolo
}

class MainActivity : AppCompatActivity() {

    var resultado: Int = 0
    var ultOperacao: Operacao = Operacao.SOMAR

    var entradaTemResultado: Boolean = true
    var numEntrada: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnZero.setOnClickListener { validaEntrada(0) }
        btnUm.setOnClickListener { validaEntrada(1) }
        btnDois.setOnClickListener { validaEntrada(2) }
        btnTres.setOnClickListener { validaEntrada(3) }
        btnQuatro.setOnClickListener { validaEntrada(4) }
        btnCinco.setOnClickListener { validaEntrada(5) }
        btnSeis.setOnClickListener { validaEntrada(6) }
        btnSete.setOnClickListener { validaEntrada(7) }
        btnOito.setOnClickListener { validaEntrada(8) }
        btnNove.setOnClickListener { validaEntrada(9) }

        btnMais.setOnClickListener { validaEntrada(Operacao.SOMAR) }
        btnMenos.setOnClickListener { validaEntrada(Operacao.DIMINUIR) }
        btnVezes.setOnClickListener { validaEntrada(Operacao.MULTIPLICAR) }
        btnDividir.setOnClickListener { validaEntrada(Operacao.DIVIDIR) }

        btnIgual.setOnClickListener {
            textSecundario.text = ""
            calcula()
        }

        btnApagar.setOnClickListener {
            textSecundario.text = ""
            textPrincipal.text = ""
            numEntrada = 0
            resultado = 0
            ultOperacao = Operacao.SOMAR
        }
    }

    fun calcula() {

        resultado = when (ultOperacao) {
            Operacao.DIVIDIR -> resultado / numEntrada
            Operacao.MULTIPLICAR -> resultado * numEntrada
            Operacao.DIMINUIR -> resultado - numEntrada
            Operacao.SOMAR -> resultado + numEntrada
        }

        numEntrada = resultado
        textPrincipal.text = numEntrada.toString()
        entradaTemResultado = true
    }

    fun validaEntrada(num: Int) {
        if (entradaTemResultado) {
            numEntrada = num
            entradaTemResultado = false
        }
        else numEntrada = numEntrada*10 + num

        textPrincipal.text = numEntrada.toString()
    }

    fun validaEntrada(op: Operacao) {
        if (entradaTemResultado)
            textSecundario.text = "${textSecundario.text.dropLast(2)} $op"
        else {
            textSecundario.text = "${textSecundario.text} $numEntrada $op"

            calcula()
        }

        ultOperacao = op
    }
}