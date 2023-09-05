package com.business.profile.util

object Util {

    fun compareStringLists(list1: List<String>, list2: List<String>): Boolean {

        if(list1.isEmpty() || list2.isEmpty()) {
            return false
        }

        if (list1.size != list2.size) {
            return false
        }

        val sortedList1 = list1.sorted()
        val sortedList2 = list2.sorted()
        return sortedList1 == sortedList2
    }

}