package com.renyongqiang.wanandroid.ui.content

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.renyongqiang.wanandroid.Constant
import com.renyongqiang.wanandroid.HttpConstant
import com.renyongqiang.wanandroid.R
import com.renyongqiang.wanandroid.databinding.FragmentContentBinding

class ContentFragment : Fragment() {
    private val args: ContentFragmentArgs by navArgs()

    companion object {
        private val TAG: String = ContentFragment::class.java.simpleName
    }

    private val contentViewModel: ContentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentContentBinding>(
            inflater,
            R.layout.fragment_content,
            container,
            false
        ).apply {
            viewModel = contentViewModel
            link = if (args.link.startsWith("/")) {
                Constant.BASE_URL + args.link
            } else {
                args.link
            }
            title = args.title

            contentViewModel.progressVisibility.observe(viewLifecycleOwner) {
                Log.d(TAG, "onActivityCreated: $it")
                newsProgBar.visibility = it
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        contentViewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
        Log.d(Companion.TAG, "onActivityCreated: ${args.id} + ${args.title} + ${args.link}")

    }

}