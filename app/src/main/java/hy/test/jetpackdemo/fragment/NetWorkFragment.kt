package hy.test.jetpackdemo.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import hy.test.jetpackdemo.R
import hy.test.jetpackdemo.Repository.NetWorkRepository
import hy.test.jetpackdemo.databinding.NetWorkFragmentBinding
import hy.test.jetpackdemo.viewModel.NetWorkViewModel
import hy.test.jetpackdemo.viewModel.NetWorkViewModelFactory
import kotlinx.android.synthetic.main.net_work_fragment.*

class NetWorkFragment : Fragment() {

    var binding : NetWorkFragmentBinding? = null
    companion object {
        fun newInstance() = NetWorkFragment()
    }

    private val viewModel: NetWorkViewModel by viewModels { NetWorkViewModelFactory(
        NetWorkRepository()
    ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<NetWorkFragmentBinding>(inflater,R.layout.net_work_fragment, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.lifecycleOwner = this
        binding?.homenfoViewModel = viewModel

        viewModel.homeInfo?.observe(viewLifecycleOwner, Observer {
            Log.d("zx","observer${it.msg+  ""+it.status}")
            homeinfo.text = it.msg
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}