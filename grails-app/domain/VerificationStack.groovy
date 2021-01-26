
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

/**
 *Type d'erreur : vide - invalide - doublon ligne - doublon champs
 */

class VerificationStack {
	
    String rapportName
    String typeError
    int row
    String dataType
    String info1
	
    static belongsTo = [verif : VerificationImport]

    static constraints = {
        row(nullable:true)
        dataType(nullable:true)
        info1 (nullable:true)
    }
    
    static mapping = {
        sort row : "desc"
    }
    
    def getDefaultProperties= {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            properties[it.name] = this[it.name]
        }
        properties.id=this.id
        return properties
    }
}
