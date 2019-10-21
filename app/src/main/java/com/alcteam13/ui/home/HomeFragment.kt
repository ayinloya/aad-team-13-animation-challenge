package com.alcteam13.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alcteam13.R
import com.alcteam13.models.Inventory
import com.alcteam13.ui.inventory.AddInventoryActivity
import com.alcteam13.ui.inventory.inventory.InventoryView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.util.*

class HomeFragment : Fragment() {

    private var inventory: MutableList<Inventory> = Collections.emptyList()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this, HomeViewModelFactory())
                .get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val inventoryRV: RecyclerView = root.findViewById(R.id.inventory_list_rv)
        inventoryRV.layoutManager = GridLayoutManager(activity, GridLayout.VERTICAL)

//        inventoryRV.itemAnimator = SlideInLeftAnimator().apply {
//            setInterpolator(OvershootInterpolator())
//        }

        val inventoryAdapter = InventoryAdapter(activity!!.applicationContext, inventory)
        val adapter = ScaleInAnimationAdapter(inventoryAdapter)
        inventoryRV.adapter = adapter
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        homeViewModel.inventoryList.observe(this, Observer {
            this.inventory = it.success!!
//            adapter.notifyDataSetChanged()
            inventoryAdapter.setInventory(this.inventory)
        })


        val fab: FloatingActionButton = root.findViewById(R.id.fab)

        fab.setOnClickListener {
            activity!!.startActivityForResult(Intent(activity, AddInventoryActivity::class.java),12345)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 12345) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("result","inventory ${data!!.getParcelableExtra<InventoryView>("inventory")!!}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}