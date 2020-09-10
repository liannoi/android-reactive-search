package org.itstep.liannoi.reactivesearch.presentation.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.itstep.liannoi.reactivesearch.databinding.FragmentUsersBinding
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentUsersBinding
    private lateinit var listAdapter: UsersAdapter
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUsersBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        setupListAdapter()
        setupNameInput()
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    private fun setupListAdapter() {
        listAdapter = UsersAdapter(viewDataBinding.viewmodel ?: return)
        viewDataBinding.usersList.adapter = listAdapter
    }

    private fun setupNameInput() {
        viewDataBinding.findUserNameInput
            .textChanges()
            .debounce(600, TimeUnit.MILLISECONDS)
            .switchMap { Observable.just(it) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, "setupNameInput (onSuccess): $it")
                    viewModel.filter(it.toString())
                },
                { Log.d(TAG, "setupNameInput (onError): ${it.message}") }
            ).addTo(disposable)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Companion object
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        private var TAG: String = this::class.simpleName!!
    }
}
