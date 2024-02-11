package io.ashkay.picklerick.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.ashkay.picklerick.databinding.ItemCharacterBinding
import io.ashkay.picklerick.models.Character
import io.ashkay.picklerick.ui.adapter.CharacterAdapter.CharacterViewHolder

class CharacterAdapter : ListAdapter<Character, CharacterViewHolder>(CharacterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                name.text = character.name
                origin.text = character.origin.name
                gender.text = character.gender

                Glide
                    .with(root)
                    .load(character.image)
                    .centerCrop()
                    .into(image)

            }
        }
    }

    object CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

}