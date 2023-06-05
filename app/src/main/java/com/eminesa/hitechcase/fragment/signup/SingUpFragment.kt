package com.eminesa.hitechcase.fragment.signup

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eminesa.hitechcase.*
import com.eminesa.hitechcase.databinding.FragmentSingUpBinding
import com.eminesa.hitechcase.extensions.*
import com.eminesa.hitechcase.model.UserModel
import java.io.File

class SingUpFragment : Fragment() {

    private var binding: FragmentSingUpBinding? = null
    private var user: UserModel? = null
    private val viewModel: SingUpViewModel by viewModels()
    private var capturedPhotoFile: File? = null

    private var cameraPermissionLauncher: ActivityResultLauncher<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        if (binding == null) binding = FragmentSingUpBinding.inflate(inflater)

        cameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            } else{
                requestCameraPermission()
            }
        }

        return binding?.root
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            shouldShowRequestPermissionRationale(CAMERA) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                cameraPermissionLauncher?.launch(CAMERA)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Camera Permission Required")
            .setMessage("You must give permission to use the camera.")
            .setPositiveButton("Allow") { _, _ ->
                cameraPermissionLauncher?.launch(CAMERA)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            submitListener()
            takePhotoListener()
            initWatcher()
        }

        viewModel.isFirstNameValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding?.signUpFirstNameEditText?.error = getString(R.string.first_name_regex)
            }
        }

        viewModel.isMailValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding?.signUpMailEditText?.error = getString(R.string.mail_regex)
            }
        }

        viewModel.isPasswordValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding?.signUpPasswordEditText?.error = getString(R.string.password_regex)
            }
        }

        viewModel.isWebsiteValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding?.signUpWebSiteEditText?.error = getString(R.string.web_site_regex)
            }
        }

        viewModel.isInputValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding?.submitButton?.isAbility(true)
            } else {
                binding?.submitButton?.isAbility(false)
            }
        }

        viewModel.uri.observe(viewLifecycleOwner) {
            binding?.avatarImageView?.let { image ->
                Glide.with(requireContext())
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image)
            }
        }
    }

    private fun FragmentSingUpBinding.takePhotoListener() {
        takePhotoImageView.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun FragmentSingUpBinding.submitListener() {
        submitButton.setOnClickListener {

            user = UserModel(
                viewModel.uri.value,
                signUpFirstNameEditText.text.toString(),
                signUpMailEditText.text.toString(),
                signUpPasswordEditText.text.toString(),
                signUpWebSiteEditText.text.toString(),
            )

            findNavController().navigate(
                R.id.action_singUpFragment_to_signInFragment, bundleOf("userModel" to user)
            )
        }
    }

    private fun openCamera() {

        capturedPhotoFile = getCaptureImageOutputFile(requireActivity())
        capturedPhotoFile?.apply {
            val photoURI = getCaptureImageOutputUri(this, requireContext())

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraResultLauncher.launch(takePictureIntent)
        }
    }

    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {

                binding?.apply {

                    capturedPhotoFile?.absolutePath?.let {
                        viewModel.setUri(Uri.fromFile(File(it)))
                    }
                }
            }
        }

    private fun FragmentSingUpBinding.initWatcher() {

        signUpFirstNameEditText.addTextChangedListener {
            viewModel.setFirstName(it.toString())
        }

        signUpMailEditText.addTextChangedListener {
            viewModel.setEmail(it?.trim().toString())
        }

        signUpPasswordEditText.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        signUpWebSiteEditText.addTextChangedListener {
            viewModel.setWebSite(it.toString())
        }

    }

    override fun onDestroy() {

        binding?.apply {
            signUpFirstNameEditText.removeTextChangedListener(null)
            signUpMailEditText.removeTextChangedListener(null)
            signUpPasswordEditText.removeTextChangedListener(null)
            signUpWebSiteEditText.removeTextChangedListener(null)
        }

        viewModel.apply {
            isFirstNameValid.removeObservers(this@SingUpFragment)
            isMailValid.removeObservers(this@SingUpFragment)
            isPasswordValid.removeObservers(this@SingUpFragment)
            isWebsiteValid.removeObservers(this@SingUpFragment)
        }

        cameraPermissionLauncher = null
        binding = null
        user = null
        capturedPhotoFile = null

        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        user = null
        capturedPhotoFile = null
        cameraPermissionLauncher = null

        viewModel.apply {
            isFirstNameValid.removeObservers(viewLifecycleOwner)
            isMailValid.removeObservers(viewLifecycleOwner)
            isPasswordValid.removeObservers(viewLifecycleOwner)
            isWebsiteValid.removeObservers(viewLifecycleOwner)
        }
    }
}