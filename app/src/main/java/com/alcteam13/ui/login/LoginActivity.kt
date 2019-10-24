package com.alcteam13.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import com.alcteam13.MainActivity
import com.alcteam13.R
import com.alcteam13.ui.registration.ui.register.RegisterActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var mEmailField : EditText? =  null
    private var mPassowrdField : EditText? = null

    private var mProgressBar: ProgressBar? = null

    private var mForgotBtn : TextView? = null
    private var mLoginBtn : Button? = null
    private var mCreateAccountBtn : Button? = null

    var firebaseAuth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // added activity transition animation
        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim)
        setContentView(R.layout.activity_login)

        initViews()
    }

    private fun initViews(){

        firebaseAuth = FirebaseAuth.getInstance()

        mEmailField = findViewById(R.id.email)
        mPassowrdField = findViewById(R.id.password)

        mProgressBar = findViewById(R.id.loading)

        mForgotBtn = findViewById(R.id.forgot_password_btn)
        mLoginBtn = findViewById(R.id.login_btn)
        mCreateAccountBtn = findViewById(R.id.create_account_btn)

        // attaching on click listener for create account button
        mCreateAccountBtn?.setOnClickListener {
            // navigate to sign up page
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        // attaching on click listener for login button
        mLoginBtn?.setOnClickListener {
            validateFields()
        }
    }

    // validate field before login
    private fun validateFields(){

        val email = mEmailField!!.text.toString().trim()
        val password = mPassowrdField!!.text.toString().trim()

        if(TextUtils.isEmpty(email)){
            mEmailField!!.error = getString(R.string.error_empty_email_field)
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mPassowrdField!!.error = getString(R.string.error_invalid_email)
        }
        else if(TextUtils.isEmpty(password)){
            mPassowrdField!!.error = getString(R.string.error_empty_password_field)
        }
        else if(password.length < 6){
            mPassowrdField!!.error = getString(R.string.error_invalid_password)
        }
        else{
            login(email,password)
        }
    }

    // login registered user
    private fun login(email : String, password : String){

        mProgressBar!!.visibility = View.VISIBLE

        this.firebaseAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                // login is successful
                val firebaseUser = this.firebaseAuth!!.currentUser!!
                Toast.makeText(this,getString(R.string.msg_login_successful),Toast.LENGTH_SHORT).show()
                // navigate to home page
                startActivity(Intent(this,MainActivity::class.java))


            } else {
                // an error occurred
                Toast.makeText(this,"Error: " + task.exception,Toast.LENGTH_SHORT).show()
            }

            mProgressBar!!.visibility = View.GONE
        }
    }

}
