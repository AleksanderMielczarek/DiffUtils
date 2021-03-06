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
import kotlinx.android.synthetic.main.fragment_diff_util.*

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

        idEntities.adapter = entityIdAdapter
        idEntities.layoutManager = LinearLayoutManager(activity)

        noIdEntities.adapter = entityNoIdAdapter
        noIdEntities.layoutManager = LinearLayoutManager(activity)
    }

    override fun diffUtilId(entities: List<EntityId>) {
        val items = entities.map { EntityIdItem(it) }
        FastAdapterDiffUtil.set(entityIdAdapter, items,
                object : DiffCallbackImpl<EntityIdItem>() {
                    override fun areContentsTheSame(oldItem: EntityIdItem, newItem: EntityIdItem): Boolean = oldItem.model == newItem.model
                })
    }

    override fun diffUtilNoId(entities: List<EntityNoId>) {
        val items = entities.map { EntityNoIdItem(it) }
        FastAdapterDiffUtil.set(entityNoIdAdapter, items,
                object : DiffCallback<EntityNoIdItem> {
                    override fun getChangePayload(oldItem: EntityNoIdItem, oldItemPosition: Int, newItem: EntityNoIdItem, newItemPosition: Int): Any? = null

                    override fun areItemsTheSame(oldItem: EntityNoIdItem, newItem: EntityNoIdItem): Boolean = areContentsTheSame(oldItem, newItem)

                    override fun areContentsTheSame(oldItem: EntityNoIdItem, newItem: EntityNoIdItem): Boolean = oldItem.model == newItem.model
                })
    }

    override fun changeIdEntitiesVisibility(visibility: Int) {
        idEntities.visibility = visibility
    }

    override fun changeNoIdEntitiesVisibility(visibility: Int) {
        noIdEntities.visibility = visibility
    }
}