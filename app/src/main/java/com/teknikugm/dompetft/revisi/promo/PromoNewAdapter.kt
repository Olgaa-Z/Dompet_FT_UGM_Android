package com.teknikugm.dompetft.revisi.promo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teknikugm.dompetft.R
import kotlinx.android.synthetic.main.activity_promo_adapter.view.*
import kotlinx.android.synthetic.main.activity_promo_adapter.view.kode_promo
import kotlinx.android.synthetic.main.activity_promo_adapter.view.min_belanja
import kotlinx.android.synthetic.main.activity_promo_adapter.view.persentase_promo
import kotlinx.android.synthetic.main.activity_promo_new_adapter.view.*

class PromoNewAdapter (private var data : List<DataItemPromoNew?>?, private var context : Context, private var onclick : (DataItemPromoNew?)->Unit)  :  RecyclerView.Adapter<PromoNewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoNewAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
        val l = view.inflate(R.layout.activity_promo_new_adapter, null)
        return PromoNewAdapter.ViewHolder(l)
    }

    override fun onBindViewHolder(holder: PromoNewAdapter.ViewHolder, position: Int) {
        val dataItem = data?.get(position)
        val b = 1
        holder.kodepromo.text = dataItem?.kodePromo
        holder.minbelanja.text = dataItem?.minBelanja
        holder.persentasepromo.text = dataItem?.persentasePromo.toString()
        holder.idpro.text = dataItem?.id.toString()
        holder.itemView.setOnClickListener {
           onclick(dataItem)
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?:0
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {

        val kodepromo = item.kodepromonew
        val minbelanja = item.minbelanjanew
        val persentasepromo = item.persentasepromonew
        val idpro = item.idpromo
    }



}