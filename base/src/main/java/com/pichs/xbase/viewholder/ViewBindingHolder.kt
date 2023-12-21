package com.pichs.xbase.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

class ViewBindingHolder<ViewBinder : ViewBinding>(val viewBinder: ViewBinder) : ViewHolder(viewBinder.root)