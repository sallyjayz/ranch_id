package com.sallyjayz.ranchid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.sallyjayz.ranchid.databinding.ActivityLoginBinding
import com.sallyjayz.ranchid.model.auth.Auth
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import io.intercom.android.sdk.Intercom
import io.intercom.android.sdk.IntercomError
import io.intercom.android.sdk.IntercomStatusCallback
import io.intercom.android.sdk.identity.Registration


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginOptionBottomSheet.OnInputSelectListener {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by viewModels()
    private var loginButtonClicked: Boolean = false
//    private var vetCouncilNumber: String? = null
    private var userRole: String? = null

    private val loginOptionBottomSheet = LoginOptionBottomSheet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        tokenViewModel.token.observe(this) { token ->
            /*if (token != null)
                startActivity(Intent(this, DashboardActivity::class.java))*/

//            call logout api and test again
            if (token != null) {
                successfulLogin()
//                startActivity(Intent(this, DashboardActivity::class.java))
                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
//                intent.putExtra("vetCouncilNumber", vetCouncilNumber)
                intent.putExtra("userRole", userRole)
                startActivity(intent)
                finish()
                /*val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)*/
            }
        }

        networkStatusViewModel.state.observe(this) { connection ->
            when(connection) {
                MyState.Fetched -> {
                    binding.offlineError.isVisible = false
                    binding.loginButton.isClickable = true
                    binding.loginButton.alpha = 1.0F
                    loginButtonClicked = false

                    viewModel.loginResponse.observe(this) {
                        when(it) {
//                is ApiResponse.Failure -> binding.loginError.text = it.errorMessage
//                ApiResponse.Loading -> binding.loginError.text = "Loading"
                            is ApiResponse.Failure -> {
//                            binding.loginError.text = it.errorMessage
                                binding.loginError.text = getString(R.string.incorrect_username_password)
                                binding.loginProgress.isVisible = false
                                binding.loginButton.isClickable = true
                                binding.loginButton.alpha = 1.0F
                            }
                            ApiResponse.Loading -> {
                                if (loginButtonClicked){
                                    binding.loginProgress.isVisible = true
                                    binding.loginButton.isClickable = false
                                    binding.loginButton.alpha = 0.5F
                                }
                            }
                            is ApiResponse.Success -> {
                                val name = "${it.data.user.surname} ${it.data.user.otherName}"
                                tokenViewModel.saveToken(it.data.token, it.data.user.username, name,
                                    it.data.user.userEmail, it.data.user.role/*, it.data.user.photo*/)
                                tokenViewModel.saveVetCouncilNumber(it.data.user.vetCouncilNumber)
//                                vetCouncilNumber = it.data.user.vetCouncilNumber
//                                userRole = it.data.user.role
                            }
                        }
                    }
                }
                MyState.Error -> {
                    binding.offlineError.isVisible = true
                    binding.loginButton.isClickable = true
                    binding.loginButton.alpha = 1.0F
                }
            }
        }


        binding.loginButton.setOnClickListener {

            loginButtonClicked = true

            if (isEntryValid()) {
                viewModel.login(
                    Auth(
                        binding.email.text.toString(),
                        binding.password.text.toString(),
                        userRole.toString()
                    ),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.loginError.text = "Error! $message"
//                            Log.d("Login Activity1", "Error! $message")
                            binding.offlineError.isVisible = true
                            binding.loginButton.isClickable = true
                            binding.loginButton.alpha = 1.0F
                        }
                    }
                )
            } else {
                binding.loginError.text = getString(R.string.all_fields_required)
            }
        }

        binding.loginButton.setOnClickListener {
            loginButtonClicked = true
            loginOptionBottomSheet.show(supportFragmentManager, LoginOptionBottomSheet.TAG)
        }

        /*viewModel.loginResponse.observe(this) {
            when(it) {
//                is ApiResponse.Failure -> binding.loginError.text = it.errorMessage
//                ApiResponse.Loading -> binding.loginError.text = "Loading"
                is ApiResponse.Failure -> {
//                            binding.loginError.text = it.errorMessage
                    binding.loginError.text = "Incorrect Username or Password"
                    binding.loginProgress.isVisible = false
                    binding.loginButton.isClickable = true
                    binding.loginButton.alpha = 1.0F
                }
                ApiResponse.Loading -> {
                    binding.loginProgress
                    binding.loginButton.isClickable = false
                    binding.loginButton.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val name = "${it.data.user.surname} ${it.data.user.otherName}"
                    tokenViewModel.saveToken(it.data.token, it.data.user.username, name,
                        it.data.user.userEmail, it.data.user.role*//*, it.data.user.photo*//*)
                }
            }
        }

        binding.loginButton.setOnClickListener {
            if (isEntryValid()) {
                viewModel.login(
                    Auth(
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    ),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                           binding.loginError.text = "Error! $message"
                        }
                    }
                )
            } else {
                binding.loginError.text = "All fields are required"
            }
        }*/

    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.email.text.toString(),
            binding.password.text.toString()
        )
    }

    private fun successfulLogin() {
        /* For best results, use a unique user_id if you have one. */
        tokenViewModel.username.observe(this) { username ->
            val registration = Registration.create().withUserId(username.toString())
            Intercom.client().loginIdentifiedUser(
                userRegistration = registration,
                intercomStatusCallback = object : IntercomStatusCallback {
                    override fun onSuccess() {
                        // Handle success

                    }

                    override fun onFailure(intercomError: IntercomError) {
                        // Handle failure
//                        Toast.makeText(this@LoginActivity, "Unable to start chat $intercomError", Toast.LENGTH_LONG).show()
                        Intercom.client().hideIntercom()
                    }

                }
            )

        }
    }

    override fun sendInput(data: String) {

        userRole = data

        if (isEntryValid()) {
            viewModel.login(
                Auth(
                    binding.email.text.toString(),
                    binding.password.text.toString(),
                    data
                ),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
//                        binding.loginError.text = "Error! $message"
//                        Log.d("Login Activity2", "Error! $message")
                        binding.offlineError.isVisible = true
                        binding.loginButton.isClickable = true
                        binding.loginButton.alpha = 1.0F
                    }
                }
            )
        } else {
            binding.loginError.text = getString(R.string.all_fields_required)
        }

        /*if (data == "ENUMERATOR" || data == "VETERINARY") {
            if (isEntryValid()) {
                viewModel.login(
                    Auth(
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    ),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            binding.loginError.text = "Error! $message"
                            binding.offlineError.isVisible = true
                            binding.loginButton.isClickable = true
                            binding.loginButton.alpha = 1.0F
                        }
                    }
                )
            } else {
                binding.loginError.text = getString(R.string.all_fields_required)
            }
        }*/
    }

}