package com.sdm.ecomileage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdm.ecomileage.databinding.ItemPostBinding


class PostsAdapter(// Getters:
    // Fields:
    val context: Context
) : RecyclerView.Adapter<PostsAdapter.BoardViewHolder?>() {
  //  private val boards: List<Int>

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding: ItemPostBinding =
            ItemPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        // Initializing:
        //val board: Int = boards[position]
        // Developing:
        //holder.binding.vectorImageView.setImageResource(board)
        /*    holder.binding.headerTextView.setText(board.getHeader())
            holder.binding.contentTextView.setText(board.getContent())*/
    }

    override fun getItemCount(): Int {
        return 5
    }

    // Holders:
    class BoardViewHolder(binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Fields:
        var binding: ItemPostBinding = binding

        // Getters:
        @JvmName("getBinding1")
        fun getBinding(): ItemPostBinding {
            return binding
        }

    }


    // Constructor:
    init {
        notifyDataSetChanged()
    }
}