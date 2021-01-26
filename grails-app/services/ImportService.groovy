import grails.transaction.Transactional
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.openxml4j.opc.OPCPackage

import java.text.Collator

/* rapportVerif.statut = "échec" si les erreurs ont bien été enregistrées
 * rapportVerif.statut = "échec sans trace" si les erreurs n'ont pas été enregistrées
 * rapportVerif.statut = "OK" La vérif s'est bien passée
 *
 *
 * Attention a bien ajouté dans Params l'absence :
 * groupe : absence
 * valeur: 21 (Paramétrable)
 */
    
class ImportService {
	
    def check = {rapport, lastRowNum, sheet ->
        def verif = new VerificationImport(statut:'échec', date : new Date(), type :  rapport)
        def listDataCell = this.dataCell(rapport)
        def listReference = [matricule:[],str:[]]
        def listRow = []
        for (def i = 1; i <= lastRowNum; i++ ) {
            XSSFRow row = sheet.getRow(i)
            verif = checkDoublonLignes(row, listRow, listDataCell, i, rapport, verif)
            def map = mapRow(row, listDataCell)
            def agent =  Agent.findWhere(matricule : map.matricule)
            if(agent) {
                if(!testAgentRef(agent,map,listDataCell)){
                    verif.addToStack(new VerificationStack( rapportName : rapport, typeError:"BDD", row:i+1, info1: "Champs de référence différent" ))
                }
            }
            if(rapport != "WACAT078"){
                verif  = checkDoublonChampsReference(row, listReference, listDataCell, i, rapport, verif)
            }
            listDataCell.each{ data ->
                data.listIndex.each{ index ->
                    XSSFCell cell = row.getCell(index)
                    if(data.required && cell) {
                        if(cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
                            verif.addToStack(new VerificationStack(typeError:"vide",row:i+1,rapportName:rapport,dataType:data.type))
                        }else{
                            if(!checkFormat(cell, data.type)) {
                                verif.addToStack(new VerificationStack(typeError:"invalide",row:i+1,rapportName:rapport,dataType:data.type))
                            }
                        }
                    }else{
                        if(!checkFormat(cell, data.type)) {
                            verif.addToStack(new VerificationStack(typeError:"invalide",row:i+1,rapportName:rapport,dataType:data.type))
                        }
                    }
                }
            }
        }
        return verif
    }

    def checkDoublonLignes = { row, listRow, listDataCell, i, rapport, verif  ->
        def line =  listDataCell.collect{ 
            it.listIndex.collect{
                row.getCell(it).toString()
            } 
        }.flatten().join(";")
        if (listRow.contains(line)){
            def idDouble = listRow.indexOf(line)+1
            verif.addToStack(new VerificationStack(typeError:"doublon ligne",row:i+1,rapportName:rapport,dataType:"",info1: (idDouble ? idDouble : "inconnu")))
        } else {
            listRow[i]=line
        }
        return verif
    }
	
    def checkDoublonChampsReference = {row, listReference, listDataCell, i, rapport, verif ->
        def reference =  listDataCell.findAll{
            it.required
        }.collect{ 
            it.listIndex.collect{
                row.getCell(it).toString()
            }
        }
        def str = reference.collect{it}.flatten().join(";")
        if(listReference.matricule.contains(reference[0])){
            verif.addToStack(new VerificationStack(typeError:"doublon champs",row:i+1, rapportName:rapport,dataType: "matricule", info1: listReference.matricule.indexOf(reference[0]) + 1 ))
        }else{
            listReference.matricule[i] = reference[0]
        }
        if(listReference.str.contains(str)){
            verif.addToStack(new VerificationStack(typeError:"doublon champs",row:i+1, rapportName:rapport,dataType: "", info1: listReference.str.indexOf(str) + 1 ))
        }else{
            listReference.str[i] = str
        }
        return verif
    }
	
    static def checkFormat = {cell, type ->
        if (cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK || cell.toString() == "") {
            return true
        }
        switch (type) {
        case "dateNaissance" :
        case "date" :
        case "dateEntree":
        case "dateSortie":
        case "dateDebutContrat":
        case "dateFinContrat":
        case "dateDebut":
        case "dateFin":
            return (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) && cell.getDateCellValue()
            break;
        case "matricule" :
            return cell ==~ /.*[A-Z]{1}/
            break;
        case "nom" :
            return cell ==~ /.*[A-Z]/
            break;
        case "prenom" :
            return cell ==~ /.*[^*]/
            break;
        case "genre" :
            return cell ==~ /F|M/
            break;
        case "retourAgent" : 
            return cell ==~ /oui|non/
            break;
        case "unite":
            return cell ==~ /[0-9]{4}.*/
            break;
        case "nationalite" :
        case "centre":
        case "libEmploi":
        case "positionAdm":
        case "motif" :
        case "numTel":
            return (cell.getCellType() ==  XSSFCell.CELL_TYPE_STRING)
            break;
        case "dureecontratAnnee":
        case "dureecontratMois":
        case "dureecontratJours":
        case "nbrJoursForce" :
        case "nbrJours" :
            return cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA
            break;
        case "courriel":
            return cell ==~/^[^\W][a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)*\@[a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\.[a-zA-Z]{2,4}$/
            break;
        case "insee":
            return (cell.getCellType() ==  XSSFCell.CELL_TYPE_STRING)
            break;		
        }
    }

    /*
     * import -> true : Le champs sera importé
     *
     *compareImport -> true : Champs comparé aux existants
     **/
    static def dataCell = { rapportName ->
        switch(rapportName) {
        case "WACAT027" :
            return  [
                [type : "matricule" ,required : true, import : false, compareImport : true, listIndex : [0]],
                [type : "nom", required : true, import : false, compareImport : true, listIndex : [1]],
                [type : "prenom", required : true, import : false, compareImport : true, listIndex : [2]],
                [type : "dateNaissance", required : true, import : false, compareImport : true, listIndex : [3]],
                [type : "dateEntree", required : false, import : true, compareImport : false,listIndex : [9]],
                [type : "dateSortie", required : false, import : true, compareImport : false, listIndex : [10]],
                [type : "dateDebutContrat", required : false, import : true, compareImport : false, listIndex : [23]],
                [type : "dateFinContrat", required : false, import : true, compareImport : false,listIndex : [24]],
                [type : "genre", required : false, import : false, compareImport : false, listIndex : [4]],
                [type : "nationalite", required : false, import : false, compareImport : true, listIndex : [5]],
                [type : "centre", required : false, import : false, compareImport : false, listIndex : [8]],
                [type : "unite", required : true, import : true, compareImport : false, listIndex : [7]],
                [type : "libEmploi", required : false, import : false, compareImport : false ,listIndex : [12]],
                [type : "positionAdm", required : false, import : false, compareImport : false, listIndex: [22]],
                /*                [type : "dureecontratAnnee", required : false, import : true, compareImport : false, listIndex : [25]],
                [type : "dureecontratMois", required : false, import : true, compareImport : false, listIndex : [26]],
                [type : "dureecontratJours", required : false, import : true, compareImport : false, listIndex : [27]],*/
            ]
            break;
        case "WACAT078" :
            return  [
                [type : "matricule" ,required : true, import : false, compareImport : true, listIndex : [2]],
                [type : "nom", required : true, import : false, compareImport : true, listIndex : [3]],
                [type : "prenom", required : true, import : false, compareImport : true, listIndex : [4]],
                [type : "dateNaissance", required : true, import : false, compareImport : true,  listIndex : [5]],
                [type : "date", required : false, import : false, compareImport : false, listIndex : [5,19,23,26,27]],
                [type : "genre", required : false, import : false, compareImport : false, listIndex : [6]],
                [type : "motif", required : false, import : false, compareImport : false, listIndex : [18]],
                [type : "centre", required : false, import : false, compareImport : false, listIndex : [7]],
                [type : "retourAgent", required : false, import : false, compareImport : false, listIndex : [25]],
                [type : "nbrJours", required : false, import : false, compareImport : false, listIndex : [28]],
                [type : "dateDebut", required : false, import : false, compareImport : false, listIndex : [0]],
                [type : "dateFin", required : false, import : false, compareImport : false, listIndex : [1]]
            ]
            break;
        case "WADAA06C" :
            return  [
                [type : "matricule" ,required : true, import : false, compareImport : true, listIndex : [0]],
                [type : "nom", required : true, import : false, compareImport : true, listIndex : [1]],
                [type : "prenom", required : true, import : false, compareImport : true, listIndex : [2]],
                [type : "dateNaissance", required : true, import : false, compareImport : true, listIndex : [3]],
                [type : "insee", required : false, import : true, compareImport : false, listIndex : [4]],
                [type : "numTel", required : false, import : true, compareImport : false, listIndex : [14]],
                [type : "courriel", required : false, import : true, compareImport : false, listIndex : [15]]
            ]
            break;
        }
    }

    def mapRow = {row, listDataCell ->
        def mapCompare = [:]
        listDataCell.each{data ->
            def index = data.listIndex[0]
            XSSFCell cell =   row.getCell(index)
            if (data.type.contains("date")) {
                if (cell && (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) && cell.getDateCellValue()) {
                    mapCompare[data.type] = cell.getDateCellValue()
                }else{
                    Date t = null
                    mapCompare[data.type] = t
                }
            } else {
                mapCompare[data.type] = cell.toString()
            }
        }
        mapCompare.matricule = mapCompare.matricule.replaceAll(" ", "")
        return mapCompare
    }

    def testAgentRef = {agent, ref, listDataCell->
        def result = true
        listDataCell.each{
            if(it.compareImport){
                if(ref[it.type] instanceof java.util.Date){
                    ref[it.type].set(hourOfDay : 12)
                    ref[it.type] = new java.sql.Timestamp(ref[it.type].getTime())
                }
                def a = agent[it.type]
                def r = ref[it.type]
                if(a instanceof java.lang.String){
                    a = a.toUpperCase()
                }
                if(r instanceof java.lang.String){
                    r = r.toUpperCase()
                }

                //Compare strings ignoring accented characters
                final Collator collator = Collator.getInstance()
                collator.setStrength(Collator.NO_DECOMPOSITION)

                if(a instanceof java.lang.String && r instanceof java.lang.String){
                    if(collator.compare(a, r)){
                        result = false
                    }

                }else{
                    if(a && a!=r) {
                        result = false
                    }
                }
            }
        }
        return result
    }
    
    def testRequired = {dataCell, agent->
        dataCell.each{
            if(it.required){
                if(!agent[it.type]){
                    return false
                }
            }
        }
        return true
    }
    
    def vmDateTestWacat027 = {agent ->
        def stack
        def today = new Date()
        use(groovy.time.TimeCategory) {
            if(!agent.archive && agent.dateEntree && agent.dateEntree < today && 
                (!(agent.positionAdm == "PEPS" || agent.positionAdm == "NON TITULAIRE" || agent.positionAdm == "NON PAYE") ||
                    (agent.dateDebutContrat && agent.dateFinContrat && agent.dateDebutContrat.minus(agent.dateFinContrat).months >= 3))){
                /*si date d'entré existe et date d'entré < now et que l'agent est un contractuelle ou que son contrat est de plus de 3 mois */
                if(agent.dateFinContrat && agent.dateFinContrat.minus(new Date()).days < 60) {
                    /*Si la Date de fin de contrat est dans moins de 60 jours ,
                    alors La FicheAgent bascule en liste prioritaire à convoquer Nature de visite VFA.
                    Si la FicheAgent est déjà présente en Liste prioritaire alors
                    changer la Nature de la visite en VFA */
                    agent.prioritaireVM = true
                    agent.natureVM = "VFA"
                    stack = new ImportStack(type : "agentPrioritaire", info:"VFA", matricule : agent.matricule, nom :agent.nom, prenom:agent.prenom)
                }else if(agent.id && !VM.findAllWhere(agent : agent) && (!(agent.dateFinContrat && agent.dateDebutContrat) || (agent.dateFinContrat && agent.dateDebutContrat && agent.dateFinContrat.minus(agent.dateDebutContrat).days > 91))){
                    /* si la durée du contrat > = à 3 mois ,
                    alors La FicheAgent bascule en liste prioritaire à convoquer Nature de visite Visite d'Embauche VE.
                    Si la FicheAgent est déjà présente en Liste prioritaire alors changer la Nature de la visite en VE */
                    agent.prioritaireVM = true
                    agent.natureVM = "VE"
                    stack = new ImportStack(type : "agentPrioritaire", info:"VE", matricule : agent.matricule, nom :agent.nom, prenom:agent.prenom)
                }
//supprimer car déjà réaliser par le service affectePrioritaire de AgentController
//                else if (agent.dateDerniereVM && today.minus(agent.dateDerniereVM).getDays() >= (365*2)){
//                    /* Si la date de dernière visite est > ou = à 2 AN,
//                    alors la FicheAgent bascule en liste prioritaire à
//                    convoquer Nature de visite VPA */
//                    agent.prioritaireVM = true
//                    agent.natureVM = "VPA"
//                    stack = new ImportStack(type : "agentPrioritaire", info:"VPA", matricule : agent.matricule, nom : agent.nom, prenom : agent.prenom)
//                }
            }
            return stack
        }
    }

    def importWACAT027 = {sheet, lastRowNum ->
        def rapport = new Rapport(type : "WACAT027", date : new Date())
        def status = 200
        def listDataCell = this.dataCell("WACAT027")
        (1..lastRowNum).each{
            XSSFRow row = sheet.getRow(it)
            def map = mapRow(row, listDataCell)
            map.dateCreation = new Date()
            map.dateModification = map.dateCreation
            def agent =  Agent.findWhere(matricule : map.matricule)
            if(agent) {
                if(testAgentRef(agent,map,listDataCell)){
                    if(testRequired(listDataCell, agent)){
                        def unite = Unite.findWhere(nom : map.unite)
                        agent.unite = unite ? unite : new Unite(nom : map.unite, importer : true).save()
                        agent.dateEntree = map.dateEntree
                        agent.dateSortie = map.dateSortie
                        agent.libEmploi = map.libEmploi
                        agent.positionAdm = map.positionAdm
                        agent.dateNaissance = map.dateNaissance
                        agent.dateDebutContrat = map.dateDebutContrat
                        agent.dateFinContrat = map.dateFinContrat
                        agent.genre = map.genre
                        
                        def testDate = vmDateTestWacat027(agent)
                        if(testDate){
                            rapport.addToStack(testDate)
                        }
                        if(agent.validate()) {
                            agent.save(flush:true)
                            rapport.addToStack(new ImportStack(type : "agentUpdate", matricule : agent.matricule, nom :agent.nom, prenom:agent.prenom, row : it+1))
                        }else{
                            status = 204
                            rapport.addToStack(new ImportStack(type : "erreur", erreur:"Agent :" + agent.matricule + " n'a pas pu être mis à jour.", matricule : agent.matricule, row : it+1))
                        }
                    }else{
                        status = 204
                        rapport.addToStack(new ImportStack(type : "erreur", erreur:"Champs requis manquant", matricule : agent.matricule, row : it+1))
                    }
                }else{
                    status = 204
                    rapport.addToStack(new ImportStack(type : "erreur", erreur:"Champs de référence différent", matricule : agent.matricule, row : it+1))
                }
            }else{
                if(!(map.positionAdm == "PEPS" || map.positionAdm == "NON TITULAIRE" || map.positionAdm == "NON PAYE")){//test si agent différent de PEPS ou Stagiaire
                    def newAgent = new Agent()
                    def unite = Unite.findWhere(nom : map.unite)
                    map.unite = unite ? unite : new Unite(nom : map.unite, importer: true).save()
                    map.site = unite ? unite.site : '';
                    newAgent.properties = map
                    def testDate = vmDateTestWacat027(newAgent)
                    if(testDate){
                        rapport.addToStack(testDate)
                    }else{
                        if(testRequired(listDataCell, newAgent)){
                            if(newAgent.validate()) {
                                newAgent.save(flush:true)
                                rapport.addToStack(new ImportStack(type : "agentNouveau", matricule : newAgent.matricule, nom :newAgent.nom, prenom:newAgent.prenom, row : it+1))
                            }else {
                                status = 204
                                rapport.addToStack(new ImportStack(type : "erreur", erreur:"L\'agent n'a pas pu être ajouté.", matricule : agent.matricule, row : it+1))
                            }
                        }else{
                            status = 204
                            rapport.addToStack(new ImportStack(type : "erreur", erreur:"Champs requis manquant", matricule : newAgent.matricule, row : it+1))
                        }
                    }
                }
            }
        }
        rapport.statut = status == 200 ? "OK" : "échec"
        rapport.save()
        return status
    }
    
    def importWADAA06C = {sheet, lastRowNum ->
        def status = 200
        def rapport = new Rapport(type : "WADAA06C", date : new Date())
        def listDataCell = this.dataCell("WADAA06C")
        (1..lastRowNum).each{
            XSSFRow row = sheet.getRow(it)
            def map = mapRow(row, listDataCell)
            def agent =  Agent.findWhere(matricule : map.matricule)
            if(agent) {
                if(testAgentRef(agent,map,listDataCell)){
                    agent.insee = map.insee
                    agent.courriel = map.courriel
                    agent.numTel = map.numTel
                    if(agent.validate()) {
                        agent.save()
                        rapport.addToStack(new ImportStack(type : "agentUpdate", matricule : agent.matricule, nom :agent.nom, prenom:agent.prenom, row : it+1))
                    }else{
                        rapport.addToStack(new ImportStack(type : "erreur", erreur:"L\'agent n'a pas pu être mis à jour.", matricule : agent.matricule, row : it+1))
                        status = 204
                    }
                }else{
                    rapport.addToStack(new ImportStack(type : "erreur", erreur:"Champs de référence différent", matricule : agent.matricule, row : it+1))
                    status = 204
                }
            }else{
                rapport.addToStack(new ImportStack(type : "erreur", erreur:"L\'agent n'a pas pu être mis à jour. Agent inconnu", matricule : map.matricule.replaceAll(" ", ""), row : it+1))
                status = 204
            }
        }
        rapport.statut = status == 200 ? "OK" : "échec"
        rapport.save()
        return status
    }

    def importWACAT078 = {sheet, lastRowNum ->
        def status = 200
        def rapport = new Rapport(type : "WACAT078", date : new Date())
        def listDataCell = this.dataCell("WACAT078")
        (1..lastRowNum).each{
            XSSFRow row = sheet.getRow(it)
            def map = mapRow(row, listDataCell)
            def agent =  Agent.findWhere(matricule : map.matricule)
            if(agent) {
                if(testAgentRef(agent,map,listDataCell)){
                    def listMotif = ["ACC. DE SERVICE", "LONGUE DUREE 1", "LONGUE DUREE 2", "LONGUE DUREE 3","CONGE MATERNITE","LONGUE MALADIE"]
                    // Couche Pathologique ? Congés supplementaire de grossesse ? Congés de présence parentale ? Congés sans traitements ? Congés longue durée 1,2,3, ? Maladie Ordinaire ? Maladie Professionnelle ? rechute accident de service ?
                    if(listMotif.contains(map.motif) && map.retourAgent == "oui") {
                        use(groovy.time.TimeCategory) {
                            def dateDebut = Date.parse('dd/MM/yy',map.dateDebut.format('dd/MM/yy'))
                            def dateFin = Date.parse('dd/MM/yy', map.dateFin.format('dd/MM/yy'))
                            def absence = Params.findWhere(groupe : "absence")
                            def paramsAbsence = absence ? absence.valeur as Integer : 21
                            if(!agent.archive && map.dateFin.minus(dateDebut).days >= paramsAbsence) {
                                agent.prioritaireVM = true
                                agent.natureVM = "VR"
                            }
                        }
                    }
                    if(agent.validate()) {
                        agent.save(flush:true)                        
                        rapport.addToStack(new ImportStack(type : "agentUpdate", matricule : agent.matricule, nom :agent.nom, prenom:agent.prenom, row : it+1))                        
                    }else{
                        rapport.addToStack(new ImportStack(type : "erreur", erreur:"Agent :" + agent.matricule + " n'a pas pu être mis à jour.", matricule : agent.matricule, row : it+1))
                        status = 204
                    }
                }else{
                    rapport.addToStack(new ImportStack(type : "erreur", erreur:"Champs de référence différent", matricule : agent.matricule, row : it+1))
                    status = 204
                }
            }else{
                rapport.addToStack(new ImportStack(type : "erreur", erreur:"L\'agent n'a pas pu être mis à jour. Agent inconnu", matricule : map.matricule.replaceAll(" ", ""), row : it+1))
                status = 204
            }
        }
        rapport.statut = status == 200 ? "OK" : "échec"
        rapport.save(flush:true)
        return status
    }
}