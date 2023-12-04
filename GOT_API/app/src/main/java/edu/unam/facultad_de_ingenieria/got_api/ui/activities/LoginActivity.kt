package edu.unam.facultad_de_ingenieria.got_api.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import edu.unam.facultad_de_ingenieria.got_api.R
import edu.unam.facultad_de_ingenieria.got_api.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var psswd: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        with(binding){
            loginBtn.setOnClickListener {
                if(!validaCampos()) return@setOnClickListener
                authUser(email, psswd)
            }

            registerBtn.setOnClickListener {
                if(!validaCampos()) return@setOnClickListener
                registerUser(email, psswd)
            }

            olvidastePsswd.setOnClickListener {
                val resetEmail = EditText(it.context)
                resetEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                AlertDialog.Builder(it.context)
                    .setTitle(getString(R.string.reset_password))
                    .setMessage(getString(R.string.enterEmailReset))
                    .setView(resetEmail)
                    .setPositiveButton(getString(R.string.enviar)){ _, _ ->
                        val mail = resetEmail.text.toString()
                        if(mail.isNotEmpty()){
                            firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                                Toast.makeText(this@LoginActivity,
                                    getString(R.string.emailSentSuccess), Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(this@LoginActivity,
                                    getString(R.string.emailSentFailure), Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@LoginActivity,
                                getString(R.string.pleaseEnterEmail), Toast.LENGTH_SHORT).show()
                        }
                    }.setNegativeButton(getString(R.string.cancel)){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }
    private fun validaCampos(): Boolean{
        email = binding.etEmail.text.toString().trim()
        psswd = binding.contrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.etEmail.error = getString(R.string.emailRequired)
            binding.etEmail.requestFocus()
            return false
        }

        if(psswd.isEmpty() || psswd.length < 8){
            binding.contrasenia.error = getString(R.string.passwordLengthRequired)
            binding.contrasenia.requestFocus()
            return false
        }

        return true
    }

    private fun handleError(task: Task<AuthResult>){
        var error = ""

        try{
            error = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            e.printStackTrace()
        }

        when(error){
            getString(R.string.error_invalid_email) -> {
                Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show()
                binding.etEmail.error = getString(R.string.invalidEmail)
                binding.etEmail.requestFocus()
            }
            getString(R.string.error_wrong_password) -> {
                Toast.makeText(this, getString(R.string.invalidPassword), Toast.LENGTH_SHORT).show()
                binding.contrasenia.error = getString(R.string.invalidPassword)
                binding.contrasenia.requestFocus()
                binding.contrasenia.setText("")

            }
            getString(R.string.account_exists) -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, getString(R.string.existingAccount), Toast.LENGTH_SHORT).show()
            }
            getString(R.string.error_email) -> {
                Toast.makeText(this, getString(R.string.emailAlreadyUsed), Toast.LENGTH_LONG).show()
                binding.etEmail.error = getString(R.string.emailAlreadyUsed)
                binding.etEmail.requestFocus()
            }
            getString(R.string.error_user_token_expired) -> {
                Toast.makeText(this, getString(R.string.expired_session), Toast.LENGTH_LONG).show()
            }
            getString(R.string.error_user_not_found) -> {
                Toast.makeText(this, getString(R.string.inexistent_user), Toast.LENGTH_LONG).show()
            }
            getString(R.string.error_password) -> {
                Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_LONG).show()
                binding.contrasenia.error = getString(R.string.password_length)
                binding.contrasenia.requestFocus()
            }
            getString(R.string.no_network) -> {
                Toast.makeText(this, getString(R.string.noRed), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this,
                    getString(R.string.authentication_failure), Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun authUser(usr: String, psw: String){
        binding.pbConexion.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            binding.pbConexion.visibility = View.GONE

            if(authResult.isSuccessful){
                Toast.makeText(this,
                    getString(R.string.autenticacion_exitosa), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                handleError(authResult)
            }
        }
    }
    private fun registerUser(usr: String, psw: String){
        binding.pbConexion.visibility = View.VISIBLE
        firebaseAuth.createUserWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            if(authResult.isSuccessful){
                val user = firebaseAuth.currentUser
                user?.sendEmailVerification()?.addOnSuccessListener {
                    Toast.makeText(this, getString(R.string.emailEnviado), Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener {
                    Toast.makeText(this,
                        getString(R.string.emailNoEnviado), Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this, getString(R.string.usuarioCreado), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                handleError(authResult)
            }
        }
    }
}