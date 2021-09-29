package com.ferrifrancis.cookpad.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.ferrifrancis.cookpad.dto.TrucoDTO
import kotlinx.android.synthetic.main.home_ver_lista_trucos.view.*
import kotlinx.android.synthetic.main.layout_home_list_item.view.*
import kotlinx.android.synthetic.main.layout_home_list_item.view.card_view_home_receta
import kotlinx.android.synthetic.main.layout_home_list_item.view.chip_aplauso
import kotlinx.android.synthetic.main.layout_home_list_item.view.chip_corazon

import kotlinx.android.synthetic.main.layout_home_list_item.view.img_ver_truco
import kotlinx.android.synthetic.main.layout_home_list_item.view.stripe_menu

class TrucoRecyclerAdapater(
    val trucoList: ArrayList<TrucoDTO>,
):RecyclerView.Adapter<TrucoRecyclerAdapater.HomeViewHolder>() {
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onBindViewHolder(holder: TrucoRecyclerAdapater.HomeViewHolder, position: Int) {
        val receta : TrucoDTO = this.trucoList[position]
        holder.imagenTruco.setImageBitmap(receta.imageTruco)
        holder.tituloTruco.setText(receta.tituloTruco)
        holder.nombreAutorTruco.setText(receta.nombreUsuarioAutor)
        holder.imagenUsuario.setImageBitmap(receta.imageUsuario)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrucoRecyclerAdapater.HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_ver_lista_trucos,parent, false)
        return TrucoRecyclerAdapater.HomeViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return this.trucoList.size
    }

    class HomeViewHolder constructor(
        itemView: View/*, listener: onItemClickListener*/
    ) : RecyclerView.ViewHolder(itemView) {

        val tituloTruco= itemView.et_titulo_truco1
        val nombreAutorTruco= itemView.tv_nombre_autor_truco
        val imagenTruco = itemView.img_ver_truco
        val imagenUsuario =itemView.img_usuario_truco



    }


}