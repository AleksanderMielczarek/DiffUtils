package com.github.aleksandermielczarek.diffutils.ui.fastadapter

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityId
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import com.github.aleksandermielczarek.diffutils.ui.DiffUtilFragment
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.commons.utils.DiffCallback
import com.mikepenz.fastadapter.commons.utils.DiffCallbackImpl
import com.mikepenz.fastadapter.utils.IdDistributor
import kotlinx.android.synthetic.main.fragment_diff_util.view.*
import java.util.*

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class FastAdapterFragment : DiffUtilFragment() {

    private val entityIdAdapter = FastItemAdapter<EntityIdItem>()
    private val entityNoIdAdapter = FastItemAdapter<EntityNoIdItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_diff_util, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.idEntities.adapter = entityIdAdapter
        view.idEntities.layoutManager = LinearLayoutManager(activity)
        view.noIdEntities.adapter = entityNoIdAdapter
        view.noIdEntities.layoutManager = LinearLayoutManager(activity)
    }

    override fun diffUtilId(entities: List<EntityId>) {
        entityNoIdAdapter.clear()
        val items = entities.map { EntityIdItem(it) }
        set(entityIdAdapter.itemAdapter, items,
                object : DiffCallbackImpl<EntityIdItem>() {
                    override fun areContentsTheSame(oldItem: EntityIdItem, newItem: EntityIdItem): Boolean = oldItem.model == newItem.model
                }, true)
    }

    override fun diffUtilNoId(entities: List<EntityNoId>) {
        entityIdAdapter.clear()
        val items = entities.map { EntityNoIdItem(it) }
        set(entityNoIdAdapter.itemAdapter, items,
                object : DiffCallback<EntityNoIdItem> {
                    override fun getChangePayload(oldItem: EntityNoIdItem, oldItemPosition: Int, newItem: EntityNoIdItem, newItemPosition: Int): Any? = null

                    override fun areItemsTheSame(oldItem: EntityNoIdItem, newItem: EntityNoIdItem): Boolean = areContentsTheSame(oldItem, newItem)

                    override fun areContentsTheSame(oldItem: EntityNoIdItem, newItem: EntityNoIdItem): Boolean = oldItem.model == newItem.model
                }, true)
    }

    private fun <A : ItemAdapter<Item>, Item : IItem<*, *>> set(adapter: A, items: List<Item>, callback: DiffCallback<Item>, detectMoves: Boolean): A {
        if (adapter.isUseIdDistributor) {
            IdDistributor.checkIds(items)
        }

        adapter.fastAdapter.collapse(false)
        if (adapter.comparator != null) {
            Collections.sort(items, adapter.comparator)
        }

        adapter.mapPossibleTypes(items)
        val oldItems = mutableListOf<Item>()
        oldItems.addAll(adapter.adapterItems)
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldItems.size

            override fun getNewListSize(): Int = items.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    callback.areItemsTheSame(oldItems[oldItemPosition], items[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    callback.areContentsTheSame(oldItems[oldItemPosition], items[newItemPosition])

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val result = callback.getChangePayload(oldItems[oldItemPosition], oldItemPosition, items[newItemPosition], newItemPosition)
                return result ?: super.getChangePayload(oldItemPosition, newItemPosition)
            }
        }, detectMoves)
        //if (items != adapter.adapterItems) {
        if (!adapter.adapterItems.isEmpty()) {
            adapter.adapterItems.clear()
        }

        adapter.adapterItems.addAll(items)
        //}

        result.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                adapter.fastAdapter.notifyAdapterItemRangeInserted(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                adapter.fastAdapter.notifyAdapterItemRangeRemoved(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                adapter.fastAdapter.notifyAdapterItemMoved(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                adapter.fastAdapter.notifyAdapterItemRangeChanged(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + position, count, payload)
            }
        })
        return adapter
    }
}