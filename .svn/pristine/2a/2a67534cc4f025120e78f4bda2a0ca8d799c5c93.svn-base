
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

/* 
 * 
 * rapportVerif.statut = "échec" si les erreurs ont bien été enregistrées
 * rapportVerif.statut = "échec sans trace" si les erreurs n'ont pas été enregistrées
 * rapportVerif.statut = "OK" La vérif s'est bien passée
 */
class VerificationImport {

    Date date
    String sDate
    String type
    String statut
	
    static hasMany = [stack : VerificationStack]

    static constraints = {
        stack(nullable:true)
    }
    
    static mapping = {
        stack sort : 'row', order: 'asc'
    }
    
    def beforeValidate() {
        sDate = new SimpleDateFormat("dd MMM yyyy").format(date)
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
    
    static def getRapportVerification(id){
        def verif = VerificationImport.get(id)
        if(verif){
            def result = verif.getDefaultProperties()
            result.vide = []
            result.invalide = []
            result.lignes = []
            result.champs = []
            result.DBB = []
            verif.stack.each(){
                switch(it.typeError){
                case "vide":
                    result.vide.add(it.getDefaultProperties())
                    break
                case "invalide":
                    result.invalide.add(it.getDefaultProperties())
                    break
                case "doublon ligne":
                    result.lignes.add(it.getDefaultProperties())
                    break
                case "doublon champs":
                    result.champs.add(it.getDefaultProperties())
                    break
                case "BDD":
                   result.DBB.add(it.getDefaultProperties())
                   break
                default:
                    break
                }
            }
            result.champsLength = result.champs.size()
            result.ligneLength = result.lignes.size()
            result.lignes = VerificationImport.groupByInfo1(result.lignes)
            result.champs = VerificationImport.groupByChamp(result.champs)
            return result
        }
        return null
    }
    
    static def groupByInfo1(listStack) {
        def group = [];
        def result = [];
        while (listStack.size() > 0) {
            def s = listStack.remove(0);
            if (group.indexOf(s.info1) == -1) {
                group.push(s.info1);
            }
            def p = group.indexOf(s.info1);
            result[p] = result[p] ? result[p] + ", Ligne " + s.row : "Ligne " + s.info1 + ", Ligne " + s.row;
        }
        return result;
    };
    
    static def groupByChamp(listStack) {
        def matricule = [];
        def ligne = [];
        def result = []
        listStack.each(){
            switch (it.dataType) {
            case 'matricule':
                matricule.add(it);
                break;
            default :
                ligne.add(it);
                break;
            }
        }
        matricule = VerificationImport.groupByInfo1(matricule);
        ligne = VerificationImport.groupByInfo1(ligne);
        matricule.each(){
            result.add(it+' : champ "matricule" identique');
        }
        ligne.each(){
            result.add(it+' : champ(s) identique');
        }
        return result
    }
}
