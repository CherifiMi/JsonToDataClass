object JsonToDataClass {

    lateinit var json: String
    val noo = listOf("\"", "{", "}")

    var isList = false
    val indexs = mutableListOf<Int>()

    val names = mutableListOf<String>()
    val types = mutableListOf<String>()

    var endJson = mutableListOf<String>()

    //_______________________________________

    fun String.jsonToData(): String{
        json = this

        // remove unwanted data
        noo.forEach { json = json.remove(it) }

        // replace , with * in a list
        json.forEachIndexed() {index, it ->
            when(it){
                '[' -> isList = !isList
                ']' -> isList = !isList
            }
            if (isList && it == ','){ indexs.add(index) }
        }

        json = StringBuilder(json).also {
            for (i in indexs){
                it.set(i, '*')
            }
        }.toString().filterNot { it.isWhitespace() || it == '\n' }

        json.split(",").forEach { it.split(':').let { i ->
            names.add(i[0])
            types.add(i[1].getType())
        } }

        for (i in 0 until names.size){
            val x = "   val ${names[i]}: ${types[i]}${if (i != names.size-1) ",\n" else ""}"

            endJson.add(x)
        }

        val x  = "data class x (\n" + endJson.joinToString(separator = "") + "\n)"

        return x
    }


    fun String.remove(new: String): String {
        return this.replace(new, "")
    }

    fun String.getType(): String {

        val isBool = this.toBooleanStrictOrNull() != null
        val isList = this[0] == '['
        val isInt = this.toIntOrNull() != null
        val isFloat =  this.toFloatOrNull() != null

        return if (isBool) "Boolean"
        else if (isList) "List<String>"
        else if (isInt) "Int"
        else if (isFloat) "Float"
        else "String"
    }

}