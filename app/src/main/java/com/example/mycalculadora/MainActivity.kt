package com.example.mycalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

enum class Operacao(val simbolo: String) {
    SOMAR("+"),
    DIMINUIR("-"),
    MULTIPLICAR("×"),
    DIVIDIR("÷");

    override fun toString() = simbolo
}

enum class TipoEntrada {
    NUMERAL,
    OPERADOR,
    RESULT_TOTAL
}

class MainActivity : AppCompatActivity() {

    var ultOperacao: Operacao = Operacao.SOMAR
    var ultEntrada: TipoEntrada = TipoEntrada.NUMERAL

    var ultResult: Int = 0
    var numPrincipal: Int = 0

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
            if (ultEntrada == TipoEntrada.RESULT_TOTAL) {
                textSecundario.text = "$ultResult $ultOperacao"
            }
            textSecundario.text = "${textSecundario.text} $numPrincipal ="
            calculaOperacaoAnterior()

            ultEntrada = TipoEntrada.RESULT_TOTAL
            //textlog4.text = "RESULT_TOTAL"
        }

        btnApagar.setOnClickListener {
            textSecundario.text = ""
            textPrincipal.text = ""
            ultOperacao = Operacao.SOMAR
            ultEntrada = TipoEntrada.NUMERAL

            numPrincipal = 0
            ultResult = 0
        }
    }

    /*
    * Valida a entrada atual se ela for inteiro
    * - Se a entrada anterior foi um NUMERAL
    * --- Transforma o valor do textPrincipal em uma dezena e soma com a entrada atual
    * - Senão
    * --- Sobreescreve o numPrincipal com a entrada atual
    * --- Redefine a última entrada como NUMERAL
    * - Redefine o textPrincipal
    */
    fun validaEntrada(num: Int) {
        //textlog4.text = "$num"
        if (ultEntrada == TipoEntrada.NUMERAL)
            numPrincipal = numPrincipal * 10 + num
        else {
            numPrincipal = num
            ultEntrada = TipoEntrada.NUMERAL

            //textlog4.text = "NUMERAL"
        }
        //textlog8.text = "$numPrincipal"

        textPrincipal.text = numPrincipal.toString()
    }

    /*
    * Valida a entrada atual se ela for uma operação
    * - Se a entrada anterior havia também sido um OPERADOR:
    * --- Apaga o operador antigo da fórmula
    * - Senão
    * --- Se a entrada anterior havia sido um resultado total:
    * ----- Torna esse resultado o 1° operando na fórmula
    * --- Senão, se a entrada anterior havia sido um numeral:
    * ----- Calcula a operação anterior
    * --- Redefine a entrada anterior como Operador
    * - Guarda a entrada atual como último operador
    * - Adiciona o utltimo operador como operador à fórmula
    */
    fun validaEntrada(op: Operacao) {
        if (ultEntrada == TipoEntrada.OPERADOR)
            textSecundario.text = textSecundario.text.dropLast(2)
        else {
            if (ultEntrada == TipoEntrada.RESULT_TOTAL)
                textSecundario.text = "$ultResult"
            else {
                textSecundario.text = "${textSecundario.text} $numPrincipal"
                calculaOperacaoAnterior()
            }

            ultEntrada = TipoEntrada.OPERADOR
            //textlog2.text = "OPERADOR"
        }

        ultOperacao = op
        //textlog4.text = "$op"
        textSecundario.text = "${textSecundario.text} $ultOperacao"
    }

    /*
    * Calcula a última operação
    * - Verifica qual o tipo da última operação e sobrescreve o numPrincipal com o resultado
    * - Escreve o numPrincipal no textPrincipal
    *
    */
    fun calculaOperacaoAnterior() {
        if (ultOperacao == Operacao.DIVIDIR && numPrincipal == 0)
            Toast.makeText(this, "Não é possível dividir por 0! Digite outro valor.", Toast.LENGTH_SHORT).show()
        else {
            ultResult = when (ultOperacao) {
                Operacao.DIVIDIR -> ultResult / numPrincipal
                Operacao.MULTIPLICAR -> ultResult * numPrincipal
                Operacao.DIMINUIR -> ultResult - numPrincipal
                Operacao.SOMAR -> ultResult + numPrincipal
            }

            //textlog6.text = "$ultResult"
            textPrincipal.text = ultResult.toString()
        }
    }
}