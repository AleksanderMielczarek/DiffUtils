package com.github.aleksandermielczarek.diffutils.ui.sdk

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import kotlinx.android.synthetic.main.item_entity.view.*

/**
 * Created by Aleksander Mielczarek on 23.08.2017.
 */
class EntityNoIdAdapter : RecyclerView.Adapter<EntityNoIdAdapter.EntityNoIdViewHolder>() {

    var noIdEntities: List<EntityNoId> = emptyList()

    override fun onBindViewHolder(holder: EntityNoIdViewHolder, position: Int) {
        val entityId = noIdEntities[position]
        holder.itemView.data1.text = entityId.data1
        holder.itemView.data2.text = entityId.data2
        holder.itemView.data3.text = entityId.data3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityNoIdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return EntityNoIdViewHolder(view)
    }

    override fun getItemCount(): Int = noIdEntities.size

    class EntityNoIdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}