package com.picazodev.electroniclogistica



fun Int.toKeyForLocationMap(): String{
    return "loc$this"
}

fun Int.toKeyForProductMap(): String{
    return "pro$this"
}