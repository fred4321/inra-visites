
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class Agent {
	
    String matricule
    String nom
    String prenom
    String lieuNaissance
    String nationalite
    String site
    String courriel
    String genre
    
    Date dateNaissance
    Date dateArretTravail
    Date dateCreation
    Date dateModification
    Date dateSortie
    Date dateEntree
    Date dateDerniereVM
    Date dateDebutContrat
    Date dateFinContrat
    
    String sDateNaissance
    String sDateArretTravail
    String sDateCreation
    String sDateModification
    String sDateSortie
    String sDateEntree
    String sDateDerniereVM
    String sDateDebutContrat
    String sDateFinContrat
    
    String departement
    String posteTravail
    String periodicite = "2 ans"
    String libEmploi
    String insee
    String groupe
    String numTel
    boolean prioritaireBS
    boolean prioritaireVM
    String natureVM = "VPA"
    boolean archive
    String positionAdm
    
	
    static belongsTo = [unite : Unite, gu : User]
    static hasMany = [vm : VM, bs : BS]
    
    static mapping = {
        vm sort : 'date', order: 'desc'
        archive defaultValue : false
        prioritaireVM defaultValue : false
    }

    static constraints = {
        dateFinContrat(nullable:true)
        dateDebutContrat(nullable:true)
        dateNaissance(nullable:true)
        dateArretTravail(nullable:true)
        dateSortie(nullable:true)
        dateEntree(nullable:true)
        lieuNaissance(nullable:true)
        nationalite(nullable:true)
        site(nullable:true)
        courriel(nullable:true)
        genre(nullable:true)
        departement(nullable:true)
        posteTravail(nullable:true)
        periodicite(nullable:true)
        libEmploi(nullable:true)
        insee(nullable:true)
        groupe(nullable:true)
        numTel(nullable:true)
        natureVM(nullable:true)
        prioritaireBS(nullable:true)
        prioritaireVM(nullable:true)
        positionAdm(nullable:true)
        dateDerniereVM(nullable:true)
        gu(nullable:true)
        groupe(nullable:true)
        sDateNaissance(nullable:true)
        sDateArretTravail(nullable:true)
        sDateCreation(nullable:true)
        sDateModification(nullable:true)
        sDateSortie(nullable:true)
        sDateEntree(nullable:true)
        sDateDerniereVM(nullable:true)
        sDateDebutContrat(nullable:true)
        sDateFinContrat(nullable:true)
    }
    
    def beforeValidate() {
        SimpleDateFormat formater = new SimpleDateFormat("dd MMM yyyy")
        if(dateNaissance){
            dateNaissance.set(hourOfDay:12, minute: 0,second: 0)
            sDateNaissance = formater.format(dateNaissance)
        }
        if(dateArretTravail){
            dateArretTravail.set(second: 0, minute: 0, hourOfDay: 12)
            sDateArretTravail = formater.format(dateArretTravail)
        }
        if(dateCreation){
            dateCreation.set(second: 0, minute: 0, hourOfDay: 12)
            sDateCreation = formater.format(dateCreation)
        }
        if(dateModification){
            dateModification.set(second: 0, minute: 0, hourOfDay: 12)
            sDateModification = formater.format(dateModification)
        }
        if(dateSortie){
            dateSortie.set(second: 0, minute: 0, hourOfDay: 12)
            sDateSortie = formater.format(dateSortie)
        }
        if(dateEntree){
            dateEntree.set(second: 0, minute: 0, hourOfDay: 12)
            sDateEntree = formater.format(dateEntree)
        }
        if(dateDerniereVM){
            dateDerniereVM.set(second: 0, minute: 0, hourOfDay: 12)
            sDateDerniereVM = formater.format(dateDerniereVM)
        }else{
            sDateDerniereVM = null
        }
        if(dateDebutContrat){
            dateDebutContrat.set(second: 0, minute: 0, hourOfDay: 12)
            sDateDebutContrat = formater.format(dateDebutContrat)
        }
        if(dateFinContrat){
            dateFinContrat.set(second: 0, minute: 0, hourOfDay: 12)
            sDateFinContrat = formater.format(dateFinContrat)
        }
    }
    
    def getDefaultProperties= {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            properties[it.name] = this[it.name]
        }
        properties.id = this.id
        return properties
    }
    
    def getPropertiesForGetAll = {
        def properties = [:]
        properties.id = this.id
        properties.matricule = this.matricule ?  this.matricule : ""
        properties.periodicite = this.periodicite ? this.periodicite : ""
        properties.site = this?.site 
        properties.insee = this.insee ? this.insee : ""
        properties.nom = this.nom ? this.nom : ""
        properties.prenom = this.prenom ? this.prenom : ""
        properties.dateSortie = this.dateSortie ? this.dateSortie : ""
        properties.unite = this.unite.nom
        properties.vmID = this.vm[0] ? this.vm[0].id : null
        properties.vmDate = this.vm[0] ? this.vm[0].date : ""
        properties.vmNature = this.vm[0] ? this.vm[0].natureVM : ""
        properties.convocation = this.vm[0] ? (this.vm[0].dateConvoc ? this.vm[0].dateConvoc : "" ) : ""
        properties.natureVM = this.natureVM
        return properties
    }
    
    def getPlugProperties = {
        def properties = this.getDefaultProperties()
        properties.unite = this.unite.nom
        properties.vm = this.vm
        properties.bs = this.bs
        if(this.gu ){
            properties.gu = this.gu.identifiant
            properties.agu = this.gu.email
        }
        return properties
    }
}
