package com.github.aleksandermielczarek.diffutils.ui.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aleksandermielczarek.diffutils.BR
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.databinding.FragmentDiffUtilBindingBinding
import com.github.aleksandermielczarek.diffutils.domain.EntityId
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import com.github.aleksandermielczarek.diffutils.ui.DiffUtilFragment
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class BindingFragment : DiffUtilFragment() {

    val entityIdBinding: ItemBinding<EntityId> = ItemBinding.of(BR.viewModel, R.layout.item_entity_id_binding)
    val idEntities: DiffObservableList<EntityId> = DiffObservableList(object : DiffObservableList.Callback<EntityId> {
        override fun areItemsTheSame(oldItem: EntityId, newItem: EntityId): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EntityId, newItem: EntityId): Boolean = oldItem == newItem
    })

    val entityNoIdBinding: ItemBinding<EntityNoId> = ItemBinding.of(BR.viewModel, R.layout.item_entity_no_id_binding)
    val noIdEntities: DiffObservableList<EntityNoId> = DiffObservableList(object : DiffObservableList.Callback<EntityNoId> {
        override fun areItemsTheSame(oldItem: EntityNoId, newItem: EntityNoId): Boolean = areContentsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: EntityNoId, newItem: EntityNoId): Boolean = oldItem == newItem
    })

    private lateinit var binding: FragmentDiffUtilBindingBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiffUtilBindingBinding.inflate(inflater)
        binding.viewModel = this
        return binding.root
    }

    override fun diffUtilId(entities: List<EntityId>) {
        noIdEntities.calculateDiff(emptyList())
        idEntities.update(entities)
    }

    override fun diffUtilNoId(entities: List<EntityNoId>) {
        idEntities.update(emptyList())
        noIdEntities.update(entities)
    }
}