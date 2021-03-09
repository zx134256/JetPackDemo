package hy.test.jetpackdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import hy.test.jetpackdemo.R
import hy.test.jetpackdemo.base.utils.RxViewUtils
import hy.test.jetpackdemo.databinding.FragmentLoginBinding
import hy.test.jetpackdemo.viewModel.LiveDataViewModel
import hy.test.jetpackdemo.viewModel.LiveDataViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment :Fragment() {

    private var binding :FragmentLoginBinding?=null

    private val viewmodel: LiveDataViewModel by viewModels { LiveDataViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        binding?.viewmodel = viewmodel
        tonetwork?.let {
            RxViewUtils.clicks(tonetwork,this).subscribe {
                Navigation.findNavController(tonetwork).navigate(R.id.action_login_fragment_to_netWorkFragment)
            }
        }
    }
}