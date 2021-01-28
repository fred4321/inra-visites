import org.springframework.mail.MailSendException

import java.sql.SQLException
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import groovy.sql.Sql
import java.text.SimpleDateFormat
import MessageService
import groovy.json.JsonSlurper

/**
 *liste des statuts:
 *initial - non affecté - affecté - convoqué - absent - effectué - cloturé
 *initial : la vm n'a pas encore d'unité
 *non affecté : la vm est créer mais aucun agent n'est affecté dessus
 *affecté : vm créé et affecté à un agent
 *convoqué : vm créé, affecté et convocation envoyé
 *absent : l'agent ne c'est pas présenté
 *effectué : vm réalisé mais pas de mail envoyé à l'agent
 *cloturé : vm réalisé et mail récapitulatif envoyé à l'agent
 *****************************************/

class VMController {

    def grailsApplication
    def messageService
    def dataSource

    def testmail() {
        println grailsApplication.config.grails.mail.host
        messageService.send([message:grailsApplication.config.convocation, subject:"sujet", from:grailsApplication.config.email_sender, to:["f.fanien@gmail.com"] ])
        println "OK"
        return [status : 200, data : [ok: true]]
    }

    def getAll() {
        if(session.user) {
            if(params.max && params.offset && params.sort && params.filter) {
                def result = []
                def slurper = new JsonSlurper()
                def offset = Integer.valueOf(params.offset)
                def max = Integer.valueOf(params.max)
                def sort = slurper.parseText(params.sort)
                def filter = slurper.parseText(params.filter)
                def sortIndex = 'dateConvoc'
                def sortOrder  = 'asc'
                def unite = params.unite
                sort.each() { key, value ->
                    sortIndex = key
                    sortOrder = value
                }
                if(sortIndex == "dateConvoc") {
                    sortIndex = "date_convoc"
                }
                //condition
                def where = " WHERE 0=0" +
                (params.statut == 'affecté-convoqué' ? " AND a.archive = false" : "")+
                (filter.statut ? " AND v.statut LIKE '${filter.statut}%'" : "")+
                (params.statut ? " AND ( v.statut = '" + params.statut.replaceAll("-", "' OR v.statut = '") + "')" : "")+
                (filter.id ? " AND v.id = " + Long.parseLong(filter.id) : "" )+
                (filter.natureVM ? " AND v.naturevm LIKE '${filter.natureVM}%'" : "" )+
                (filter.creneau ? " AND v.creneau LIKE '%${filter.creneau}%'" : "")+
                (filter.lieu ? " AND v.lieu LIKE '${filter.lieu}%'" : "" )+
                (filter.site ? " AND v.site LIKE '${filter.site}%'" : "" )+
                (filter.dateConvoc ? " AND v.s_date_convoc LIKE '%${filter.dateConvoc}%'" : "" )+
                (filter.date ? " AND v.s_date LIKE '%${filter.date}%'" : "" )+
                (filter.unite || session.user.profil == "GU" ? " AND u.nom LIKE '${ session.user.profil == "GU" ? session.user.unite.nom : filter.unite }%'" : "" )+
                (filter.nom ? " AND a.nom LIKE '${filter.nom}%'" : "")+
                (filter.prenom ? " AND a.prenom LIKE '${filter.prenom}%'" : "" )+
                (filter.matricule ? " AND a.matricule LIKE '${filter.matricule}%'" : "" )+
                (filter.periodicite ? " AND a.periodicite LIKE '${filter.periodicite}%'" : "")+
                (filter.libEmploi ? " AND a.libEmploi LIKE '${filter.libEmploi}%'" : "" )
                //trie
                def order = " ORDER by ${sortIndex} ${sortOrder}"
                //execution de la requette
                def sql = new Sql(dataSource)
                def requette = "SELECT v.id, creneau, v.statut, v.site, v.date, date_convoc AS dateConvoc, date_envois_courrier_agent AS dateEnvoisCourrierAgent," +
                    //" v.naturevm AS natureVM, a.id as idAgent, a.nom, a.prenom, a.matricule, u.nom AS unite"+
                    " v.naturevm AS natureVM, a.id as idAgent, a.nom, a.prenom, a.matricule, a.libEmploi as libEmploi, a.date_naissance as dateNaissance, a.insee, u.nom AS unite"+

                    " FROM vm as v"+
                    " LEFT JOIN agent AS a ON v.agent_id = a.id"+
                    (unite == "agent" ? " LEFT JOIN unite AS u ON u.id = a.unite_id " : " LEFT JOIN unite AS u ON u.id = v.unite_id ")+
                (where != " where 0=0" ? where : "")+order
                def rows = sql.rows(requette)
                sql.close()

                //trie éventuel par créneaux
                if(sortIndex == "creneau") {
                    rows.sort() { x, y ->
                        def nX = 0
                        def nY = 0
                        if(x.creneau && y.creneau) {
                            def xT = x.creneau.toUpperCase().split("H")
                            def yT = y.creneau.toUpperCase().split("H")
                            if(xT[0].isInteger() && xT[1].isInteger() && yT[0].isInteger() && yT[1].isInteger()) {
                                nX = xT[0].toInteger() * 60 + xT[1].toInteger()
                                nY = yT[0].toInteger() * 60 + yT[1].toInteger()
                            }
                        }
                        if(sortOrder == "asc") {
                            nX <=> nY
                        }else {
                            nY <=> nX
                        }
                    }
                }
                //pagination du resultat
                for (int i = offset; i < offset + max; i ++ ) {
                    i < rows.size() ? result.push( rows.get(i) ) : null
                }

                //envoi du resultat
                return [status:200, data:[vms:result, total: rows.size()]]
            }
            return [status:415]
        }
        return [status : 401]
    }

    def get() {
        if(session.user) {
            def vm
            if(session.user.profil == "medecin" || session.user.profil == "SP") {
                vm = VM.get(params.id).getFullVm()
            }else {
                vm = VM.get(params.id).getLimitVm()
            }
            if(vm) {
                return [status : 200, data : vm]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }

    def post() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if(data && data.creneaux && data.creneaux.size() > 0 && data.date && data.site && data.site.id && data.lieu && data.lieu.id) {
                def site = Params.get(data.site.id)
                if (site) {
                    def lieu =  Params.get(data.lieu.id)
                    if(lieu) {
                        if (data.date.periode == "true") {
                            def dateDebut = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.date.debut.substring(0, 24))
                            def dateFin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.date.fin.substring(0, 24))
                            def list = []
                            while (dateDebut <= dateFin + 1) {
                                if(dateDebut.format('E') != "sam." && dateDebut.format('E') != "dim.") {
                                    data.creneaux.each() {
                                        def vm = new VM([ date : dateDebut, modification : dateDebut , creneau : it , site : site.valeur , statut : "initial" , lieu : lieu.valeur])
                                        if (vm.validate()) {
                                            vm.save()
                                            list.add(vm)
                                            new Journal(ref:vm.id, date:new Date(), operation:"Création", typeObject:"VM", user:session.user.identifiant).save()
                                        }
                                    };
                                }
                                dateDebut = dateDebut.next()
                            }
                            log.info("création de " + list.size() + " vm à attrivuer")
                            if(list) {
                                return [status : 200, data : list]
                            }
                            return [status : 204]
                        }else {
                            def date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.date.value.substring(0, 24))
                            def list = []
                            data.creneaux.each() {
                                def vm = new VM([ date : date , modification : date, creneau : it , site : site.valeur , statut : "initial" , lieu : lieu.valeur])
                                if (vm.validate()) {
                                    vm.save()
                                    new Journal(ref:vm.id, date:new Date(), operation:"Création", typeObject:"VM", user:session.user.identifiant).save()
                                    list.add(vm)
                                }
                            };
                            log.info("création de " + list.size() + " vm à attrivuer")
                            if(list) {
                                return [status : 200, data : list]
                            }
                            return [status : 204]
                        }
                    }
                    return [status : 406]
                }
                return [status : 407]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def assignAgentAndNature() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if( data && data.length() > 0) {
                data.each() {
                    if(it.id && it.agent && it.natureVM) {
                        def vm = VM.get(it.id)
                        if (vm) {
                            def agent = Agent.get(it.agent)
                            agent.dateDerniereVM = vm.date
                            vm.agent = agent
                            vm.natureVM  = it.natureVM
                            vm.agent.prioritaireVM = false
                            vm.statut = "affecté"
                            if(vm.validate() && agent.validate()) {
                                vm.save(flush:true)
                                agent.save(flush:true)
                                new Journal(ref:vm.id, date:new Date(), operation:"Modification", typeObject:"VM", nom:vm.agent.nom, prenom:vm.agent.prenom, matricule:vm.agent.matricule, unite:vm.agent.unite.nom, user:session.user.identifiant).save()
                                log.info("vm "+vm.id+" affécter à l'agent matricule : " + vm.agent.matricule)
                            }
                            else {
                                vm.errors.allErrors.each { println it }
                            }
                        }
                    }
                }
                return [status:200, data:null]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def assignUnite() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if( data && data.length() > 0) {
                data.each() {
                    if(it.id && it.unite) {
                        def vm = VM.get(it.id)
                        if(vm) {
                            vm.unite = Unite.get(it.unite)
                            vm.statut = "non affecté"
                            if(vm.validate()) {
                                vm.save(flush:true)
                                log.info("vm "+vm.id+" affecté à l'unité " + vm.unite.nom)
                            }
                            else {
                                vm.errors.allErrors.each { println it }
                            }
                        }
                    }
                }
                return [status:200]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def absent() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id) {
                def vm = VM.get(data.id)
                if (vm) {
                    vm.statut = "absent"
                    vm.agent.prioritaireVM = true
                    vm.agent.natureVM = vm.natureVM
                    def sql = new Sql(dataSource)
                    def requette = "SELECT MAX(date) as dateVm from vm where agent_id = " + vm.agent.id + " and statut = 'cloturé';"
                    def rows = sql.rows(requette)
                    sql.close()
                    if(rows[0] && rows[0].dateVm) {
                        vm.agent.dateDerniereVM = rows[0].dateVm
                    }else {
                        vm.agent.dateDerniereVM = null
                    }
                    if(vm.validate()) {
                        vm.modification = new Date()
                        vm.save(flush:true)
                        if(vm.date > new Date()) {
                            def emptyVM = new VM(date : vm.date, modification : vm.modification, creneau : vm.creneau, lieu : vm.lieu, site : vm.site, unite : vm.unite,  statut : "non affecté")
                            if(emptyVM.validate()) {
                                emptyVM.save()
                                log.info("vm "+vm.id+": absence de l'agent " + vm.agent.matricule + " lors de la visite")
                                return [status : 200, data : vm]
                            }
                            return [status : 500]
                        }
                        log.info("vm "+vm.id+": absence de l'agent " + vm.agent.matricule + " lors de la visite")
                        return [status : 200, data : vm]
                    }
                    else {
                        vm.errors.allErrors.each { println it }
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def convoquer() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if (data && data.id) {
                def vm = VM.get(data.id)
                vm.statut = "convoqué"
                vm.dateConvoc = new Date()
                vm.agent.prioritaireVM = false
                if(vm.validate()) {
                    vm.save()
                    def emails = [vm.agent.courriel, grailsApplication.config.email_sender]
                    vm.agent.gu ? emails.push(vm.agent.gu.email) : null
                    try {
                        messageService.send(messageService.prepareMessage(session.user, emails, ("INRAE - Convocation de " + vm.agent.nom + " " + vm.agent.prenom + " à la visite médicale le " + vm.date.format('dd/MM/yyyy') + "."), messageService.vmConvocation(vm)))
                        log.info("vm " + vm.id + " - agent " + vm.agent.nom + " convoqué le " + vm.dateConvoc)
                        return [status : 200, data : vm]
                    }
                    catch (MailSendException e) {
                        if(vm.agent.gu && vm.agent.gu.email) {
                            messageService.send(messageService.prepareMessage(session.user,  [vm.agent.gu.email], ("[Email non envoyé] INRAE - Convocation de "+vm.agent.nom+" "+vm.agent.prenom+" à la visite médicale le "+vm.date.format('dd/MM/yyyy')+"."), "<p>Le message ci dessous n'a pas pu être envoyé à un ou plusieurs des destinaires suivant : " + emails + "</p>" + messageService.vmConvocation(vm)))
                        }

                        log.error("[Adresse mail eronnée] vm " + vm.id + " - agent " + vm.agent.nom + " convoqué le " + vm.dateConvoc)
                        return [status : 500, data : [errors : "Erreur lors de l'envoi de mail"]]
                    }
                    catch (Exception e) {
                        return [status : 500, data : [errors : "Erreur lors de l'envoi de mail."]]
                    }
                }else {
                    vm.errors.allErrors.each { println it }
                    return [status : 500]
                }
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def recapitulation() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if (data && data.id) {
                def vm = VM.get(data.id)
                if (vm) {
                    vm.statut = "cloturé"
                    if(vm.validate()) {
                        vm.save()
                        def vmEmail = vm.getFullVm()
                        def fullNature = Params.findWhere(groupe:"natureVM", valeur : vmEmail.natureVM)
                        vmEmail.natureVM = fullNature ? fullNature.info1 : vmEmail.natureVM
                        def emails = [vm.agent.courriel, grailsApplication.config.email_sender]
                        messageService.send(messageService.prepareMessage(session.user, emails, "INRAE - Résultat de la Visite médicale de " + vm.agent.nom + " " + vm.agent.prenom + ", le " + vm.sDate, g.render(template:'../pdf/vm', model: [vm:vmEmail, user:[profil:"agent"]])))
                        log.info("vm " + vm.id + " cloturé. Envoie du mail récapitulatif")
                        return [status : 200, data : vm]
                    }else {
                        vm.errors.allErrors.each { println it }
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def mailAgent() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id) {
                def vm = VM.get(data.id)
                if (vm) {
                    vm.statut = "effectué"
                    vm.dateEnvoisCourrierAgent = new Date()
                    vm.agent.prioritaireVM = false
                    if(vm.validate() && vm.agent.validate()) {
                        vm.save()
                        vm.agent.save()
                        def vmEmail = vm.getFullVm()
                        def fullNature = Params.findWhere(groupe:"natureVM", valeur : vmEmail.natureVM)
                        vmEmail.natureVM = fullNature ? fullNature.info1 : vmEmail.natureVM
                        def emails = [vm.agent.courriel, grailsApplication.config.email_sender]
                        messageService.send(messageService.prepareMessage(session.user, emails, "INRAE - Résultat de la Visite médicale de " + vm.agent.nom + " " + vm.agent.prenom + ", le " + vm.sDate, g.render(template:'../pdf/vm', model: [vm:vmEmail, user:[profil:"agent"]])))

                        log.info("vm "+vm.id+" - Fin de la visite médical et envoi du mail à l'agent")
                        return [status : 200, data : vm]
                    }else {
                        vm.errors.allErrors.each { println it }
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def emailRuRrh() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id) {
                def vm = VM.get(data.id)
                def DU = vm.unite.DU
                def RRH = Params.findAllWhere(groupe:"RRH").collect { it.valeur }
                if (vm) {
                    vm.dateEnvoisCourrierDURRH = new Date()
                    if(vm.validate()) {
                        vm.save()
                        def vmEmail = vm.getFullVm()
                        def fullNature = Params.findWhere(groupe:"natureVM", valeur : vmEmail.natureVM)
                        vmEmail.natureVM = fullNature ? fullNature.info1 : vmEmail.natureVM
                        if(DU) {
                            println "envoi email DU : " + DU
                            messageService.send(messageService.prepareMessage(session.user, [DU], "INRAE - Résultat de la Visite médicale de " + vm.agent.nom + " " + vm.agent.prenom + ", le " + vm.sDate + vm.id, g.render(template:'../pdf/vm', model: [DU:true, vm:vmEmail, user:[profil:"DU"]])))
                        }
                        if(RRH) {
                            println "envoi email RRH : " + RRH
                            messageService.send(messageService.prepareMessage(session.user, RRH, "INRAE - Résultat de la Visite médicale de " + vm.agent.nom + " " + vm.agent.prenom + ", le " + vm.sDate + vm.id, g.render(template:'../pdf/vm', model: [DU:true, vm:vmEmail, user:[profil:"SP"]])))
                        }
                        return [status : 200, data : vm]
                    }else {
                        vm.errors.allErrors.each { println it }
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def update() {
        if(session.user) {
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id) {
                def vm = VM.get(data.id)
                if (vm) {
                    vm.apte = data.apte
                    vm.natureVM = data.autreNature ? data.autreNature : data.natureVM
                    vm.amenagement = data.amenagement ? data.amenagement : false
                    vm.modification = new Date()
                    vm.refMedecin = data.refMedecin
                    if(vm.validate()) {
                        vm.save()
                        new Journal(ref:vm.id, date:new Date(), operation:"Modification", typeObject:"VM", nom:vm.agent.nom, prenom:vm.agent.prenom, matricule:vm.agent.matricule, unite:vm.agent.unite.nom, user:session.user.identifiant).save()
                        return [status : 200, data : vm]
                    }
                    return [status : 500]
                }
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }

    def delete() {
        if(session.user) {
            def vm = VM.get(params.id)
            def mapResult
            if(vm) {
                vm.properties = params
                if (vm.agent) {
                    vm.agent.prioritaireVM = true
                    vm.agent.save()
                }
                if(vm.validate()) {
                    new Journal(ref : vm.id, date : new Date(), operation : "Supprimer", typeObject : "VM", nom : (vm.agent ? vm.agent.nom : null ), prenom : (vm.agent ? vm.agent.prenom : null), matricule : (vm.agent ? vm.agent.matricule : null), unite : (vm.agent ? vm.agent.unite.nom : null), user : session.user.identifiant).save(flush : true)
                    log.info("suppression de la vm " + vm.id)
                    vm.delete(flush:true)
                    return [status : 200, data : vm]
                }
                else {
                    vm.errors.allErrors.each { println it }
                    return [status : 500]
                }
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }

}
