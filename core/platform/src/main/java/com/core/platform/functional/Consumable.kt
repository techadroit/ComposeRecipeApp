//package com.core.platform.functional
//
//import java.io.Serializable
//
//data class Consumable <out T> (val data: T?, private var isConsumed: Boolean = false) : Serializable {
//
//    @Synchronized
//    fun consume(): T? {
//        return if (!isConsumed) {
//            isConsumed = true
//            data
//        } else {
//            null
//        }
//    }
//
//    operator fun invoke(): T? {
//        return consume()
//    }
//}
//
////fun <T : Any?> T.asConsumable(): Consumable<T> {
////    return Consumable(this)
////}
//
//fun <T : Any> Resource<T>.asConsumable(): Consumable<Resource<T>> {
//    return Consumable(this)
//}
//
//inline fun <reified E> Consumable<*>.isOfType(type: E): Boolean = data is E
