package ttl.introkot.course._3MoreNullness

fun nullOps(nullable: String?) : Int{

    var d = nullable?.length

    return d?.times(3) ?: 2
}

fun boo(any: Any) {

}
