import java.text.SimpleDateFormat

class Journal {
    
    Date date
    String sDate
    String operation
    String typeObject
    String nom
    String prenom
    String matricule
    String unite
    String user
    int ref

    static mapping = {
        sort date:"desc"
        nom defaultValue : ""
        prenom defaultValue : ""
        matricule defaultValue : ""
        unite defaultValue : ""
    }
    
    static constraints = {
        nom (nullable : true)
        prenom (nullable : true)
        matricule (nullable : true)
        unite (nullable : true)
        ref (nullable : true)
        sDate (nullable :true)
    }
    
    def beforeValidate() {
        if(date){
            sDate = new SimpleDateFormat("dd MMM yyyy").format(date)
        }
    }
}
