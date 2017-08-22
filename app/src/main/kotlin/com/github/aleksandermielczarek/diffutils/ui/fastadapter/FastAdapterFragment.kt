package com.github.aleksandermielczarek.diffutils.ui.fastadapter

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityId
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import com.github.aleksandermielczarek.diffutils.ui.DiffUtilFragment
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.commons.utils.DiffCallback
import com.mikepenz.fastadapter.commons.utils.DiffCallbackImpl
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil
import kotlinx.android.synthetic.main.fragment_diff_util.view.*

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
        FastAdapterDiffUtil.set(entityIdAdapter.itemAdapter, items,
                object : DiffCallbackImpl<EntityIdItem>() {
                    override fun areContentsTheSame(oldItem: EntityIdItem, newItem: EntityIdItem): Boolean = oldItem.model == newItem.model
                }, true)
    }

    override fun diffUtilNoId(entities: List<EntityNoId>) {
        entityIdAdapter.clear()
        val items = entities.map { EntityNoIdItem(it) }
        FastAdapterDiffUtil.set(entityNoIdAdapter.itemAdapter, items,
                object : DiffCallback<EntityNoIdItem> {
                    override fun getChangePayload(oldItem: EntityNoIdItem, oldItemPosition: Int, newItem: EntityNoIdItem, newItemPosition: Int): Any? = null

                    override fun areItemsTheSame(oldItem: EntityNoIdItem, newItem: EntityNoIdItem): Boolean = false

                    override fun areContentsTheSame(oldItem: EntityNoIdItem, newItem: EntityNoIdItem): Boolean = oldItem.model == newItem.model
                }, true)
    }
}