package com.mokresh.tretton37.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mokresh.tretton37.BR
import com.mokresh.tretton37.utils.UIEvent
import com.dansdev.libeventpipe.EventPipe
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass


abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes val layoutId: Int, viewModelClass: KClass<VM>
) : Fragment() {


    private var _binding: B? = null

    lateinit var binding: B
    abstract fun onUIEventTriggered(event: UIEvent)

    open val viewModel: VM by viewModel(viewModelClass)
    var isFragmentVisible = false
    var isFragmentPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        registerEvent()
        viewModel.isDataEmpty.set(false)
    }

    init {
        viewLifecycleOwnerLiveData.observe(this) { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                /** Since we are observing on the view's lifecycle, this is
                 * the equivalent of the Fragment's onDestroyView() method
                 */
                override fun onDestroy(owner: LifecycleOwner) {
                    (_binding as? ViewDataBinding)?.unbind()
                    binding.unbind()
                }
            })
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return if (_binding != null) {
            binding = _binding!!
            binding.lifecycleOwner = this
            binding.setVariable(BR.lifecycle, this)
            binding.setVariable(BR.vm, viewModel)
            binding.root

        } else {
            null
        }

    }

    private fun onBackButtonPressed() {
        val navController = findNavController()
        navController.popBackStack()
    }

    private fun registerEvent() {
        EventPipe.registerEvent(UIEvent::class.java) { event ->
            when (event) {
                is UIEvent.OnBackPressed -> {
                    onBackButtonPressed()
                }
                else -> {
                    onUIEventTriggered(event)
                }
            }
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }


    override fun onPause() {
        super.onPause()
        isFragmentPaused = true
    }

    override fun onResume() {
        super.onResume()
        isFragmentPaused = false
        registerEvent()

    }

    override fun setMenuVisibility(visible: Boolean) {
        isFragmentVisible = visible
        super.setMenuVisibility(visible)
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        this.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

}