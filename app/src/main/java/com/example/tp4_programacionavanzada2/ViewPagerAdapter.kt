package com.example.tp4_programacionavanzada2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AltaFragment()
            1 -> ModificacionFragment()
            2 -> ListadoFragment()
            else -> AltaFragment()
        }
    }
}