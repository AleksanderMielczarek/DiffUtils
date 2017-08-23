package com.github.aleksandermielczarek.diffutils.ui.sdk

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityId
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import com.github.aleksandermielczarek.diffutils.ui.DiffUtilFragment
import kotlinx.android.synthetic.main.fragment_diff_util.view.*

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class SdkFragment : DiffUtilFragment() {

    private val entityIdAdapter = EntityIdAdapter()
    private val entityNoIdAdapter = EntityNoIdAdapter()

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
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = entityIdAdapter.idEntities[oldItemPosition]
                val newItem = entities[newItemPosition]
                return oldItem.id == newItem.id
            }

            override fun getOldListSize(): Int = entityIdAdapter.idEntities.size

            override fun getNewListSize(): Int = entities.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = entityIdAdapter.idEntities[oldItemPosition]
                val newItem = entities[newItemPosition]
                return oldItem == newItem
            }

        }, true)
        entityIdAdapter.idEntities = entities
        result.dispatchUpdatesTo(entityIdAdapter)
    }

    override fun diffUtilNoId(entities: List<EntityNoId>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    areContentsTheSame(oldItemPosition, newItemPosition)

            override fun getOldListSize(): Int = entityNoIdAdapter.noIdEntities.size

            override fun getNewListSize(): Int = entities.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = entityNoIdAdapter.noIdEntities[oldItemPosition]
                val newItem = entities[newItemPosition]
                return oldItem == newItem
            }

        }, true)
        entityNoIdAdapter.noIdEntities = entities
        result.dispatchUpdatesTo(entityNoIdAdapter)
    }
}