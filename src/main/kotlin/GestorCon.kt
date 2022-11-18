class GestorBDD private constructor() {
    companion object{
        private var instance: GestorBDD? = null

        fun getInstance(): GestorBDD{
            if(instance==null){
                instance = GestorBDD()
            }
            return instance!!
        }
    }
}