import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import com.bloomhealthco.jasypt.GormEncryptedStringType

class User {
	
    String identifiant
    String password
    String profil
    String email
    
    static belongsTo  = [ unite : Unite]

    static constraints = {
        unite ( nullable : true)
        email( nullable : true)
    }
    
    static mapping = {
        password type: GormEncryptedStringType
    }
    
    def getDefaultProperties= {
        def properties = [:]
        properties["id"]=this["id"]
        properties["identifiant"]=this["identifiant"]
        properties["profil"]=this["profil"]
        properties["email"]=this["email"]
        properties.unite = this.unite ? this.unite.nom : null
        return properties
    }
    
    static def All = {
        def users = this.findAll()
        def listUser = users.collect{user ->
            def properties = user.getDefaultProperties()
            properties.unite = user.unite ? user.unite.nom : null
            properties.password = user.password
            return properties
        }
        return listUser
    }
    
    static def getGu = {
        def users  = User.findAllByProfil("GU")
        def listUser = users.collect{user ->
            return user.getDefaultProperties()
        }
        return listUser
    }
}
