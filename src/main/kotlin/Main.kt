import JsonToDataClass.jsonToData


fun main() {

    val x = readln()

    println(x.jsonToData())
}

data class x (
    val id: String,
    val owner: String,
    val brand: String,
    val phone: Int,
    val autheId: String,
    val address: String,
    val isActive: Boolean
)