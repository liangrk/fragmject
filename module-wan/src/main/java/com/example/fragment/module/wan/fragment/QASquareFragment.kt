package com.example.fragment.module.wan.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragment.library.base.model.BaseViewModel
import com.example.fragment.library.base.view.OnLoadMoreListener
import com.example.fragment.library.base.view.OnRefreshListener
import com.example.fragment.library.base.view.PullRefreshLayout
import com.example.fragment.library.common.adapter.ArticleAdapter
import com.example.fragment.library.common.fragment.RouterFragment
import com.example.fragment.module.wan.databinding.FragmentQaSquareBinding
import com.example.fragment.module.wan.model.SquareViewModel

class QASquareFragment : RouterFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): QASquareFragment {
            return QASquareFragment()
        }
    }

    private val viewModel: SquareViewModel by viewModels()
    private var _binding: FragmentQaSquareBinding? = null
    private val binding get() = _binding!!

    private val articleAdapter = ArticleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQaSquareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initView() {
        //广场列表
        binding.list.layoutManager = LinearLayoutManager(binding.list.context)
        binding.list.adapter = articleAdapter
        //下拉刷新
        binding.pullRefresh.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: PullRefreshLayout) {
                viewModel.getUserArticle()
            }
        })
        //加载更多
        binding.pullRefresh.setOnLoadMoreListener(binding.list, object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: PullRefreshLayout) {
                viewModel.getUserArticleNext()
            }
        })
    }

    override fun initViewModel(): BaseViewModel {
        viewModel.userArticleResult.observe(viewLifecycleOwner) { result ->
            when (result.errorCode) {
                "0" -> if (viewModel.isHomePage()) {
                    articleAdapter.setNewData(result.data?.datas)
                } else {
                    articleAdapter.addData(result.data?.datas)
                }
                else -> activity.showTips(result.errorMsg)
            }
            //结束下拉刷新状态
            binding.pullRefresh.finishRefresh()
            //设置加载更多状态
            binding.pullRefresh.setLoadMore(viewModel.hasNextPage())
        }
        return viewModel
    }

    override fun initLoad() {
        if (viewModel.userArticleResult.value == null) {
            viewModel.getUserArticle()
        }
    }

}