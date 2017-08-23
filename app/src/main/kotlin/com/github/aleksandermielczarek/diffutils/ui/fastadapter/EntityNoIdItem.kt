package com.github.aleksandermielczarek.diffutils.ui.fastadapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.item_entity.view.*

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class EntityNoIdItem(model: EntityNoId) : ModelAbstractItem<EntityNoId, EntityNoIdItem, EntityNoIdItem.EntityNoIdItemViewHolder>(model) {

    override fun getLayoutRes(): Int = R.layout.item_entity

    override fun getType(): Int = layoutRes

    override fun getViewHolder(view: View): EntityNoIdItemViewHolder = EntityNoIdItemViewHolder(view)

    @SuppressLint("MissingSuperCall")
    override fun bindView(holder: EntityNoIdItemViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        holder.itemView.data1.text = model.data1
        holder.itemView.data2.text = model.data2
        holder.itemView.data3.text = model.data3
    }

    class EntityNoIdItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}