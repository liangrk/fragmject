package com.example.fragment.module.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.fragment.library.base.adapter.BaseAdapter
import com.example.fragment.module.wan.R
import com.example.fragment.module.wan.bean.NavigationBean
import com.example.fragment.module.wan.databinding.ItemNavigationMenuBinding

class LinkMenuAdapter : BaseAdapter<NavigationBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemNavigationMenuBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: NavigationBean) {
        val binding = holder.binding as ItemNavigationMenuBinding
        binding.tv.text = item.name
        binding.root.background = if (item.isSelected) {
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.layer_while_item_bottom)
        } else {
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.layer_gray_item_bottom)
        }
    }

}