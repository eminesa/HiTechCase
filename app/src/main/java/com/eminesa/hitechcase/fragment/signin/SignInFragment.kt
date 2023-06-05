package com.eminesa.hitechcase.fragment.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.eminesa.hitechcase.databinding.FragmentSignInBinding
import com.eminesa.hitechcase.model.UserModel

class SignInFragment : Fragment() {

    private var binding: FragmentSignInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null)
            binding = FragmentSignInBinding.inflate(inflater)

        val arg = arguments?.getParcelable<UserModel>("userModel")
        arg?.apply {
            binding?.avatarImageView?.let { image ->
                Glide.with(image.context)
                    .load(avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image)
            }

            binding?.nameTextView?.text = firstName
            binding?.webSiteTextView?.text = webSite
            binding?.mailTextView?.text = mail
         }

        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}