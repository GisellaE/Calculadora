package com.example.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.myapplication3.databinding.ActivityMainBinding



    class MainActivity : AppCompatActivity(), View.OnClickListener {

        lateinit var binding: ActivityMainBinding
        var number: Int = 1

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setContent()

        }

        fun setContent() {
            binding.btnBorrar.setOnClickListener(this)
            binding.btn1.setOnClickListener(this)
            binding.btn2.setOnClickListener(this)
            binding.btn3.setOnClickListener(this)
            binding.btn4.setOnClickListener(this)
            binding.btn5.setOnClickListener(this)
            binding.btn6.setOnClickListener(this)
            binding.btn7.setOnClickListener(this)
            binding.btn8.setOnClickListener(this)
            binding.btn9.setOnClickListener(this)
            binding.btn0.setOnClickListener(this)
            binding.btnComa.setOnClickListener(this)
            binding.btnSumar.setOnClickListener(this)
            binding.btnRestar.setOnClickListener(this)
            binding.btnMultiplicar.setOnClickListener(this)
            binding.btnDivision.setOnClickListener(this)
            binding.btnIgual.setOnClickListener(this)
            binding.btnAbrir.setOnClickListener(this)
            binding.btnCerrar.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

                when (v?.id) {
                    R.id.btn1 -> {
                        binding.eTexto.append("1")
                    }

                    R.id.btn2 -> {
                        binding.eTexto.append("2")
                    }

                    R.id.btn3 -> {
                        binding.eTexto.append("3")
                    }

                    R.id.btn4 -> {
                        binding.eTexto.append("4")
                    }

                    R.id.btn5 -> {
                        binding.eTexto.append("5")
                    }

                    R.id.btn6 -> {
                        binding.eTexto.append("6")
                    }

                    R.id.btn7 -> {
                        binding.eTexto.append("7")
                    }

                    R.id.btn8 -> {
                        binding.eTexto.append("8")
                    }

                    R.id.btn9 -> {
                        binding.eTexto.append("9")
                    }

                    R.id.btn0-> {
                        binding.eTexto.append("0")
                    }

                    R.id.btnComa -> {
                        binding.eTexto.append(".")
                    }

                    R.id.btnSumar -> {
                        binding.eTexto.append("+")
                    }

                    R.id.btnRestar -> {
                        binding.eTexto.append("-")
                    }

                    R.id.btnMultiplicar -> {
                        binding.eTexto.append("*")
                    }

                    R.id.btnDivision -> {
                        binding.eTexto.append("/")
                    }

                    R.id.btnBorrar -> {
                        //borrar de un caracter a la vez
                        val cadena = binding.eTexto.text.toString()
                        if (cadena.isNotEmpty()) {
                            binding.eTexto.setText(cadena.substring(0, cadena.length - 1))
                        }
                    }
                }

                when (v?.id) {
                    R.id.btnIgual -> {
                        val cadena = binding.eTexto.text.toString() // Obtener la cadena de texto del EditText

                        // Resolver las operaciones dentro de los paréntesis
                        var expresionConParentesis = resolverParentesis(cadena)

                        // Dividir la expresión en números y operadores
                        val expresion: MutableList<String> = mutableListOf() // Lista que contendrá los números y operadores
                        var num = ""
                        for (caracter in expresionConParentesis) {
                            if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/') {
                                expresion.add(num)
                                expresion.add(caracter.toString())
                                num = ""
                            } else {
                                num += caracter
                            }
                        }
                        expresion.add(num)

                        // Resolver primero las multiplicaciones y divisiones
                        val expresionConMultiplicacionesYDivisiones: MutableList<String> = mutableListOf()
                        var i = 0
                        while (i < expresion.size) {
                            val token = expresion[i]
                            if (token == "*" || token == "/" || token == "(") {
                                val numAnterior = expresionConMultiplicacionesYDivisiones.removeAt(
                                    expresionConMultiplicacionesYDivisiones.size - 1
                                ).toDouble()
                                val operacion = expresion[i]
                                val numSiguiente = expresion[i + 1].toDouble()
                                val resultadoOperacion =
                                    if (operacion == "*") numAnterior * numSiguiente else numAnterior / numSiguiente
                                expresionConMultiplicacionesYDivisiones.add(resultadoOperacion.toString())
                                i += 2
                            } else {
                                expresionConMultiplicacionesYDivisiones.add(token)
                                i++
                            }
                        }
                        // Realizar las sumas y restas
                        var resultado = expresionConMultiplicacionesYDivisiones[0].toDouble()
                        i = 1
                        while (i < expresionConMultiplicacionesYDivisiones.size) {
                            val operador = expresionConMultiplicacionesYDivisiones[i]
                            val numero = expresionConMultiplicacionesYDivisiones[i + 1].toDouble()
                            when (operador) {
                                "+" -> resultado += numero
                                "-" -> resultado -= numero
                            }
                            i += 2
                        }

                        // Mostrar el resultado en el EditText
                        if (resultado % 1 == 0.0) {
                            binding.eTexto.setText(resultado.toInt().toString())
                        } else {
                            binding.eTexto.setText(resultado.toString())
                        }
                    }

                    R.id.btnAbrir -> {
                        binding.eTexto.append("(")
                    }

                    R.id.btnCerrar -> {
                        binding.eTexto.append(")")
                    }
                }
            }
        }

        //funcion para resolver las operaciones dentro de los paréntesis
        fun resolverParentesis(cadena: String): String {
            val pila = mutableListOf<Int>()
            val parentesisAbre = '('
            val parentesisCierra = ')'
            for (i in cadena.indices) {
                if (cadena[i] == parentesisAbre) {
                    pila.add(i)
                } else if (cadena[i] == parentesisCierra) {
                    if (pila.isEmpty()) {
                        throw IllegalArgumentException("Error: hay un paréntesis que no se cierra")
                    }
                    val parentesisAbreIndex = pila.removeAt(pila.size - 1)
                    val subcadena = cadena.substring(parentesisAbreIndex + 1, i)
                    val resultadoSubcadena = resolverParentesis(subcadena) //
                    val nuevaCadena =
                        cadena.substring(0, parentesisAbreIndex) + resultadoSubcadena + cadena.substring(i + 1)
                    return resolverParentesis(nuevaCadena)
                }
            }

            if (pila.isNotEmpty()) {
                throw IllegalArgumentException("Error: hay un paréntesis que no se cierra")
            }
            return cadena
        }