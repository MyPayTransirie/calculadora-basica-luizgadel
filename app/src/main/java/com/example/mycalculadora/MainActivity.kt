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
    var ultimaOp: Operacao = Operacao.SOMAR

    var entradaVazia: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnZero.setOnClickListener { validaEntrada("0") }
        btnUm.setOnClickListener { validaEntrada("1") }
        btnDois.setOnClickListener { validaEntrada("2") }
        btnTres.setOnClickListener { validaEntrada("3") }
        btnQuatro.setOnClickListener { validaEntrada("4") }
        btnCinco.setOnClickListener { validaEntrada("5") }
        btnSeis.setOnClickListener { validaEntrada("6") }
        btnSete.setOnClickListener { validaEntrada("7") }
        btnOito.setOnClickListener { validaEntrada("8") }
        btnNove.setOnClickListener { validaEntrada("9") }

        btnMais.setOnClickListener { validaEntrada(Operacao.SOMAR) }
        btnMenos.setOnClickListener { validaEntrada(Operacao.DIMINUIR) }
        btnVezes.setOnClickListener { validaEntrada(Operacao.MULTIPLICAR) }
        btnDividir.setOnClickListener { validaEntrada(Operacao.DIVIDIR) }

        btnIgual.setOnClickListener {
            textSecundario.text = ""
            ultimaOp = Operacao.SOMAR
            calcula()
        }

        btnApagar.setOnClickListener {
            textSecundario.text = ""
            textPrincipal.text = ""
            resultado = 0
            ultimaOp = Operacao.SOMAR
        }
    }

    fun calcula() {
        val segundoOperando = Integer.parseInt(textPrincipal.text.toString())

        resultado = when (ultimaOp) {
            Operacao.DIVIDIR -> resultado / segundoOperando
            Operacao.MULTIPLICAR -> resultado * segundoOperando
            Operacao.DIMINUIR -> resultado - segundoOperando
            Operacao.SOMAR -> resultado + segundoOperando
        }

        textPrincipal.text = resultado.toString()
        entradaVazia = true
    }

    fun validaEntrada(num: String) {
        if (entradaVazia) {
            textPrincipal.text = num
            entradaVazia = false
        }
        else textPrincipal.text = textPrincipal.text.toString() + num
    }

    fun validaEntrada(op: Operacao) {
        if (entradaVazia)
            textSecundario.text = "${textSecundario.text.dropLast(2)} $op"
        else {
            textSecundario.text = "${textSecundario.text} ${textPrincipal.text} $op"

            calcula()
        }

        ultimaOp = op
    }
}