package com.sdm.ecomileage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdm.ecomileage.databinding.OnBoardItemBinding


class BoardAdapter(// Getters:
    // Fields:
    val context: Context, boards: List<Int>
) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder?>() {
    private val boards: List<Int>

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding: OnBoardItemBinding =
            OnBoardItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        // Initializing:
        val board: Int = boards[position]
        // Developing:
        holder.binding.vectorImageView.setImageResource(board)
        /*    holder.binding.headerTextView.setText(board.getHeader())
            holder.binding.contentTextView.setText(board.getContent())*/
    }

    override fun getItemCount(): Int {
        return boards.size
    }

    // Holders:
    class BoardViewHolder(binding: OnBoardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Fields:
        var binding: OnBoardItemBinding = binding

        // Getters:
        @JvmName("getBinding1")
        fun getBinding(): OnBoardItemBinding {
            return binding
        }

    }


    // Constructor:
    init {
        this.boards = boards
        notifyDataSetChanged()
    }
}