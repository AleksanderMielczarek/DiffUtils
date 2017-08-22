package com.github.aleksandermielczarek.diffutils.ui.fastadapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityId
import com.mikepenz.fastadapter.items.GenericAbstractItem
import kotlinx.android.synthetic.main.item_entity.view.*

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class EntityIdItem(model: EntityId) : GenericAbstractItem<EntityId, EntityIdItem, EntityIdItem.EntityItemViewHolder>(model) {

    override fun getLayoutRes(): Int = R.layout.item_entity

    override fun getType(): Int = layoutRes

    override fun getViewHolder(view: View): EntityItemViewHolder = EntityItemViewHolder(view)

    override fun getIdentifier(): Long = model.id.toLong()

    @SuppressLint("MissingSuperCall")
    override fun bindView(holder: EntityItemViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        holder.itemView.data1.text = model.data1
        holder.itemView.data2.text = model.data2
        holder.itemView.data3.text = model.data3
    }

    class EntityItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}