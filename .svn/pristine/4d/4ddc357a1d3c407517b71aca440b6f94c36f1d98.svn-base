
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class Unite {
	
    String nom
    String lieu
    String site
    String DU
    boolean importer
    
    //static belongsTo = [defaultGu : User]
    static hasMany = [agent : Agent, gu : User]

    static constraints = {
        gu (nullable:true)
        //defaultGu (nullable:true)
        site (nullable:true)
        lieu (nullable:true)
        DU (nullable:true)
    }
    
    static mapping = {
        sort "nom"
        importer defaultValue: false
    }
    
    def getDefaultProperties= {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            properties[it.name] = this[it.name]
        }
        properties.id = this.id
        //properties.defaultGu = this.defaultGu ? this.defaultGu.identifiant : null 
        return properties
    }
}
