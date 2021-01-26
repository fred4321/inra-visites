import com.bloomhealthco.jasypt.GormEncryptedStringType

class Observation {

    String text
        
    static constraints = {
        text(nullable:true)
    }
    
    static mapping = {
        text type: 'text'
        text type: GormEncryptedStringType
    }
}
