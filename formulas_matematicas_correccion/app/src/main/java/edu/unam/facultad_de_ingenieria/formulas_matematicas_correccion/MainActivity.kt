package edu.unam.facultad_de_ingenieria.formulas_matematicas_correccion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import edu.unam.facultad_de_ingenieria.formulas_matematicas_correccion.databinding.FormulasMatematicasBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FormulasMatematicasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.formulas_matematicas)
        binding =FormulasMatematicasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se le da el texto del botón
        binding.btnCalcular.text = getString(R.string.btnName)

        // Se define el texto del TextView del inicio
        binding.selectOptionsText.text = getString(R.string.seleccionaFormula)

        // Se le dan las opciones al Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.opcionesSpinner, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOptions.adapter = adapter

        // Se coloca el título del segundo TextView
        binding.respuestaTitle.text = getString(R.string.respuestaTitulo)

        // Se le da instrucciones al usuario
        binding.msgUsuario.text = getString(R.string.msgUsuario)

        // Se declaran las variables donde se guardarán los datos
        var respuesta:String = ""
        var dato1:String = ""
        var dato2:String = ""
        var dato3:String = ""

        // Se añade el evento del Spinner
        binding.spinnerOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Se realiza una acción en función de la opción seleccionada
                if(position == 0) {
                    // Ecuación de segundo grado

                    // Se limpia el text view
                    binding.respuesta.text = ""
                    dato1 = ""
                    dato2 = ""
                    dato3 = ""


                    // Se cambia la imagen de la ecuación
                    binding.imagen.setImageResource(R.drawable.chicharronera)

                    // Se le da un nombre a los inputs
                    binding.variable1.hint = "a"
                    binding.variable2.hint = "b"
                    // Se muestra un input
                    binding.variable3.visibility = View.VISIBLE
                    binding.variable3.hint = "c"

                    // Se habilita el evento onClick del botón
                    binding.btnCalcular.setOnClickListener {
                        // Se guardan los datos de los inputs
                        // dato1 -> a; dato2 -> b; dato3 -> c
                        dato1 = binding.variable1.text.toString()
                        dato2 = binding.variable2.text.toString()
                        dato3 = binding.variable3.text.toString()

                        if(!camposVacios(dato1,dato2,dato3)) {
                            respuesta = chicharronera(dato1.toDouble(),dato2.toDouble(),dato3.toDouble())

                            if (respuesta == "") {
                                mensajeUsuario(getString(R.string.errorTitle),
                                    getString(R.string.msgIndefinido1))
                            }
                        } else {
                            mensajeUsuario(getString(R.string.advertenciaTitle),
                                getString(R.string.msgLlenarInputs))
                        }

                        binding.respuesta.text = "$respuesta"
                    }

                } else if(position == 1) {
                    // Pitágoras

                    // Se limpia el text view
                    binding.respuesta.text = ""
                    dato1 = ""
                    dato2 = ""
                    dato3 = ""

                    // Se cambia la imagen de la ecuación
                    binding.imagen.setImageResource(R.drawable.pitagoras)

                    // Se le da un nombre a los inputs
                    binding.variable1.hint = "a"
                    binding.variable2.hint = "b"
                    // Se oculta un input
                    binding.variable3.visibility = View.INVISIBLE

                    // Se habilita el evento onClick del botón
                    binding.btnCalcular.setOnClickListener {
                        // Se guardan los datos de los inputs
                        // dato1 -> a; dato2 -> b
                        dato1 = binding.variable1.text.toString()
                        dato2 = binding.variable2.text.toString()

                        if(!camposVacios(dato1,dato2,"No es necesario este valor")) {
                            respuesta = pitagoras(dato1.toDouble(),dato2.toDouble())
                        } else {
                            mensajeUsuario(getString(R.string.advertenciaTitle),
                                getString(R.string.msgLlenarInputs))
                        }

                        binding.respuesta.text = "$respuesta"
                    }

                } else if(position == 2) {
                    // Gravitación universal

                    // Se limpia el text view
                    binding.respuesta.text = ""
                    dato1 = ""
                    dato2 = ""
                    dato3 = ""

                    // Se cambia la imagen de la ecuación
                    binding.imagen.setImageResource(R.drawable.gravitacion_universal)

                    // Se le da un nombre a los inputs
                    binding.variable1.hint = "m1"
                    binding.variable2.hint = "m2"
                    // Se muestra un input
                    binding.variable3.visibility = View.VISIBLE
                    binding.variable3.hint = "r"

                    binding.btnCalcular.setOnClickListener {
                        // Se guardan los datos de los inputs
                        // dato1 -> a; dato2 -> b
                        dato1 = binding.variable1.text.toString()
                        dato2 = binding.variable2.text.toString()
                        dato3 = binding.variable3.text.toString()

                        if(!camposVacios(dato1,dato2,dato3)) {
                            respuesta = gravitacionUniversal(dato1.toDouble(),dato2.toDouble(),dato3.toDouble())

                            if (respuesta == "1") {
                                mensajeUsuario(getString(R.string.errorTitle),
                                    getString(R.string.msgIndefinido2))
                                respuesta = ""
                            } else if(respuesta == "2") {
                                mensajeUsuario(getString(R.string.errorTitle),
                                    getString(R.string.msgMasas))
                                respuesta = ""
                            }
                        } else {
                            mensajeUsuario(getString(R.string.advertenciaTitle),
                                getString(R.string.msgLlenarInputs))
                        }

                        binding.respuesta.text = "$respuesta"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada (opcional)
            }
        }
    }

    // Se revisa si los campos están vacíos
    fun camposVacios(dato1: String = "", dato2: String = "", dato3: String = ""):Boolean {
        if(dato1.isEmpty() or dato2.isEmpty() or dato3.isEmpty()) {
            return true
        }

        return false
    }

    // Se realiza los cálculos de la ecuación de segundo grado
    fun chicharronera(a:Double = 0.0, b:Double = 0.0, c:Double = 0.0):String {
        // Se agregan variables auxiliares
        var respuesta:String = ""
        var radicando:Double = 0.0
        var raiz:Double = 0.0
        var x1:Double = 0.0
        var x2:Double = 0.0

        // Se revisa que a no sea 0, pues es una indefinición
        if(a != 0.0) {
            radicando = (b * b) - (4.0 * a * c)

            // Se revisa si la respuesta es un número complejo o un número real
            if(radicando >= 0.0) {
                x1 = (-b + kotlin.math.sqrt(radicando)) / (2.0 * a)
                x2 = (-b - kotlin.math.sqrt(radicando)) / (2.0 * a)

                respuesta = "x1=${"%.3f".format(x1)}, x2=${"%.3f".format(x2)}"
            } else {
                raiz = kotlin.math.sqrt(kotlin.math.abs(radicando)) / (2.0 * a)

                respuesta = "x1=${"%.1f".format((-b)/ (2 * a))}+${"%.2f".format(raiz)}i, "
                respuesta += "x2=${"%.1f".format((-b)/ (2 * a))}-${"%.2f".format(raiz)}i"
            }

            return respuesta
        }

        return respuesta
    }

    fun pitagoras(a:Double = 0.0, b:Double = 0.0):String {
        return "c=${"%.3f".format(kotlin.math.sqrt((a * a + b * b).toDouble()))}"
    }

    fun gravitacionUniversal(m1:Double = 0.0, m2:Double = 0.0, r:Double = 0.0):String {
        val G = 6.67430e-11

        if(r == 0.0) {
            return "1"
        } else if(m1 <= 0.0 || m2 <= 0.0) {
            return "2"
        }

        return "F=${"%.3e".format(G * ((m1 * m2) / (r * r)))}"
    }

    fun mensajeUsuario(title:String, msg:String) {
        AlertDialog.Builder(this@MainActivity).setTitle(title).setMessage(msg).setPositiveButton(getString(R.string.btnAceptar)) {
                dialog, _ -> dialog.dismiss()
        }.show()
    }
}